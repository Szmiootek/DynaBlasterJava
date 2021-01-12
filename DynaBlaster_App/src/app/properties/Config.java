package app.properties;

import app.main.Client;

import javax.swing.*;
import java.util.Properties;
import java.io.*;
import java.util.Vector;

/**
 * Configuration class - contains an instance of Properties
 */

public class Config {

    /**
     * An instance of Config
     */
    public static Config _instance = null;
    /**
     * Properties which will be used through the project
     */
    public static Properties properties;

    /**
     * This is the scenario
     */
    public static Vector<Integer> Scenario;

    private static String ip;
    private static int port;

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
     * Method which loads all of the Properties from the file
     */
    public void load() {
        try {
            FileInputStream reader = new FileInputStream("DynaBlaster_App/src/config/Properties");
            properties = new Properties();
            properties.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }

        try {
            FileReader reader = new FileReader("DynaBlaster_App/src/config/Scenario");
            BufferedReader bfr = new BufferedReader(reader);
            Scenario = new Vector<>();
            String str;
            while ((str = bfr.readLine()) != null){
                Scenario.addElement(Integer.parseInt(str));
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No matching file found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File exception has been thrown!");
        }
    }

    /**
     * Method which loads all of the Properties from the server
     * @throws IOException IOException
     */
    public void loadFromServer() throws IOException {
        String response = Client.getConfig();
        Reader stringReader = new StringReader(response.replace("-", "\n"));
        properties = new Properties();
        properties.load(stringReader);

        Scenario = new Vector<>();
        response = Client.getScenario();
        if (response != null) {
            String[] parts = response.split("-");
            for (String part : parts) {
                Scenario.add(Integer.parseInt(part));
            }
        }
    }

    /**
     * Gets the Properties of the Config
     * @return Properties
     */
    public Properties getProperties(){
        return properties;
    }

    /**
     * Gets one chosen property by its key
     * @param key one specified property key
     * @return property
     */
    public String getProperty(String key){
        return getProperties().getProperty(key);
    }

    /**
     * Sets the IP used in server connection
     * @param ip IP address
     */
    public void setIP(String ip){ Config.ip = ip; }

    /**
     * Sets the port used in server connection
     * @param port Communication port
     */
    public void setPort(int port){ Config.port = port; }

    /**
     * Gets the IP used in server connection
     * @return ip IP address
     */
    public String getIp(){ return ip; }

    /**
     * Gets the port used in server connection
     * @return port Communication port
     */
    public int getPort(){ return port; }
}
