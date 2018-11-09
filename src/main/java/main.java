public class main {

    private static final String IP_ADDRESS = "192.168.1.81";

    public static void main(String argv[]) throws Exception {

        TpLinkMessage messager = new TpLinkMessage(IP_ADDRESS);

        System.out.println(messager.getCommand("info"));

    }
}
