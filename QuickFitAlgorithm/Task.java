package QuickFitAlgorithm;

public class Task {

    // Variable Declare
    private int taskId;
    private int memoryUsage;
    private String listName;

    // Create a Task
    public Task(int taskId, int memoryUsage, String listName) {
        this.taskId = taskId;
        this.memoryUsage = memoryUsage;
        this.listName = listName;
    }

    // The unique ID of the task.
    public int getTaskId() {
        return taskId;
    }

    // How many memory used by the task.
    public int getMemoryUsage() {
        return memoryUsage;
    }

    // The name of the memory list
    public String getListName() {
        return listName;
    }

    // Task format
    @Override
    public String toString() {
        return "Task " + taskId + " - memory usage " + memoryUsage;
    }
}
