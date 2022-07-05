# SANTORINI

## Authors
- Edoardo Venir
- Leonardo Ruzza
- Valerio Schips

## Requirements implemented:
- Complete rules
- CLI
- GUI
- Socket
- 2 FA: multiple match, advanced gods (Ares, Chronus, Hera, Hestia, Hypnus)

## Coverage Report for package "Model":
- Class: 100%
- Method: 95%
- Line: 91%

## System requirements:
 - Have at least a screen resolution of 1920x1080 (for GUI).
- Have installed [Maven](https://maven.apache.org/), [Maven Download](http://maven.apache.org/download.cgi)
- Have installed [Java SDK](https://www.oracle.com/it/java/technologies/javase-downloads.html)

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

## How to run the Santorini-server.jar from command line?

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
If you want to play with CLI please make sure tu use Linux or macOS because Windows' CLI doesn't support colors.\
So if you have Windows and you want to play with CLI please follow these steps (or if you prefer use simply a virtual machine with Unix):
- Open "Program and Features" in the Control Center.
- Click on "Turn Windows features on or off".
- Check the Windows Subsystem for Linux option.
- Click the OK button.
- Click the Restart now button.
- Download "Ubuntu" from Microsoft Store and open it (or search for the Linux distribution that you want to install).
- Install Java from Ubuntu CLI.
- Exec the command "explorer.exe ." on the Ubuntu CLI.
- Copy or move Santorini-client.jar (which you have previously created) in this directory or where you prefer in subdirectory.
### Run Santorini quickly
If you want to run the Santorini-client.jar or Santorini-server.jar more quickly you can use provided batch files (simple double click it to run, or edit like you prefer to change default setting of launch like showed previously for the CLI).

 Note: make sure batch files are in the same directory of the jar files.
