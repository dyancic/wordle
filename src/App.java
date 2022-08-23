
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class App {
    public static void main(String[] args) throws Exception {
        String word = Wordle.getWord(); 

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
