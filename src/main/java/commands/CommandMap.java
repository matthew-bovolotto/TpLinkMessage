package commands;

import java.util.Map;
import java.util.TreeMap;

public class CommandMap {

    private static final Map<String,String> commandOptions = new TreeMap<String,String>();

    public CommandMap(){
        commandOptions.put("info","{\"system\":{\"get_sysinfo\":{}}}");
        commandOptions.put("time","{\"time\":{\"get_time\":{}}}");
    }

    public Map<String,String> getComandMap(){
        return commandOptions;
    }
}
