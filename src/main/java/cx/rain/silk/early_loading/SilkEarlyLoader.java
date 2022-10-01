package cx.rain.silk.early_loading;

import com.chocohead.mm.api.ClassTinkerers;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import static cx.rain.silk.early_loading.SilkEarlyLoadingConstants.*;

public class SilkEarlyLoader implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.addTransformation(CLASS_MINECRAFT_GAME_PROVIDER, node -> {
            for (var method : node.methods) {
                if (method.name.equals(METHOD_MINECRAFT_GAME_PROVIDER_LOCATE_GAME)
                        && method.desc.equals(METHOD_DESC_MINECRAFT_GAME_PROVIDER_LOCATE_GAME)) {
                    var insnList = new InsnList();
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, CLASS_SILK_WORKER_HELLO, METHOD_SILK_WORKER_HELLO, METHOD_DESC_SILK_WORKER_HELLO));
                    insnList.add(new InsnNode(Opcodes.ARETURN));
                    method.instructions.add(insnList);
                }
            }
        });
    }
}
