package argparseTest

import net.sourceforge.argparse4j.inf.ArgumentParserException
import spock.lang.Specification;
import net.sourceforge.argparse4j.inf.Namespace;
import argparse.ArgParseLightSwitchServer;

class ArgParseLightSwitchServerTest extends Specification {

    Namespace namespace;

    def "no arguments"(){
        given:
            String[] args = ""
        when:
            namespace = ArgParseLightSwitchServer.argParseProvider(args)
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())
    }

    def "empty arguments"(String[] args){
        when:
            namespace = ArgParseLightSwitchServer.argParseProvider(args)

        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())

        where:
            args << [["--switch", "--lights", "127.0.0.1", "--attempts", "10"],
                    ["--switch", "127.0.0.1", "--lights", "--attempts", "10"],
                    ["--switch", "127.0.0.1", "--lights", "127.0.0.1", "--attempts"]]
    }

    def "valid arguments"(String[] args, List<String> lightVal, int attemptVal){
        when:
            namespace = ArgParseLightSwitchServer.argParseProvider(args)

        then:
            namespace.getString("SWITCH_ADDRESS") == "127.0.0.1"
            (List<String>) namespace.get("LIGHT_ADDRESSES") == lightVal
            namespace.get("ATTEMPTS") == attemptVal

        where:
            args << [["--switch", "127.0.0.1", "--lights", "127.0.0.1"],
                     ["--switch", "127.0.0.1", "--lights", "127.0.0.1", "127.0.0.1"],
                     ["--switch", "127.0.0.1", "--lights", "127.0.0.1", "--attempts", "10"],
                     ["--switch", "127.0.0.1", "--lights", "127.0.0.1", "127.0.0.1", "--attempts", "10"]]

            lightVal << [["127.0.0.1"],
                         ["127.0.0.1","127.0.0.1"],
                         ["127.0.0.1"],
                         ["127.0.0.1","127.0.0.1"]]

            attemptVal << [5,5,10,10]
    }

    def "help menu"(String[] args){
        when:
            namespace = ArgParseLightSwitchServer.argParseProvider(args)
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())

        where:
            args << [["--help"],["-h"]]

    }
}
