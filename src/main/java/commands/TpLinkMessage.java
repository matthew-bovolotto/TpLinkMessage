package commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import java.nio.charset.Charset;

import java.util.Map;
import java.util.TreeMap;

public class TpLinkMessage {

    private GetCommands commands;

    private static final String IPV4_PATTERN = "^(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
    private static final byte KEY = (byte) 171;
    private static final int PORT = 9999;

    private String ipAddress;

    public TpLinkMessage(final String ipAddress){
        this.commands = new GetCommands();

        if(!validIp(ipAddress)){
            throw new Error("Not a valid IP address");
        }
        this.ipAddress = ipAddress;
    }

    public TpLinkMessage(){
        this.commands = new GetCommands();
    }

    public String getCommand(final String commandOption) throws IOException {

        if(ipAddress == null){
            throw new Error("IP address was not set");
        }

        String option = commands.getPropertyValues(commandOption);

        if(option == null){
            throw new Error("Option was not valid");
        }

        return replaceZero(getSystemInfo(ipAddress, encrypt(option)));
    }

    private boolean validIp(String ipAddress) {

        if (ipAddress.matches(IPV4_PATTERN)){
            return true;
        }
        return false;
    }

    private static String replaceZero(String output){
        return "{" + output.substring(1);
    }

    private static String getSystemInfo(String ipAddress, byte[] command){

        try{
            Socket connectionSocket = new Socket(ipAddress, PORT);

            InputStream is = connectionSocket.getInputStream();

            DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());

            out.write(command);

            byte[] result = new byte[2048];

            is.read(result);

            //System.out.println("received: " + Hex.encodeHexString(decrypt(result).toByteArray()));
            //System.out.println(decrypt(result));

            connectionSocket.close();

            return new String(decrypt(result).toByteArray());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] encrypt(String data){

        byte key = KEY;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        result.write(0);
        result.write(0);
        result.write(0);
        result.write((byte) data.length());

        byte[] encoded = data.getBytes(Charset.forName("UTF-8"));

        for(byte c : encoded){
            byte a = (byte)(key ^ c);
            key = a;
            result.write(a);
        }

        return result.toByteArray();

    }

    private static ByteArrayOutputStream decrypt(byte[] data){

        byte key = KEY;
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        for(byte c : data){
            byte a = (byte)(key ^ c);
            key = c;

            //Strip out any ascii non-character elements
            if(a > (byte) 32){
                result.write(a);
            }
        }
        return result;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {

        if(!validIp(ipAddress)){
            throw new Error("Not a valid IP address");
        }

        this.ipAddress = ipAddress;
    }
}
