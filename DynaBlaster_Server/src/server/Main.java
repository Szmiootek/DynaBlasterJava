package server;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Main class
 */
public class Main {

    /**
     * Main method of the server
     * @param args System arguments
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ScoreBoard.loadRanking();
        Server server = new Server();
        System.out.println("IP address: " + InetAddress.getLocalHost().toString().split("/")[1]);
        System.out.println("Port: " + Config.getInstance().getPort());
        server.runServer();
    }
}
