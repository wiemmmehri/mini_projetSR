package TaskListRMI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaskList extends Remote {
    void addTask(String task) throws RemoteException;
    boolean removeTask(String task) throws RemoteException;
    List<String> getTasks() throws RemoteException;
}