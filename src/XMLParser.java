public class XMLParser {


    public String[] ParseData(String input){
        //System.out.println("Data: " + input);

        String[] parts = input.split("[<|>]");

//        for (int i = 0; i < parts.length; i++){
//            System.out.println("Part: " + i + "  -  " + parts[i]);
//        }



        return parts;
    }
}
