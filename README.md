Distributed Systems Project
This project implements servers and clients using three distributed communication technologies in Java: Java RMI, gRPC, and sockets. It aims to understand the workings and differences between these technologies by implementing specific functionalities in each system.

Technologies Used
Java RMI
gRPC
Sockets
Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
Java JDK 8 or higher
Maven (for gRPC)
Protocol Buffers (for gRPC, if not included in your gRPC framework)
Installing
Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/wiemmmehri/mini_projetSR.git
Navigate to the project directory:


cd your-repository-name


Java RMI
Deploying the RMI Server
Compile the Java files:
javac RMI/*.java
Start the RMI registry:
rmiregistry &
Run the server:
java RMI.Server
Run the client application:
java RMI.Client
Follow the on-screen instructions to add, delete, and list tasks.


gRPC:
cd gRPC
mvn compile
mvn exec:java -Dexec.mainClass="gRPC.Server"
mvn exec:java -Dexec.mainClass="gRPC.Client"



Sockets

cd Sockets
Compile and run the server:
javac ChatServer.java
Connecting with the Chat Client
Open a new terminal window for each client.
Compile and run the client:
javac ChatClient.java
