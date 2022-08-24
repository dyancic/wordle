
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TO-DO:
// add word and how many guesses to game-history.json
// fetch dictionary meaning from the api and create a method in wordle https://cdn.crunchify.com/wp-content/uploads/2013/03/How-to-write-JSON-object-to-File-in-Java.png
// add a you lose if word isn't guessed

public class App {
    public static void main(String[] args) throws Exception {
        String word = Services.getWord(); 
        System.out.println(word);
        for (int i=0; i<6; i++) {
            Scanner s = new Scanner(System.in);
            String ans = "";
            String input = "";
            System.out.printf("Enter a 5 letter word - you have %s guesses remaining%n", 6-i);

            while (ans.length() == 0) {
                input = s.nextLine().toLowerCase();
                Matcher m = Pattern.compile("[^a-zA-Z]+").matcher(input);

                if (m.find() || input.length() != 5) {
                    System.out.println("Please enter a valid input");
                } else {
                    Services.defineWord(word);
                    ans = Wordle.solve(input, word, i);
                    System.out.println(ans);
                }
            }

            if (input.equals(word)) {
                System.out.printf("Congratulations you won in %s guesses!%n", i+1);
                for (String game: Wordle.gameData) {
                    System.out.println(game);
                }
                break;
            }
        }
    }
}
