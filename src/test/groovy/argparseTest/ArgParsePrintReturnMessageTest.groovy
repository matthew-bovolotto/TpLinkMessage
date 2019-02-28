package argparseTest

import commands.GetCommands
import net.sourceforge.argparse4j.inf.ArgumentParserException
import spock.lang.Shared
import spock.lang.Specification;
import net.sourceforge.argparse4j.inf.Namespace;
import argparse.ArgParsePrintReturnMessage;

class ArgParsePrintReturnMessageTest extends Specification{

    Namespace namespace;
    @Shared List<String> commandList = new GetCommands().getPropertyList();

    def "[ArgParsePrintReturnMessageTest]: no arguments"(){
        given:
            String[] args = ""
        when:
            namespace = ArgParsePrintReturnMessage.argParseProvider(args)
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())
    }

    def "[ArgParsePrintReturnMessageTest]: empty arguments"(String[] args){
        when:
            namespace = ArgParsePrintReturnMessage.argParseProvider(args)

        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())

        where:
            args << [["--ipAddress", "--command", "info"],
                     ["--ipAddress", "-c", "info"],
                     ["--ip", "--command", "info"],
                     ["--ip", "-c", "info"],
                     ["--ipAddress", "127.0.0.1", "--command"],
                     ["--ipAddress", "127.0.0.1", "-c"],
                     ["--ip", "127.0.0.1", "--command"],
                     ["--ip", "127.0.0.1", "-c"],
                     ["--ipAddress", "127.0.0.1"],
                     ["--ip", "127.0.0.1"],
                     ["-c","info"],
                     ["--command","info"]]
    }

    def "[ArgParsePrintReturnMessageTest]: valid arguments"(String command){
        when:
            String[] args = ["--ipAddress","127.0.0.1","--command",command]
            namespace = ArgParsePrintReturnMessage.argParseProvider(args)

        then:
            namespace.getString("ipAddress") == "127.0.0.1"
            namespace.getString("command") == command

        where:
            command << commandList
    }

    def "[ArgParsePrintReturnMessageTest]: help menu"(String[] args){
        when:
            namespace = ArgParsePrintReturnMessage.argParseProvider(args)
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())

        where:
            args << [["--help"],["-h"]]
    }
}
