# TP Link Smart Home Tools

Tool set created to communicate locally and directly to the TP Link smart home line of products (https://www.tp-link.com/us/kasa-smart/)

## Getting Started

**Prerequisites:**

The project is written in Java and built using Gradle. Gradle can be installed using https://sdkman.io/install

**Installation:**

To build the project, clone the git repository and run

```
gradle installDist
```

This will install the project to <PROJECT_DIRECTORY>/build/install/

## Library

The [TpLinkMessage](src/main/java/commands/TpLinkMessage.java) class is the main library of methods used by tools in this project. 

The GetCommands object is able to send and receive messages to supporting TP Link devices.

The set of tested commands is located in the [commandOptions.properties](src/main/resources/commandOptions.properties) file. A full set of commands is located in
 the [tplink-smarthome-commands.txt](tplink-smarthome-commands.txt) file.
 
A TpLinkMessage object can be created simply calling the constructor:
```
public TpLinkMessage(final String ipAddress)

public TpLinkMessage() //Must set the IP Address separately with
                       //public setIpAddress(final String ipAddress)
```

The TpLinkMessage object provides the getCommands method. This method allows the passing of a command (String) 
which is encrypted, sent to the TP Link device, the returned message from the device is then decrypted and
returned as a String.

**Example:**
```
String SWITCH_ADDRESS = "192.168.1.10";
TpLinkMessage switchMessager = new TpLinkMessage(SWITCH_ADDRESS);

System.out.println(switchMessager.getCommand("info"));
```
**Return:**

*Some information has been removed from the returned JSON*
```
{  
   "system":{  
      "get_sysinfo":{  
         "sw_ver":"1.5.1Build171109Rel.165709",
         "hw_ver":"2.0",
         "type":"IOT.SMARTPLUGSWITCH",
         "model":"HS100(US)",
         "mac":"--",
         "dev_name":"SmartWi-FiPlug",
         "alias":"--",
         "relay_state":1,
         "on_time":3471,
         "active_mode":"none",
         "feature":"TIM",
         "updating":0,
         "icon_hash":"",
         "rssi":-43,
         "led_off":0,
         "longitude_i":--,
         "latitude_i":--,
         "hwId":"--",
         "fwId":"00000000000000000000000000000000",
         "deviceId":"--",
         "oemId":"--",
         "err_code":0
      }
   }
}
```

## Tools

**printReturnMessage:**

**lightSwitchServer:**

## Acknowledgment

The reverse engineering of the TP Link protocol and commands was done by the following project:

https://github.com/softScheck/tplink-smartplug

