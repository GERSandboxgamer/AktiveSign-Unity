package de.sbg.unity.aktivesign;

import net.risingworld.api.objects.Player;

public class asAttribute {

    public final PlayerAttribute Player;

    public asAttribute() {
        this.Player = new PlayerAttribute();
    }

    public class PlayerAttribute {

        public final SignAttribute Sign;
        private final String ToggleModus, SaveModus;

        public PlayerAttribute() {
            this.Sign = new SignAttribute();
            this.ToggleModus = "AktiveSign_PlayerAttribute_ToggleModus";
            this.SaveModus = "AktiveSign_PlayerAttribute_SaveModus";
        }

        public void setDefault(Player player) {
            setToggleModus(player, false);
            setSaveModus(player, false);
            Sign.setDestroyMode(player, false);
            Sign.setEditMode(player, false);
        }

        public void setToggleModus(Player player, boolean modus) {
            player.setAttribute(ToggleModus, modus);
        }

        public boolean getToggleModus(Player player) {
            return (boolean)player.getAttribute(ToggleModus);
        }
        
        public void setSaveModus(Player player, boolean modus) {
            player.setAttribute(SaveModus, modus);
        }

        public boolean getSaveModus(Player player) {
            return (boolean)player.getAttribute(SaveModus);
        }

        public class SignAttribute {

            private final String EditMode, DestroyMode;

            public SignAttribute() {
                EditMode = "AktiveSign_PlayerAttribute_Sign_EditMode";
                DestroyMode = "AktiveSign_PlayerAttribute_Sign_DestroyMode";
            }

            public boolean getEditMode(Player player) {
                return (boolean) player.getAttribute(EditMode);
            }

            public void setEditMode(Player player, boolean editmode) {
                player.setAttribute(EditMode, editmode);
            }

            public boolean getDestroyMode(Player player) {
                return (boolean) player.getAttribute(DestroyMode);
            }

            public void setDestroyMode(Player player, boolean destroymode) {
                player.setAttribute(DestroyMode, destroymode);
            }
        }

    }

}
