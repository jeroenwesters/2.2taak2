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

    public ServerTask(Socket socket) {
        parser = new XMLParser();
        this.socket = socket;

        System.out.println(String.format("New thread started"));

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

                if(!isReading){
                    // Look for Measurement start
                    if(input.equals("<MEASUREMENT>")){
                        // Clear measurements!
                        measurementData = new ArrayList<>();

                        isReading = true;
                        //System.out.println("Start reading");
                    }
                }else if(isReading ){
                    if(input.equals("</MEASUREMENT>")){
                        // Done reading!
                        isReading = false;

                        //System.out.println("Done reading");

                        // Give data to !?
                        Measurement m = Measurement.fromData(measurementData);

                        //m.print();

                        // DEBUG
                        // break;

                        // isReading = false;
                        // handle current measurement and reset it
                    }else{
                        // Add data!

                        // Check for zero string
                        String[] data = parser.ParseData(input);


                        for (int i = 0; i < data.length; i++){
                            if(data[i].equals("")){
                                data[i] = "0";
                            }
                        }


                        measurementData.add(data[2]);

                    }


                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client ");
        } finally {
            try {
                System.out.println("Closing socket");

                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close socket");
            }
        }



        // Infinite loop, to keep the thread alive.
        while (true) {
//            try
//            {
//                // Debug
//                Thread.sleep(1000);
//
//
//            }
//            catch (InterruptedException ex) {
//                System.out.println("Exception encounterted");
//            }
//
//            System.out.println(Thread.currentThread().getName() +
//                    " finished executing");
//
//            Server.RemoveClient();
//            break;


            // Read line
            //

            // When closing: Server.RemoveClient();






        }
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
