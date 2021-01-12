package server;

import java.io.*;
import java.net.Socket;

/**
 * Main server thread
 */
public class ServerThread implements Runnable {
    private final Socket socket;

    /**
     * Parametrized constructor
     * @param socket socket used in the communication with the Client
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Overwritten method of the Runnable interface, used when new Thread is created from this class
     */
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                String fromClient = bufferedReader.readLine();
                if (fromClient != null) {
                    System.out.println("From client: " + fromClient);
                    String serverRespond = ServerCommands.serverAction(fromClient);
                    printWriter.println(serverRespond);
                    printWriter.flush();
                    System.out.println("Server respond: " + serverRespond);
                    break;
                }

            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Connection lost!");
        }
    }
}
