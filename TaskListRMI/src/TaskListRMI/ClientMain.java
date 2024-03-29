package TaskListRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null); 
            TaskList stub = (TaskList) registry.lookup("TaskListService");
            Scanner scanner = new Scanner(System.in);
            String choice;
            
            do {
                System.out.println("\nMenu:");
                System.out.println("A - Ajouter une tâche");
                System.out.println("D - Supprimer une tâche");
                System.out.println("S - Afficher la liste des tâches");
                System.out.println("Q - Quitter");
                System.out.print("Choisissez une option: ");
                choice = scanner.nextLine().toUpperCase();
                
                switch (choice) {
                    case "A":
                        System.out.print("Entrez la tâche à ajouter: ");
                        String taskToAdd = scanner.nextLine();
                        stub.addTask(taskToAdd);
                        System.out.println("Tâche ajoutée.");
                        break;
                    case "D":
                        System.out.print("Entrez la tâche à supprimer: ");
                        String taskToRemove = scanner.nextLine();
                        if (stub.removeTask(taskToRemove)) {
                            System.out.println("Tâche supprimée.");
                        } else {
                            System.out.println("Tâche introuvable.");
                        }
                        break;
                    case "S":
                        List<String> tasks = stub.getTasks();
                        System.out.println("Liste des tâches:");
                        tasks.forEach(System.out::println);
                        break;
                    case "Q":
                        System.out.println("Quitter.");
                        break;
                    default:
                        System.out.println("Option invalide.");
                }
            } while (!choice.equals("Q"));
            
        } catch (Exception e) {
            System.err.println("Erreur client: " + e.toString());
            e.printStackTrace();
        }
    }
}
