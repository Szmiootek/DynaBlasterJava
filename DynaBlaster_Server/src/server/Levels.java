package server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class which determines current level configuration
 */
public class Levels {

    /**
     * An instance of levels
     */
    public static Levels _instance = null;

    /**
     * Method which allows to get an instance of the levels class
     * @return _instance
     */
    public static Levels getInstance(){
        if(_instance == null)
            _instance = new Levels();
        return _instance;
    }

    /**
     * Method which loads configuration of the chosen level from text file
     * @param levelIndex index of the level
     * @return response for the Client
     */
    public String load(int levelIndex) {
        String response = "";
        try {
            String line;
            FileReader reader = new FileReader("DynaBlaster_Server/src/maps/level" + levelIndex);
            BufferedReader bfr = new BufferedReader(reader);
            while ((line = bfr.readLine()) != null) {
                response += line + "-";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
        return response;
    }
}
