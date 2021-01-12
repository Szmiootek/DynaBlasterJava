package app.main;

import app.properties.Config;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Class responsible for connecting with the server
 */
public class Client {
    static Socket socket;
    public static boolean isOffline = true;
    static Config cfg = Config.getInstance();

    /**
     * Gets configuration from the server
     * @return configuration as a String
     * @throws IOException IOException
     */
    public static String getConfig() throws IOException {
        String response = connect("getConfig");
        if(socket != null){
            socket.close();
        }
        System.out.println("Config loaded from server");
        return response;
    }

    /**
     * Gets difficulties from the server
     * @return difficulties as a String
     * @throws IOException IOException
     */
    public static String getDifficulties() throws IOException {
        String response = connect("getDifficulties" + "-" + Main.difficulty_level);
        if (socket != null) {
            socket.close();
        }
        System.out.println("Difficulties loaded from server");
        return response;
    }

    /**
     * Gets scenario from the server
     * @return scenario as a String
     * @throws IOException IOException
     */
    public static String getScenario() throws IOException {
        String response = connect("getScenario");
        if(socket != null){
            socket.close();
        }
        System.out.println("Scenario loaded from server");
        return response;
    }

    /**
     * Gets ranking from the server
     * @return ranking as a String
     * @throws IOException IOException
     */
    public static String getRanking() throws IOException {
        String response = connect("getRanking");
        if (socket != null) {
            socket.close();
        }
        System.out.println("Ranking loaded from server");
        return response;
    }

    /**
     * Gets level configuration from the server
     * @param levelIndex index of the level
     * @return level configuration as a String
     * @throws IOException IOException
     */
    public static String getLevel(int levelIndex) throws IOException {
        String response = connect("getLevel" + "-" + levelIndex);
        if (socket != null) {
            socket.close();
        }
        System.out.println("Level " + levelIndex + " loaded from server");
        return response;
    }

    /**
     * Saves the score on the server
     * @param request combined nick and score
     * @throws IOException IOException
     */
    public static void saveScore(String request) throws IOException {
        connect("saveScore" + "-" + request);
        if (socket != null) {
            socket.close();
        }
        System.out.println("Score saved to the server");
    }

    /**
     * Checks whether the server is online or offline
     * @return appearance of the server (online/offline)
     */
    public static boolean checkIfOffline(){
        try {
            isOffline = false;
            socket = new Socket(cfg.getIp(), cfg.getPort());
        } catch (IOException e) {
            System.out.println("Server offline!");
            isOffline = true;
        }
        return isOffline;
    }

    /**
     * This method is a main responsible way of connecting with the server
     * @param command request sent to the server
     * @return server's answer on the request
     * @throws IOException IOException
     */
    private static String connect(String command) throws IOException {
        cfg = Config.getInstance();
        try{
            socket = new Socket(cfg.getIp(), cfg.getPort());
        } catch (Exception e){
            System.out.println("Could not create a socket!");
        }
        socket = new Socket(cfg.getIp(), cfg.getPort());
        OutputStream os;
        os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        pw.println(command);
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.readLine();
    }
}
