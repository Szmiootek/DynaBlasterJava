package server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration class - contains an instance of Properties
 */
public class Config {

    static Properties properties;
    static int port;
    /**
     * An instance of Config
     */
    public static Config _instance = null;

    /**
     * Method which allows to get an instance of the Config class
     * @return _instance
     */
    public static Config getInstance(){
        if(_instance == null)
            _instance = new Config();
        return _instance;
    }

    /**
     * This method loads the port from the configuration file
     */
    public void loadPort() {
        try {
            FileReader fileReader = new FileReader("DynaBlaster_Server/src/config/Properties");
            properties = new Properties();
            properties.load(fileReader);
            port = Integer.parseInt(properties.getProperty("port"));
            fileReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
    }

    /**
     * This method loads the properties from the file
     * @return response for the Client
     */
    public String loadConfig() {
        String response = "";
        try {
            FileReader fileReader = new FileReader("DynaBlaster_Server/src/config/Properties");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                response += str + "-";
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
        return response;
    }

    /**
     * This method loads the scenario from the file
     * @return response for the Client
     */
    public String loadScenario() {
        String response = "";
        try {
            FileReader fileReader = new FileReader("DynaBlaster_Server/src/config/Scenario");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null){
                response += str + "-";
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
        return response;
    }

    /**
     * Gets the port used in the communication
     * @return port communication port
     */
    public int getPort() { return port; }
}
