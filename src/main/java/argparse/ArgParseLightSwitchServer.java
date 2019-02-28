package argparse;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;

public class ArgParseLightSwitchServer {

    public static Namespace argParseProvider(String[] args) throws IOException, ArgumentParserException {

        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("TP LINK ARG PARSE");

        parser.addArgument("--switch","--switchAddress")
                .dest("SWITCH_ADDRESS")
                .type(String.class)
                .help("Specify address for switch to query")
                .required(true);

        parser.addArgument("--lights", "--lightAddresses")
                .dest("LIGHT_ADDRESSES")
                .type(String.class)
                .nargs("+")
                .required(true)
                .help("Specify address for lights controlled by the switch");
        parser.addArgument("--attempts")
                .dest("ATTEMPTS")
                .type(int.class)
                .help("Specify number of retry attempts")
                .setDefault(5)
                .required(false);

        Namespace res = parser.parseArgs(args);
        return res;

    }

}
