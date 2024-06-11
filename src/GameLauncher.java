public class GameLauncher {

    public GameLauncher() {
        try {
            new Window();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GameLauncher();
    }
}
