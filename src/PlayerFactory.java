public class PlayerFactory {
    public enum PlayerTypes {

        COMPUTER("Computer"),
        SENTIENT("Sentient");
        private final String label;
        PlayerTypes(String label) {
            this.label = label;
        }
        public String toString() {
            return label;
        }
    }
    public Player getPlayer(PlayerTypes playerType, String name, String marker, int turn){
// if (playerType == null) {
//    playerType = PlayerTypes.SENTIENT;
//    return new Sentient("SENTIENT", "X", 0);
//}
        if (playerType == null) {
            throw new NullPointerException("Player type is null");
        }

        return switch (playerType) {
                case COMPUTER -> new Computer(name, marker, turn);
                case SENTIENT -> new Sentient(name, marker, turn);
            };
        }
}
