import java.util.Scanner;

public class Main {
    private static final int INVALID_INDEX = -1;
    private static final int INITIAL_TASKS_CAPACITY = 5;

    private static String[] tasks = new String[INITIAL_TASKS_CAPACITY];
    private static int currentTasksCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Программа управления задачами.");
        System.out.println("Доступные команды: add, delete, search, update, list, exit");

        while (true) {
            System.out.print("Введите команду:");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    System.out.print("Введите текст задачи: ");
                    addTask(scanner.nextLine());
                    break;
                case "delete":
                    System.out.print("Введите номер задачи для удаления: ");
                    deleteTask(readTaskIndex(scanner));
                    break;
                case "search":
                    System.out.print("Введите текст для поиска: ");
                    searchTask(scanner.nextLine());
                    break;
                case "update":
                    System.out.print("Введите номер задачи для редактирования: ");
                    int updateIndex = readTaskIndex(scanner);

                    System.out.print("Введите новый текст задачи: ");
                    String newText = scanner.nextLine();

                    updateTask(updateIndex, newText);
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    System.out.println("Завершение работы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неизвестная команда. Доступные: add, delete, search, update, list, exit");
            }
        }
    }
// тестовый коммент
    private static void addTask(String name) {
        ensureCapacity();
        tasks[currentTasksCount++] = name;
        System.out.println("Задача добавлена.");
    }

    // увеличиваем массив задач в 2 раза при заполнении
    private static void ensureCapacity() {
        if (currentTasksCount < tasks.length) {
            return;
        }
        String[] newTasksArray = new String[tasks.length * 2];
        for (int i = 0; i < tasks.length; i++) {
            newTasksArray[i] = tasks[i];
        }
        tasks = newTasksArray;
    }

    private static void deleteTask(int taskIndex) {
        if (isInvalidIndex(taskIndex)) {
            return;
        }
        shiftLeftTasksFrom(taskIndex - 1);
        System.out.println("Задача удалена.");
    }

    private static void searchTask(String query) {
        boolean found = false;
        for (int i = 0; i < currentTasksCount; i++) {
            if (tasks[i].toLowerCase().contains(query.toLowerCase())) {
                System.out.printf("%d. %s%n", i + 1, tasks[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Задачи, соответствующие запросу, не найдены.");
        }
    }

    private static void updateTask(int taskIndex, String newTaskText) {
        if (isInvalidIndex(taskIndex)) {
            return;
        }
        tasks[taskIndex - 1] = newTaskText;
        System.out.println("Задача обновлена.");
    }

    private static boolean isInvalidIndex(int taskIndex) {
        boolean indexInvalid = taskIndex < 1 || taskIndex > currentTasksCount;
        if (indexInvalid) {
            System.out.println("Задачи с таким номером не существует.");
        }
        return indexInvalid;
    }

    private static void listTasks() {
        if (currentTasksCount == 0) {
            System.out.println("Список задач пуст.");
            return;
        }
        System.out.println("Текущий список задач:");
        for (int taskIndex = 0; taskIndex < currentTasksCount; taskIndex++) {
            System.out.printf("%d. %s%n", taskIndex + 1, tasks[taskIndex]);
        }
    }

    private static void shiftLeftTasksFrom(int start) {
        for (int taskIndex = start; taskIndex < currentTasksCount - 1; taskIndex++) {
            tasks[taskIndex] = tasks[taskIndex + 1];
        }
        tasks[--currentTasksCount] = null;
    }

    private static int readTaskIndex(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный номер задачи.");
            return INVALID_INDEX;
        }
    }

}