package server;

import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Ranking class
 */
public class ScoreBoard {

    static Map<Integer, String> scores;

    /**
     * Loads Ranking from the file
     */
    public static void loadRanking() {
        scores = new TreeMap<>(Collections.reverseOrder());
        try {
            FileReader fileReader = new FileReader("DynaBlaster_Server/src/config/ScoreBoard");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            int index;
            while((str = bufferedReader.readLine()) != null){
                index = str.indexOf('-');
                try {
                    scores.put(Integer.parseInt(str.substring(0, index)), str.substring(index + 1));
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("StringIndexOutOfBoundsException has been thrown on the server!");
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
    }

    /**
     * Prepares Ranking to be sent to the Client
     * @return response for the Client
     */
    public static String getRanking() {
        String response = "";
        try {
            FileReader fileReader = new FileReader("DynaBlaster_Server/src/config/ScoreBoard");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            int index;
            while((str = bufferedReader.readLine()) != null){
                index = str.indexOf('-');
                try {
                    scores.put(Integer.parseInt(str.substring(0, index)), str.substring(index + 1));
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("StringIndexOutOfBoundsException has been thrown on the server!");
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }

        Set<Map.Entry<Integer, String>> entrySet = scores.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet){
            response += entry.getKey() + "-" + entry.getValue() + "_";
        }

        return response;
    }

    /**
     * Saves score to the file
     * @param nick nick of the player
     * @param score result of the game
     */
    public static void saveScore(String nick, int score) {
        scores.put(score, nick);
        saveToFile(nick, score);
    }

    private static void saveToFile(String nick, int score) {
        try {
            File file = new File("DynaBlaster_Server/src/config/ScoreBoard");
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(score + "-" + nick + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot open the ScoreBoard file!");
        }
    }
}
