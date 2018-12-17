package main;

import argparse.ArgParseLightSwitchServer;
import commands.TpLinkMessage;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.List;

public class LightSwitchServer {

    public static void main(String[] argv) throws Exception {

        try {

            Namespace res = ArgParseLightSwitchServer.argParseProvider(argv);

            String SWITCH_ADDRESS = res.getString("SWITCH_ADDRESS");
            List<String> LIGHT_ADDRESSES = (List<String>) res.get("LIGHT_ADDRESSES");
            int ATTEMPTS = res.get("ATTEMPTS");

            try {

                startupMessage(SWITCH_ADDRESS,LIGHT_ADDRESSES);

                TpLinkMessage switchMessager = new TpLinkMessage(SWITCH_ADDRESS);

                Boolean switchState = false;
                Boolean prevSwitchState = false;
                int switchPolls = 0;

                while (true) {

                    String switchInfo = null;
                    int hostAttempts = 0;


                    do {
                        try {
                            switchInfo = switchMessager.getCommand("info");

                            if(hostAttempts > 0){
                                System.out.println("------------------------------------------------------------------------");
                                System.out.println("Connection reattempt successful - polling light switch");
                                System.out.println("------------------------------------------------------------------------");
                            }
                            switchPolls++;
                            System.out.print("\r(" + switchPolls + ") - Switch : " + SWITCH_ADDRESS);
                            hostAttempts = 0;

                        } catch (Exception e) {

                            hostAttempts++;
                            System.out.println("------------------------------------------------------------------------");
                            System.out.println("Could not connect to host - reattempting connection in 5 seconds (" + hostAttempts + ")");
                            System.out.println("------------------------------------------------------------------------");
                            Thread.sleep(5000);
                            if(hostAttempts >= ATTEMPTS){
                                System.out.println(e.getMessage());
                                System.exit(1);
                            }
                        }

                    } while(hostAttempts != 0);

                    switchState = checkState(switchInfo);

                    if (prevSwitchState != switchState) {
                        System.out.println("Change of state!");
                        stateChange(LIGHT_ADDRESSES);
                    }

                    prevSwitchState = switchState;
                }

            } catch (java.lang.Error error) {
                System.out.println(error.getMessage());
                System.exit(1);
            }

        }catch(ArgumentParserException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static Boolean checkState(String info){
        if(info.matches(".*\"relay_state\":0.*")){
            return false;
        }else{
            return true;
        }
    }

    private static void stateChange(List<String> LIGHTS) throws Exception{

        Boolean lightState = false;

        lightState = messageAll(LIGHTS,"info");

        if(lightState){
            messageAll(LIGHTS,"off");
        }else{
            messageAll(LIGHTS,"on");
        }

    }

    private static Boolean messageAll(List<String> LIGHTS, String command) throws Exception{

        TpLinkMessage messager = new TpLinkMessage();

        Boolean lightState = false;

        for(String lights : LIGHTS){
            messager.setIpAddress(lights);
            String info = messager.getCommand(command);

            if(checkState(info)){
                lightState = true;
            }
        }
        return lightState;
    }

    private static void startupMessage(String SWITCH_ADDRESS, List<String> LIGHT_ADDRESSES){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Light Switch Server Started:");
        System.out.println("Light Switch Address: " + SWITCH_ADDRESS);
        System.out.println("Light Addresses: " + LIGHT_ADDRESSES);
        System.out.println("------------------------------------------------------------------------");
    }

}