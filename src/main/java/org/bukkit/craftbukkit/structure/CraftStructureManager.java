package org.bukkit.craftbukkit.structure;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

public class CraftStructureManager implements StructureManager {

    private final StructureTemplateManager structureManager;

    public CraftStructureManager(StructureTemplateManager structureManager) {
        this.structureManager = structureManager;
    }

    @Override
    public Map<NamespacedKey, Structure> getStructures() {
        Map<NamespacedKey, Structure> cachedStructures = new HashMap<>();
        for (Map.Entry<Identifier, Optional<StructureTemplate>> entry : structureManager.structureRepository.entrySet()) {
            entry.getValue().ifPresent(definedStructure -> cachedStructures.put(CraftNamespacedKey.fromMinecraft(entry.getKey()), new CraftStructure(definedStructure)));
        }
        return Collections.unmodifiableMap(cachedStructures);
    }

    @Override
    public Structure getStructure(NamespacedKey structureKey) {
        Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

        final Optional<StructureTemplate> definedStructure = structureManager.structureRepository.get(CraftNamespacedKey.toMinecraft(structureKey));
        if (definedStructure == null) {
            return null;
        }
        return definedStructure.map(CraftStructure::new).orElse(null);
    }

    @Override
    public Structure loadStructure(NamespacedKey structureKey, boolean register) {
        Identifier minecraftKey = createAndValidateMinecraftStructureKey(structureKey);

        Optional<StructureTemplate> structure = structureManager.structureRepository.get(minecraftKey);
        structure = structure == null ? Optional.empty() : structure;
        structure = structure.isPresent() ? structure : structureManager.loadTemplateFromFile(minecraftKey);
        structure = structure.isPresent() ? structure : structureManager.loadTemplateFromResource(minecraftKey);

        if (register) {
            structureManager.structureRepository.put(minecraftKey, structure);
        }

        return structure.map(CraftStructure::new).orElse(null);
    }

    @Override
    public Structure loadStructure(NamespacedKey structureKey) {
        return loadStructure(structureKey, true);
    }

    @Override
    public void saveStructure(NamespacedKey structureKey) {
        Identifier minecraftKey = createAndValidateMinecraftStructureKey(structureKey);

        structureManager.saveTemplate(minecraftKey);
    }

    @Override
    public void saveStructure(NamespacedKey structureKey, Structure structure) throws IOException {
        Preconditions.checkArgument(structureKey != null, "NamespacedKey structure cannot be null");
        Preconditions.checkArgument(structure != null, "Structure cannot be null");

        File structureFile = getStructureFile(structureKey);
        Files.createDirectories(structureFile.toPath().getParent());
        saveStructure(structureFile, structure);
    }

    @Override
    public Structure registerStructure(NamespacedKey structureKey, Structure structure) {
        Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");
        Preconditions.checkArgument(structure != null, "Structure cannot be null");
        Identifier minecraftKey = createAndValidateMinecraftStructureKey(structureKey);

        final Optional<StructureTemplate> optionalDefinedStructure = Optional.of(((CraftStructure) structure).getHandle());
        final Optional<StructureTemplate> previousStructure = structureManager.structureRepository.put(minecraftKey, optionalDefinedStructure);
        return previousStructure == null ? null : previousStructure.map(CraftStructure::new).orElse(null);
    }

    @Override
    public Structure unregisterStructure(NamespacedKey structureKey) {
        Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");
        Identifier minecraftKey = createAndValidateMinecraftStructureKey(structureKey);

        final Optional<StructureTemplate> previousStructure = structureManager.structureRepository.remove(minecraftKey);
        return previousStructure == null ? null : previousStructure.map(CraftStructure::new).orElse(null);
    }

    @Override
    public void deleteStructure(NamespacedKey structureKey) throws IOException {
        deleteStructure(structureKey, true);
    }

    @Override
    public void deleteStructure(NamespacedKey structureKey, boolean unregister) throws IOException {
        Identifier key = CraftNamespacedKey.toMinecraft(structureKey);

        if (unregister) {
            structureManager.structureRepository.remove(key);
        }
        Path path = structureManager.getTemplatePath(key, ".nbt");
        Files.deleteIfExists(path);
    }

    @Override
    public File getStructureFile(NamespacedKey structureKey) {
        Identifier minecraftKey = createAndValidateMinecraftStructureKey(structureKey);
        return structureManager.getTemplatePath(minecraftKey, ".nbt").toFile();
    }

    @Override
    public Structure loadStructure(File file) throws IOException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        FileInputStream fileinputstream = new FileInputStream(file);
        return loadStructure(fileinputstream);
    }

    @Override
    public Structure loadStructure(InputStream inputStream) throws IOException {
        Preconditions.checkArgument(inputStream != null, "inputStream cannot be null");

        return new CraftStructure(structureManager.readTemplate(inputStream));
    }

    @Override
    public void saveStructure(File file, Structure structure) throws IOException {
        Preconditions.checkArgument(file != null, "file cannot be null");
        Preconditions.checkArgument(structure != null, "structure cannot be null");

        FileOutputStream fileoutputstream = new FileOutputStream(file);
        saveStructure(fileoutputstream, structure);
    }

    @Override
    public void saveStructure(OutputStream outputStream, Structure structure) throws IOException {
        Preconditions.checkArgument(outputStream != null, "outputStream cannot be null");
        Preconditions.checkArgument(structure != null, "structure cannot be null");

        NbtCompound nbttagcompound = ((CraftStructure) structure).getHandle().writeNbt(new NbtCompound());
        NbtIo.writeCompressed(nbttagcompound, outputStream);
    }

    @Override
    public Structure createStructure() {
        return new CraftStructure(new StructureTemplate());
    }

    private Identifier createAndValidateMinecraftStructureKey(NamespacedKey structureKey) {
        Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

        Identifier minecraftkey = CraftNamespacedKey.toMinecraft(structureKey);
        Preconditions.checkArgument(!minecraftkey.getPath().contains("//"), "Resource key for Structures can not contain \"//\"");
        return minecraftkey;
    }

    @Override
    public Structure copy(Structure structure) {
        Preconditions.checkArgument(structure != null, "Structure cannot be null");
        return new CraftStructure(structureManager.createTemplate(((CraftStructure) structure).getHandle().writeNbt(new NbtCompound())));
    }
}
