public class XMLParser {


    void ParseData(String input){
        //System.out.println("Data: " + input);

        String[] parts = input.split("[<|>]");

//            for (int i = 0; i < parts.length; i++){
//                System.out.println("Part: " + i + "  -  " + parts[i]);
//            }

        switch (parts[1]){
            case "STN":
                System.out.println("STN: " + parts[2]);
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
