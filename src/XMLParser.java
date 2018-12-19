import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLParser {




    //Pattern regex = Pattern.compile("(>)(.*)(<)", Pattern.MULTILINE);

    // Named pattern
    Pattern regex = Pattern.compile("(>)(?<value>.*)(<)", Pattern.MULTILINE);

    public  XMLParser(){

    }

    public String ParseData(String input){

        //System.out.println("Input:" + input);
        //System.out.println("reg:" + regex);

        Matcher m = regex.matcher(input);

        if(m.find())
        {
            //System.out.println("FOUND MATCH:" + m.group(2));
            //return m.group(2); // Return value by index


            // Return value by name
            return m.group("value"); // Return value


        }else{
            System.out.println("No MATCH FOUND:");

            // Split manual:
            String[] parts = input.split("[<>]");

            // Return value
            return parts[2];
        }
    }
}
