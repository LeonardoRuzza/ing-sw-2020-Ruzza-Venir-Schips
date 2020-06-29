# SANTORINI

## Requirements implemented:
  -Complete rules\
  -CLI
  -GUI
  -Socket
  -2 FA: multiple match, advanced gods (Ares, Chronus, Hera, Hestia, Hypnus)

## Coverage Report for package "Model":
  -Class: 100%
  -Method: 94%
  -Line: 87%

## System requirements:
Have at least a screen resolution of 1920x1080 (for GUI).
Have installed [Maven] (https://maven.apache.org/), [Maven Download] (http://maven.apache.org/download.cgi)
Have installed [Java SDK] (https://www.oracle.com/it/java/technologies/javase-downloads.html)

# How to run JUnit tests?
```bash
mvn test -f pom.xml
```


# Usage

## How to create the .jar from command line?
Go to the directory where is placed the source code of Santorini (stop when you see "pom.xml" file).
```bash
cd [path to directory]
mvn clean -f pom.xml
mvn validate -f pom.xml
mvn compile -f pom.xml
mvn test -f pom.xml
mvn package -f pom.xml
```

## How to run the Santorini-client.jar from command line?
### CLI
```bash
java -jar path/Santorini-client.jar cli
```
### GUI
```bash
java -jar path/Santorini-client.jar
```

## How to run the Santorini-server.jar from command line?

```bash
java -jar path/Santorini-server.jar
```

## How to run the Santorini-client.jar with a not default IP address and PORT?
Needs to specify both ip and port
### CLI
```bash
java -jar path/Santorini-client.jar cli IP PORT
```
### GUI
```bash
java -jar path/Santorini-client.jar IP PORT
```

## How to run the Santorini-server.jar with a not default PORT?
```bash
java -jar path/Santorini-server.jar PORT
```

Note: insert the IP address instead of "IP" and the PORT's number instead of "PORT"!

# ONLY FOR WINDOWS' USERS
If you want to play with CLI please make sure tu use Linux or macOS because Windows' CLI doesn't support colors.
So if you have Windows and you want to play with CLI please follow these steps (or if you prefer use simply a virtual machine with Unix):
  1-Open "Program and Features" in the Control Center.
  2-Click on "Turn Windows features on or off".
  3-Check the Windows Subsystem for Linux option.
  4-Click the OK button.
  5-Click the Restart now button.
  6-Download "Ubuntu" from Microsoft Store and open it (or search for the Linux distribution that you want to install).
  7-Install Java from Ubuntu CLI.
  8-Exec the command "explorer.exe ." on the Ubuntu CLI.
  9-Copy or move Santorini-client.jar (which you have previously created) in this directory or where you prefer in subdirectory.
