package org.bukkit.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class CraftMerchantCustom extends CraftMerchant {

    public CraftMerchantCustom(String title) {
        super(new MinecraftMerchant(title));
        getMerchant().craftMerchant = this;
    }

    @Override
    public String toString() {
        return "CraftMerchantCustom";
    }

    @Override
    public MinecraftMerchant getMerchant() {
        return (MinecraftMerchant) super.getMerchant();
    }

    public static class MinecraftMerchant implements Merchant {

        private final Text title;
        private final TradeOfferList trades = new TradeOfferList();
        private PlayerEntity tradingPlayer;
        protected CraftMerchant craftMerchant;

        public MinecraftMerchant(String title) {
            Preconditions.checkArgument(title != null, "Title cannot be null");
            this.title = CraftChatMessage.fromString(title)[0];
        }

        @Override
        public CraftMerchant getCraftMerchant() {
            return craftMerchant;
        }

        @Override
        public void setCustomer(PlayerEntity entityhuman) {
            this.tradingPlayer = entityhuman;
        }

        @Override
        public PlayerEntity getCustomer() {
            return this.tradingPlayer;
        }

        @Override
        public TradeOfferList getOffers() {
            return this.trades;
        }

        @Override
        public void trade(TradeOffer merchantrecipe) {
            // increase recipe's uses
            merchantrecipe.use();
        }

        @Override
        public void onSellingItem(ItemStack itemstack) {
        }

        public Text getScoreboardDisplayName() {
            return title;
        }

        @Override
        public int getExperience() {
            return 0; // xp
        }

        @Override
        public void setExperienceFromServer(int i) {
        }

        @Override
        public boolean isLeveledMerchant() {
            return false; // is-regular-villager flag (hides some gui elements: xp bar, name suffix)
        }

        @Override
        public SoundEvent getYesSound() {
            return SoundEvents.VILLAGER_YES;
        }

        @Override
        public void setOffersFromServer(TradeOfferList merchantrecipelist) {
        }

        @Override
        public boolean isClient() {
            return false;
        }
    }
}
