package QuickFitAlgorithm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MemoryManager allocator = new MemoryManager();

        // Initialize the free memory unit
        allocator.initialize();
        allocator.printState();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Show the menu to user
            System.out.println("\nSelect an option : ");
            System.out.println("1 - Memory Allocation");
            System.out.println("2 - Process End");
            System.out.println("0 - Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                // Memory allocation here
                case 1:
                    System.out.println("\nHow many bytes do you wants to allocate in memory ?");
                    int size = scanner.nextInt();
                    if (size > 0) {
                        String allocationResult = allocator.allocate(size);
                        System.out.println(allocationResult);
                    }
                    break;

                // Free Memory
                case 2:
                    System.out.println("\nEnter the task number to free : ");
                    int taskId = scanner.nextInt();
                    allocator.freeTaskById(taskId);
                    break;

                // Exit the program
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                // Invalid message
                default:
                    System.out.println("Invalid, please select again...");
                    break;
            }

            // Print the current memory state
            allocator.printState();
        }
    }
}
