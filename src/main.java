/**
 * Server program, for retrieving, converting and storing weather data
 */
public class main {

    /**
     * Main function
     */
    public static void main(String[] args) throws Exception {
        // Create server
        Server server = new Server(800); // 10 clients

        // Run server (Could make a thread of it)
        server.run();
    }
}