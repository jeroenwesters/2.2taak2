import database.Database;
import model.Measurement;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Server program, for retrieving, converting and storing weather data
 */
public class main {

    static int count = 0;

    synchronized static void addCount(){
        count++;
    }
    synchronized static int getCount(){
        return count;
    }


    /**
     * Main function
     */
    public static void main(String[] args) throws Exception {
        // Create server

        Database database = new Database("root", "root", "localhost", 3306, "unwdmi");

        Server server = new Server(10); // 10 clients

        // Run server (Could make a thread of it)
        server.run();
    }


    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("New connection with client# " + clientNumber + " at " + socket);
        }


        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                boolean start_file = false;
                boolean start_measuring = false;
                ArrayList<String> measurementData = new ArrayList<>();

                while (true) {
                    // Read line
                    String input = in.readLine(); // remove spaces
                    // No input
                    if(input == null){
                        break;
                    }

                    // Remove spaces
                    input = input.replaceAll("\\s","");
                    //input = input.trim();

                    // Start command
                    if (input.equals("<WEATHERDATA>")){
                        if(!start_file){
//                            System.out.println("Start of file");
                            start_file = true;
                        }else{
                            System.out.println("Error occurred, stopping");
                            break;
                        }

                    }else if(start_file){ // If we are reading

                        if(!start_measuring){
                            //System.out.println("THISSS Started!" + input);

                            if (input.equals("<MEASUREMENT>")) { // Start reading measurement, prepare new data

                                if(!start_measuring){
                                    addCount();

                                    start_measuring = true;
                                }else{
                                    System.out.println("Error occurred, stopping");
                                    break;
                                }
                            }
                        }else if(start_measuring){

                            if (input.equals("</MEASUREMENT>")) { // End measurement
                                start_measuring = false;
                            }else{
                                //System.out.println(input.split("[<|>]")[2]);
                                String data = input.split("[<|>]")[2];
                                measurementData.add("".equals(data) ? "0.0" : data);
                                if(measurementData.size() == 14) {
                                    Measurement.fromData(measurementData).print();
                                    measurementData = new ArrayList<>();
                                }
//                                ParseData(input);
                            }
                        }

                       if (input.equals("</WEATHERDATA>")){ // End command
//                            System.out.println("End of file");

                           System.out.println(getCount());

                            start_file = false;
                            //break; <-- cancels the program!
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close socket");
                }
            }
        }


        void ParseData(String input){
            //System.out.println("Data: " + input);

            String[] parts = input.split("[<|>]");

//            for (int i = 0; i < parts.length; i++){
//                System.out.println("Part: " + i + "  -  " + parts[i]);
//            }

//          Useless switch....
//          System.out.println(String.format("%s: %s", parts[1], parts[2]));

            switch (parts[1]){
                case "STN":
                    break;

                case "DATE":
                    System.out.println("Date: " + parts[2]);
                    break;

                case "TIME":
                    System.out.println("TIME: " + parts[2]);
                    break;

                case "TEMP":
                    System.out.println("TEMP: " + parts[2]);
                    break;
                case "DEWP":
                    System.out.println("DEWP: " + parts[2]);
                    break;
                case "STP":
                    System.out.println("STP: " + parts[2]);
                    break;
                case "SLP":
                    System.out.println("SLP: " + parts[2]);
                    break;
                case "VISIB":
                    System.out.println("VISIB: " + parts[2]);
                    break;
                case "WDSP":
                    System.out.println("WDSP: " + parts[2]);
                    break;
                case "PRCP":
                    System.out.println("PRCP: " + parts[2]);
                    break;
                case "SNDP":
                    System.out.println("SNDP: " + parts[2]);
                    break;
                case "FRSHTT":
                    System.out.println("FRSHTT: " + parts[2]);
                    break;
                case "CLDC":
                    System.out.println("CLDC: " + parts[2]);
                    break;
                case "WNDDIR":
                    System.out.println("WNDDIR: " + parts[2]);
                    break;
                default:
                    System.out.println("ERROR");
                    break;

            }

            /* int station = "STN"; // station nummer
            date = "DATE"; // datum
            time = "TIME"; // tijd
            float temp = "TEMP"; // temperatuur
            float dewp = "DEWP"; // dauwpunt
            float stp = "STP"; // luchtdruk op stationniveau
            float slp = "SLP"; // luchtdruk op zee niveau
            float visib = "VISIB"; // Zichtbaarheid in kilometers
            float wdsp = "WDSP"; // windsnelheid in km/h
            float prcp = "PRCP"; // neerslag in CM
            float sndp = "SNDP"; // sneel in cm
            int frshtt = "FRSHTT"; // gebeurtinissen (binair weergegeven)
            float cldc = "CLDC"; // bewolking in procenten
            int wnddir = "WNDDIR"; // windrichting in graden 0 - 359 */

        }

    }
}