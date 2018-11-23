package argparse;

import commands.GetCommands;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.util.ArrayList;

public class ArgParseTpLink {

    public static Namespace argParseProvider(String args[]) throws IOException, ArgumentParserException {

        GetCommands command = new GetCommands();
        ArrayList<String> arrayList = command.getPropertyList();

        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("TP LINK ARG PARSE");
        parser.addArgument("--ip","--ipAddress")
                .dest("ipAddress")
                .type(String.class)
                .help("Specify address to send command to")
                .required(true);
        parser.addArgument("-c", "--command")
                .dest("command")
                .choices(arrayList)
                .help("command to submit to tp link control module")
                .required(true);

        Namespace res = parser.parseArgs(args);
        return res;

    }
}