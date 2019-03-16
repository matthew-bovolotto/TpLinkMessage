package argparseTest

import commands.GetCommands
import argparse.ArgParsePrintReturnMessage

import net.sourceforge.argparse4j.inf.ArgumentParserException
import net.sourceforge.argparse4j.inf.Namespace
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.DirtiesContext;
import spock.lang.Shared
import spock.lang.Specification;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption
import java.nio.file.Path
import java.nio.file.Paths;

@EnableAutoConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ArgParsePrintReturnMessageTest extends Specification{

    @Shared
    List<String> commandList = new GetCommands().getPropertyList();

    def "[ArgParsePrintReturnMessageTest]: no arguments"(){
        given:
            String[] args = ""
        when:
            Namespace namespace = new ArgParsePrintReturnMessage(args).getNamespace()
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())
    }

    def "[ArgParsePrintReturnMessageTest]: empty arguments"(String[] args){
        when:
            Namespace namespace = new ArgParsePrintReturnMessage(args).getNamespace()

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
        given:
            Files.copy(Paths.get("src/test/resources/minCommandOptions.properties"),Paths.get("src/test/resources/commandOptions.properties"),StandardCopyOption.REPLACE_EXISTING)

        when:
            println("commandOptions:" + new File("src/test/resources/commandOptions.properties").text)
            String[] args = ["--ipAddress","127.0.0.1","--command",command, "--help"]
            println(args)
            Namespace namespace = new ArgParsePrintReturnMessage(args).getNamespace()

        then:
            def e = thrown(Exception)
            println(e.getMessage())
            //namespace.getString("ipAddress") == "127.0.0.1"
            //namespace.getString("command") == command

        cleanup:
            Files.copy(Paths.get("src/test/resources/testCommandOptions.properties"),Paths.get("src/test/resources/commandOptions.properties"),StandardCopyOption.REPLACE_EXISTING)

        where:
            command << commandList
    }

    def "[ArgParsePrintReturnMessageTest]: help menu"(String[] args){
        when:
            Namespace namespace = new ArgParsePrintReturnMessage(args).getNamespace()
        then:
            def e = thrown(ArgumentParserException)
            println(e.getMessage())

        where:
            args << [["--help"],["-h"]]
    }
}
