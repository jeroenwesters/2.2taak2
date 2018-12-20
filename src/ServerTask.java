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
            boolean isReading = false;

            long startTime = 0;


            // Infinite loop to prevent thread from stopping
            while(true){

                // Read line
                String input = reader.readLine(); // remove spaces
                //System.out.println("CUR LINE: " + input);

                // No input
                if(input == null){
                    break;
                }

                // Remove spaces
                input = input.replaceAll("\\s","");
                //System.out.println(input);


                if(input.equals("<WEATHERDATA>")){
                    // Start of file
                    //startTime = System.nanoTime();

                }else if (input.equals("</WEATHERDATA>")){
                    // End of file
                    //stopTimer("Measurement", startTime);

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

                        // DEBUG:: Print all measured data to console.
                        //m.print();

                        // DEBUG    break;
                    }else{
                        // Convert string (to get variable)
                        //String[] data = parser.ParseData(input);
                        String data = parser.ParseData(input);


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
