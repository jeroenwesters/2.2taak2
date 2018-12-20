import model.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



// Handles server functions
public class ServerTask extends Thread {

    // Generator settings (amount of stations and measurements)
    private final int amount_stations = 10;
    private  final int amount_measurements = 14;
    private  final int max_backlog = 30; // Amount of saved values

    private Socket socket = null;;
    private XMLParser parser = null;


    // Multidimensional array containing stations, measurements and data
    String stationData[][][] = new String[amount_stations][amount_measurements][max_backlog];
    int currentStation = 0;
    int currentMeasurement = 0;
    int currentBacklog = 0;


//    int values_avarage[] = {
//            3,  // temp
//    };

    private final int use_previous[] = {
            0,
            1,
            2,
            13,
    };

    private final int temp_id = 3;


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

                //System.out.println(stationData[0][0][0]);

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
                    currentStation = 0;

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
                        currentStation++;
                        currentMeasurement = 0;


                        // Done reading!
                        isReading = false;

                        //System.out.println("Done reading");

                        // Give data to !?
                        //Measurement m = Measurement.fromData(measurementData);

                        //m.print();
                    }else{
                        // Convert string (to get variable)
                        //String[] data = parser.ParseData(input);
                        String data = parser.ParseData(input); // Change the 1 !!!
                        //System.out.println(data);


                        // Check for zero value
                        if(data.equals("")){
                            boolean usePrevious = false;

                            // No data, check if we can use previous data:
                            for(int d = 0; d < use_previous.length; d++){
                                if(currentMeasurement == use_previous[d]){
                                    // Todo:
                                    // use previous data
                                    usePrevious = true;
                                }
                            }

                            if(usePrevious){
                                // Todo: get old value
                                data = "My OLD VALUE";

                            }else{
                                // Todo: calculate avarage
                                data = "My AVARAGE VALUE";
                            }

                        }


                        if(currentMeasurement == temp_id){
                            //System.out.println("Temperature data: " + data);

                            // Todo: Check for max 20% difference
                            // For now:
                            stationData[currentStation][currentMeasurement][currentBacklog] = data;

                        }else{
                            // Todo: assign the data
                            //System.out.println(currentMeasurement + " - data: " + data);

                            stationData[currentStation][currentMeasurement][currentBacklog] = data;

                        }

                        //System.out.println(stationData[currentStation][currentMeasurement][currentBacklog]);
                        currentMeasurement++;

                        measurementData.add(data);

                        if(currentMeasurement > 13){
                            //break;
                        }
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


    private String CorrectMissingData(String data[][][], int Station, int measurement, int dataIndex, String input){


        // Fill each row with 1.0
        for (int sd = 0; sd < stationData.length; sd++){
            System.out.println();
            System.out.print(sd);
            System.out.print(": ");

            for(int md = 0; md < stationData[sd].length; md++){
                System.out.print("..");
                System.out.print(md);

            }
        }

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
