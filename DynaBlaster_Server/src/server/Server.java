package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server class
 */
public class Server {
    int port;

    /**
     * Non-args constructor
     */
    public Server() {
        Config config = Config.getInstance();
        config.loadPort();
        port = config.getPort();
    }

    /**
     * This method waits for the request from the Client, multithreaded, can serve multiple requests
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public void runServer() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is up");
        while (true) {
            Thread.sleep(10);
            Socket socket = serverSocket.accept();
            new Thread(new ServerThread(socket)).start();
        }
    }
}
