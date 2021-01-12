package server;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Difficulty class - represents difficulty levels
 */
public class Difficulty {

    /**
     * An instance of Difficulty
     */
    public static Difficulty _instance = null;

    /**
     * Difficulty level
     */
    public static Properties difficulties;

    /**
     * Method which allows to get an instance of the Difficulty class
     * @return _instance
     */
    public static Difficulty getInstance() {
        if (_instance == null)
            _instance = new Difficulty();
        return _instance;
    }

    /**
     * Method which loads difficulty configuration
     * @param difficulty_level difficulty level
     * @return response for the Client
     */
    public String loadDifficulties(int difficulty_level) {
        try {
            FileReader reader = new FileReader("DynaBlaster_Server/src/config/Difficulties");
            difficulties = new Properties();
            difficulties.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }

        switch (difficulty_level) {
            case 1 -> {
                return difficulties.getProperty("Easy");
            }
            case 2 -> {
                return difficulties.getProperty("Medium");
             }
            case 3 -> {
                return difficulties.getProperty("Hard");
             }
            default -> { return ""; }
        }
    }
}
