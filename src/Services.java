import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Services {

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
        return "final";
    }

    public static String saveGame(String data) {
        return "";
    }

    public static String defineWord(String word) {
        try {
            String temp = "";
            URL url = new URL(String.format("https://api.dictionaryapi.dev/api/v2/entries/en/%s", word));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
			conn.connect();
            if(conn.getResponseCode() == 200) {
				Scanner scan = new Scanner(url.openStream());
				while(scan.hasNext()) {
					temp += scan.nextLine();
                }
                scan.close();
                JSONParser parser = new JSONParser();
                JSONArray arr = (JSONArray) parser.parse(temp);
                ArrayList<?> definition = new Gson().fromJson(arr.toString(), new TypeToken<List<String>>(){}.getType());
                System.out.println(Arrays.deepToString(definition.toArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return "";
    }
}
