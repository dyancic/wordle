import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
        for (int i=0;i<5;i++) {
            if (inputList.get(i).equals(answerList.get(i))) {
                result[i] = ANSI_GREEN + inputList.get(i) + ANSI_RESET;
            }
        }
        
        //2. check for in the word but wrong position
        for (int i=0;i<5;i++) {
            if (result[i] != null) continue;

            String testLetter = inputList.get(i);
            List<String> filteredAnswers = answerList.stream()
                .filter(c -> c.equals(testLetter))
                .collect(Collectors.toList());

            if (filteredAnswers.size() > 0) {
                result[i] = ANSI_YELLOW + inputList.get(i) + ANSI_RESET;
            } else {
                result[i] = ANSI_RED + inputList.get(i) + ANSI_RESET;
            }
            //need more logic here for duplicates
        }
        
        //3. return string to get displayed in the console
        for (String r: result) {
            resultString += r;
        }
        gameData.add(resultString);
        return resultString;
    }

    public static void resetGame() {
        gameData.removeAll(gameData);
    }

    public static String getWord() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader("./resources/word-list.json"));
            ArrayList<String> wordList = new Gson().fromJson(arr.toString(), new TypeToken<List<String>>(){}.getType());
            Random r = new Random();
            int random = r.nextInt(wordList.size());
            return wordList.get(random);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "throw";
    }

    public static String saveGame() {
        
    }
}
