package app.properties;

import app.main.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;


/**
 * Class which determines current level configuration
 */
public class Levels {

    /**
     * An instance of levels
     */
    public static Levels _instance = null;
    /**
     * This is a String array which contains the configuration of the how the map will look during concrete level
     */
    public static Vector<String> configuration;

    private static int nmb_lvl;
    String line;

    /**
     * Non-args constructor
     */
    public Levels() { nmb_lvl = 0; }

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
     * Method which loads configuration of the chosen level from file
     */
    public void load() {
        configuration = null;
        try {
            FileReader reader = new FileReader("DynaBlaster_App/src/maps/level" + Config.Scenario.elementAt(nmb_lvl));
            BufferedReader bfr = new BufferedReader(reader);
            configuration = new Vector<>();
            while((line = bfr.readLine()) != null){
                configuration.add(line);
            }
            ++nmb_lvl;
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
    }

    /**
     * Method which loads configuration of the chosen level from the server
     */
    public void loadFromServer() {
        configuration = null;
        try {
            String response = Client.getLevel(Config.Scenario.elementAt(nmb_lvl));
            configuration = new Vector<>();
            String[] parts = response.split("-");
            configuration.addAll(Arrays.asList(parts));
            ++nmb_lvl;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the current level number
     * @return current level
     */
    public static int getNmb_lvl() { return nmb_lvl; }
}
