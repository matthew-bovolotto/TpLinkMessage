package commandsTest

import spock.lang.Specification;

import commands.GetCommands

import java.nio.file.Files
import java.nio.file.Paths


class GetCommandsTest extends Specification{

    def "[GetCommandsTest]: Properties Test"(String command){
        given:
            Files.copy(Paths.get("src/test/resources/nullCommandOptions.properties"),Paths.get("src/test/resources/commandOptions.properties"))
        when:
            println("commandOptions:" + new File("src/test/resources/commandOptions.properties").text)
            GetCommands getCommands = new GetCommands();
            ArrayList<String> commandList = getCommands.getPropertyList();
        then:
            println(commandList + " : " + command);
            commandList.contains(command);
        cleanup:
            new File("src/test/resources/commandOptions.properties").delete()
        where:
            command << ["reboot","off","info","wlanscan","schedule","reset","on","antitheft","countdown","cloudinfo"]

    }

    /*def "[GetCommandsTest]: Missing Properties File"(){
        given:
            Files.copy(Paths.get("src/test/resources/nullCommandOptions.properties"),Paths.get("src/test/resources/commandOptions.properties"))
        when:
            GetCommands getCommands = new GetCommands();
            ArrayList<String> commandList = getCommands.getPropertyList();
        then:
            println commandList.toString()
            commandList == ""
        cleanup:
            new File("src/test/resources/commandOptions.properties").delete()
    }*/

}
