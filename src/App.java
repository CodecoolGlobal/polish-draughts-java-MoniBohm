public class App {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
        System.out.println(game.checkForTacticalWinner());
    }
}
