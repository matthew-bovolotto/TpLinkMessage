package main;

import commands.GetCommands;
import commands.TpLinkMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String argv[]) throws Exception {

        TpLinkMessage messager = new TpLinkMessage();

        //String[] IP_ADDRESSES = {"192.168.1.80","192.168.1.81","192.168.1.82","192.168.1.83","192.168.1.85","192.168.1.86"};
        String[] IP_ADDRESSES = {"192.168.1.80"};

        for(String IP_ADDRESS : IP_ADDRESSES){
            messager.setIpAddress(IP_ADDRESS);
            String json = messager.getCommand(argv[0]);
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Return: " + json);

            //TODO: Add class for parsing json returns

            GetCommands commands = new GetCommands();
            String property = commands.getPropertyValues(argv[0]);

            System.out.println("------------------------------------------------------------------------");
            System.out.println("Property: '" + property + "'");
            Pattern pattern = Pattern.compile("\"(.*?)\".*?\"(.*?)\"");
            Matcher matcher = pattern.matcher(property);

            if(matcher.find()) {
                JSONParser parser = new JSONParser();

                Object obj = parser.parse(json);

                JSONObject system = (JSONObject) obj;
                JSONObject get_sysinfo = (JSONObject) system.get(matcher.group(1));

                Map sysinfo = ((Map) get_sysinfo.get(matcher.group(2)));

                Iterator<Map.Entry> itr = sysinfo.entrySet().iterator();

                System.out.println("------------------------------------------------------------------------");
                while (itr.hasNext()) {
                    Map.Entry pair = itr.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                }
                System.out.println("------------------------------------------------------------------------");
            }
        }
    }
}