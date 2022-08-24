import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.SourceDataLine;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Wordle {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static ArrayList<String> gameData = new ArrayList<>();

    //method for testing the wordle and returning the scores
    public static String solve(String input, String answer, Integer guess){
        List<String> inputList = Arrays.asList(input.split(""));
        List<String> answerList = Arrays.asList(answer.split(""));
        String[] result = new String[5];
        String resultString = "";
        
        //1. check for ticks    
        //2. check for in the word but wrong position and wrong letters
        for (int i=0;i<5;i++) {
            if (inputList.get(i).equals(answerList.get(i))) {
                result[i] = ANSI_GREEN + inputList.get(i) + ANSI_RESET;
                continue;
            }

            String testLetter = inputList.get(i);
            ArrayList<String> filteredAnswer = answerList.stream()
                .filter(c -> c.equals(testLetter))
                .collect(Collectors.toCollection(ArrayList::new));
            if (!filteredAnswer.isEmpty()) {
                result[i] = ANSI_YELLOW + testLetter + ANSI_RESET;
            } else {
                result[i] = ANSI_RED + testLetter + ANSI_RESET;
            } 
        }

        // 3.filtering through the results array to check for duplicate yellows and greens
        for (int i=0;i<5;i++) {
            String testLetter = inputList.get(i);

            long answerCount = answerList.stream()
                .filter(c -> c.equals(testLetter))
                .count();

            long inputCount = inputList.stream()
                .filter(c -> c.equals(testLetter))
                .count();

            long count = answerCount - Stream.of(result)
                .filter(c -> c != null ? c.equals(ANSI_GREEN + testLetter + ANSI_RESET) : false).count();
            
            if (answerCount < inputCount && i == inputList.lastIndexOf(testLetter)) {
                for (int j=0; j<5; j++) {
                    if (count <= 0 && result[j].equals(ANSI_YELLOW + testLetter + ANSI_RESET)) {
                        result[j] = testLetter;
                    }
                    if (result[j].equals(ANSI_YELLOW + testLetter + ANSI_RESET)) {
                        --count;
                    }
                }
            }
        }

        //4. return string to get displayed in the console
        for (String r: result) {
            resultString += r;
        }
        
        gameData.add(resultString);
        return resultString;
    }

    public static void resetGame() {
        gameData.removeAll(gameData);
    }
}
