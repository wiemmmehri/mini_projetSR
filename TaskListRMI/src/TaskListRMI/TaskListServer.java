package TaskListRMI;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TaskListServer extends UnicastRemoteObject implements TaskList {
    private final List<String> tasks;

    protected TaskListServer() throws RemoteException {
        super();
        tasks = new ArrayList<>();
    }

    public void addTask(String task) throws RemoteException {
        tasks.add(task);
    }

    public boolean removeTask(String task) throws RemoteException {
        return tasks.remove(task);
    }

    public List<String> getTasks() throws RemoteException {
        return new ArrayList<>(tasks); 
    }
}

