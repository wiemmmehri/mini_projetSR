package TaskListRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            TaskListServer obj = new TaskListServer();
            Registry registry = LocateRegistry.createRegistry(1099); 
            registry.rebind("TaskListService", obj);
            System.out.println("Serveur RMI prêt.");
        } catch (Exception e) {
            System.err.println("Erreur serveur: " + e.toString());
            e.printStackTrace();
        }
    }
}

