package QuickFitAlgorithm;

import java.util.ArrayList;

public class MemoryManager {

    // Memory lists, 'categorized according to the size'
    private ArrayList<MemoryUnit> listSmall = new ArrayList<>();
    private ArrayList<MemoryUnit> listMedium = new ArrayList<>();
    private ArrayList<MemoryUnit> listLarge = new ArrayList<>();

    // List running tasks
    private ArrayList<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 1;

    // Method to allocate memory
    public String allocate(int size) {
        MemoryUnit allocatedUnit = null;
        String listName = "";

        // Check to allocate from the small list
        if (size <= 30 && !listSmall.isEmpty()) {
            allocatedUnit = allocateFromList(listSmall, size);
            listName = "Small list";
        }

        // Check to allocate from the medium list
        else if (size <= 80 && !listMedium.isEmpty()) {
            allocatedUnit = allocateFromList(listMedium, size);
            listName = "Medium list";
        }

        // Check to allocate from the large list
        else if (size <= 200 && !listLarge.isEmpty()) {
            allocatedUnit = allocateFromList(listLarge, size);
            listName = "Large list";
        }

        // Check if no suitable unit found in any list
        if (allocatedUnit == null) {
            boolean isOutOfMemory = listSmall.isEmpty() && listMedium.isEmpty() && listLarge.isEmpty();
            if (isOutOfMemory) {
                return "\nMemory filled : All memory units are filled.";
            } else {
                return "\nOut of memory : No suitable memory units found.";
            }
        }

        // Allocate memory and display the result
        tasks.add(new Task(taskIdCounter++, size, listName));
        return "\nAllocation successful: Allocated " + size + " bytes.\nStored List : " + listName
                + "\n#########################################";
    }

    // Allocate memory from a list
    private MemoryUnit allocateFromList(ArrayList<MemoryUnit> list, int requestSize) {
        for (int i = 0; i < list.size(); i++) {
            MemoryUnit unit = list.get(i);

            // Remove the unit from the list
            if (unit.getSize() >= requestSize) {
                list.remove(i);

                // Take the required amount and send the remaining to the list.
                if (unit.getSize() > requestSize) {
                    int remainingSize = unit.getSize() - requestSize;
                    MemoryUnit remainingUnit = new MemoryUnit(remainingSize);
                    list.add(i, remainingUnit);
                }
                return new MemoryUnit(requestSize);
            }
        }
        return null;
    }

    // Method to free memory and return it to the list
    public void free(MemoryUnit unit, String listName) {
        ArrayList<MemoryUnit> targetList = null;
        if (listName.equals("Small list")) {
            targetList = listSmall;
        } else if (listName.equals("Medium list")) {
            targetList = listMedium;
        } else if (listName.equals("Large list")) {
            targetList = listLarge;
        }

        if (targetList != null) {
            int insertIndex = 0;
            for (int i = 0; i < targetList.size(); i++) {
                if (unit.getSize() < targetList.get(i).getSize()) {
                    insertIndex = i;
                    break;
                } else {
                    insertIndex = i + 1;
                }
            }

            targetList.add(insertIndex, unit);
            sortMemoryList(targetList);
        }
    }

    // Sort the memory unit list by size
    private void sortMemoryList(ArrayList<MemoryUnit> list) {
        list.sort((unit1, unit2) -> Integer.compare(unit1.getSize(), unit2.getSize()));
    }

    // Method to free a task by task ID
    public void freeTaskById(int taskId) {
        Task taskToFree = null;
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                taskToFree = task;
                break;
            }
        }

        if (taskToFree != null) {
            // Remove the task from the running tasks
            tasks.remove(taskToFree);
            System.out.println("Task " + taskId + " - memory usage " + taskToFree.getMemoryUsage() + " freed.");

            // Free the memory unit and return it to the list
            int taskMemorySize = taskToFree.getMemoryUsage();
            String taskListName = taskToFree.getListName();
            MemoryUnit unitToReturn = new MemoryUnit(taskMemorySize);

            free(unitToReturn, taskListName);
        } else {
            System.out.println("Task ID not found!");
        }
    }

    // Method to print the state of the lists
    public void printState() {
        System.out.println("\n******* Current memory state *******");
        System.out.println("Small list " + listToString(listSmall));
        System.out.println("Medium list " + listToString(listMedium));
        System.out.println("Large list " + listToString(listLarge));

        // Display the running tasks and their memory usage
        System.out.println("\n******* Current running tasks *******");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private String listToString(ArrayList<MemoryUnit> list) {
        if (list.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getSize());
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // initialize the memory lists
    public void initialize() {
        listSmall.add(new MemoryUnit(20));
        listSmall.add(new MemoryUnit(30));
        listMedium.add(new MemoryUnit(50));
        listMedium.add(new MemoryUnit(80));
        listLarge.add(new MemoryUnit(100));
        listLarge.add(new MemoryUnit(200));
    }
}
