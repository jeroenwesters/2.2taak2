import model.Measurement;

import javax.xml.stream.FactoryConfigurationError;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

// Handles server functions
public class ServerTask extends Thread {
    private Socket socket = null;;
    private XMLParser parser = null;


    /**
     * Constructor
     * @param socket
     */
    public ServerTask(Socket socket) {
        parser = new XMLParser();
        this.socket = socket;

        //System.out.println(String.format("New thread started"));

    }


    /**
     * Start thread
     */
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create data container
            List<String> measurementData = new ArrayList<>();

            boolean fileStart = false;
            boolean isReading = false;


            long startTime = 0;

            String input = "";

            // Infinite loop to prevent thread from stopping
            while(true){

                // Read line
                input = reader.readLine(); // remove spaces
                //System.out.println("CUR LINE: " + input);

                // No input
                if(input == null){
                    System.out.println("No input received, canceling");
                    break;
                }

                // Remove spaces
                input = input.replaceAll("\\s","");

                // Check if the file starts or ends!
                if(input.equals("<WEATHERDATA>")){
                    startTime = System.nanoTime();
                    fileStart = true;

                }else if (input.equals("</WEATHERDATA>")){
                    // End of file
                    fileStart = false;
                    stopTimer("Measurement", startTime);
                }else{

                }


                if(!isReading){
                    // Look for Measurement start
                    if(input.equals("<MEASUREMENT>")){
                        // Clear measurements!
                        measurementData.clear();

                        // Start reading the file
                        isReading = true;
                    }
                }else if(isReading ){
                    // If it is the end of a measurement
                    if(input.equals("</MEASUREMENT>")){
                        // Done reading!
                        isReading = false;

                        //System.out.println("Done reading");

                        // Give data to !?
                        Measurement m = Measurement.fromData(measurementData);

                        //m.print();
                    }else{
                        // Convert string (to get variable)
                        //String[] data = parser.ParseData(input);
                        String data = parser.ParseData(input); // Change the 1 !!!


                        // Check for zero value

                        if(data.equals("")){
                            //System.out.println("MISSING VALUE" + data);
                            data = "0";
                        }else{
                            //System.out.println("Good data: " + data);
                        }

                        measurementData.add(data);
                    }
                }

                input = "";
            }
        } catch (IOException e) {
            System.out.println("Error handling client ");
        } finally {
            try {
                //System.out.println("Closing socket");

                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close socket");
            }
        }
    }


    private void stopTimer(String desc, long startTime){
        long stopTime = (System.nanoTime() - startTime);

        double sec = (double)stopTime / 1000000000;

        System.out.println(String.format("%s timer stopped after: %f seconds", desc, sec));
    }


    private String CorrectMissingData(String[] oldValues, String input){

        return null;
    }


    private void HandleData(String[] data){

        for (int i = 1; i < data.length; i++){
            System.out.println("ID " + data[i]);

            if(data[i].equals("")){
                for (int x = 0; x < 1000; x++){
                    System.out.println("MISSING VARIABLE ");

                }
            }
        }

            /*

            EXAMPLE DATA!


             */

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
