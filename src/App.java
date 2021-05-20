import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        showWelcomeASCIIArt();
        Game game = new Game();
        game.start();
    }

    private static void showWelcomeASCIIArt() {
        try {
            File myObj = new File("welcomeASCII.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println("\033[0;33m"+data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
