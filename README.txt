Santorini

Requirements implemented:
  -Complete rules
  -CLI
  -GUI
  -Socket
  -2 FA: multiple match, advanced gods (Ares, Chronus, Hera, Hestia, Hypnus)

Coverage Report for package "Model":
  -Class: 100%
  -Method: 94%
  -Line: 87%

System requirements:
  -Have at least a screen resolution of 1920x1080 (for GUI).
  -Have installed Maven --https://maven.apache.org/
                        --http://maven.apache.org/download.cgi
  -Java SDK (see: https://www.oracle.com/it/java/technologies/javase-downloads.html)

How to create the .jar from command line?
  1-Go to the directory where is placed the source code of Santorini (stop when you see "pom.xml" file).
  2-Run: mvn clean -f pom.xml
  3-Run: mvn validate -f pom.xml
  4-Run: mvn compile -f pom.xml
  5-Run: mvn test -f pom.xml
  6-Run: mvn package -f pom.xml

How to run JUnit tests?
  -Execute the phase from 1 to 4 (of "How to create the jar. etc") if you have didnt't do it previously or simply run: mvn test -f pom.xml

How to run the Santorini-client.jar from command line?
  1- CLI version: java -jar path/Santorini-client.jar cli
  2- GUI version: java -jar path/Santorini-client.jar

How to run the Santorini-server.jar from command line?
  - CLI version: java -jar path/Santorini-server.jar

If you want to play with CLI please make sure tu use Linux or macOS beacuse Windows' CLI doesn't support colors.
So if you have Windows please follow these steps (or if you prefer use simply a virtual machine with Unix):
  1-Open "Program and Features" in the Control Center.
  2-Click on "Turn Windows features on or off".
  3-Check the Windows Subsystem for Linux option.
  4-Click the OK button.
  5-Click the Restart now button.
  6-Download "Ubuntu" from Microsoft Store and open it (or search for the Linux distribution that you want to install).
  7-Install Java from Ubuntu CLI.
  8-Exec the command "explorer.exe ." on the Ubuntu CLI.
  9-Copy or move Santorini.jar (which you have previously created) in this directory or where you prefer in subdirectory.


