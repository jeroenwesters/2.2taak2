import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// Handles server functions
public class ServerTask extends Thread {
    private Socket socket;

    public ServerTask(Socket socket) {
        this.socket = socket;

        System.out.println(String.format("New thread started"));

    }


    /**
     * Start thread
     */
    public void run() {
        // Infinite loop, to keep the thread alive.
        while (true) {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {
                System.out.println("Exception encounterted");
            }

            System.out.println(Thread.currentThread().getName() +
                    " finished executing");

            Server.RemoveClient();
            break;
            // Read line
            //

            // When closing: Server.RemoveClient();
        }
    }
}
