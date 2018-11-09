import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;
import java.util.Map;

public class main {

    public static void main(String argv[]) throws Exception {

        TpLinkMessage messager = new TpLinkMessage();

        String[] IP_ADDRESSES = {"192.168.1.80","192.168.1.81","192.168.1.82","192.168.1.83","192.168.1.85","192.168.1.86"};

        for(String IP_ADDRESS : IP_ADDRESSES){
            messager.setIpAddress(IP_ADDRESS);
            String json = messager.getCommand("info");
            System.out.println(json);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);

            JSONObject system = (JSONObject)obj;
            JSONObject get_sysinfo = (JSONObject)system.get("system");

            Map sysinfo = ((Map)get_sysinfo.get("get_sysinfo"));

            Iterator<Map.Entry> itr = sysinfo.entrySet().iterator();

            System.out.println("------------------------------------------------------------------------");

            while(itr.hasNext()){
                Map.Entry pair = itr.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }

            System.out.println("------------------------------------------------------------------------");

        }
    }
}
