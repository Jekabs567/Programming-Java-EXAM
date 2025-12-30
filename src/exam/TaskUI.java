package exam;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class TaskUI {

    private TaskManager manager;

    public TaskUI(TaskManager manager) {
        this.manager = manager;
    }

    public void start() {
        while (true) {
            String choice = JOptionPane.showInputDialog(
                    "To-Do Manager\n\n"
                    + "1. Add Task\n"
                    + "2. List Tasks\n"
                    + "3. Edit Task\n"
                    + "4. Remove Task\n"
                    + "5. Mark Task as Completed\n"
                    + "6. Filter Tasks\n"
                    + "7. Exit"
            );

            if (choice == null || choice.equals("7")) break;

            switch (choice) {
                case "1": addTask(); break;
                case "2": listTasks(); break;
                case "3": editTask(); break;
                case "4": removeTask(); break;
                case "5": markCompleted(); break;
                case "6": filterTasks(); break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option!");
            }
        }
    }

    private Task chooseTaskByTitle(String message, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks available!");
            return null;
        }

        String[] titles = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            titles[i] = tasks.get(i).getId() + ": " + tasks.get(i).getTitle();
        }

        String selected = (String) JOptionPane.showInputDialog(
                null,
                message,
                "Select Task",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titles,
                titles[0]
        );

        if (selected == null) return null;

        int selectedId = Integer.parseInt(selected.split(":")[0]);

        for (Task t : tasks) {
            if (t.getId() == selectedId) {
                return t;
            }
        }
        return null;
    }


    private void addTask() {
        String title = JOptionPane.showInputDialog("Enter task title:");
        if (title == null || title.isBlank()) {
            JOptionPane.showMessageDialog(null, "Title cannot be empty!");
            return;
        }

        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        String priorityStr = (String) JOptionPane.showInputDialog(
                null,
                "Select priority:",
                "Priority",
                JOptionPane.QUESTION_MESSAGE,
                null,
                priorities,
                priorities[0]
        );

        if (priorityStr == null) return;

        manager.addTask(title, Priority.valueOf(priorityStr));
        JOptionPane.showMessageDialog(null, "Task added!");
    }

    private void listTasks() {
        ArrayList<Task> tasks = manager.getAllTasks();

        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks available!");
            return;
        }

        StringBuilder sb = new StringBuilder("All Tasks:\n\n");
        for (Task t : tasks) {
            sb.append(t).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void editTask() {
        Task task = chooseTaskByTitle("Select task to edit:", manager.getAllTasks());
        if (task == null) return;

        String newTitle = JOptionPane.showInputDialog("New title:", task.getTitle());
        if (newTitle != null && !newTitle.isBlank()) {
            task.setTitle(newTitle);
        }

        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        String newPriority = (String) JOptionPane.showInputDialog(
                null,
                "Select new priority:",
                "Priority",
                JOptionPane.QUESTION_MESSAGE,
                null,
                priorities,
                task.getPriority().name()
        );

        if (newPriority != null) {
            task.setPriority(Priority.valueOf(newPriority));
        }

        JOptionPane.showMessageDialog(null, "Task updated!");
    }

    private void removeTask() {
        Task task = chooseTaskByTitle("Select task to remove:", manager.getAllTasks());
        if (task == null) return;

        manager.removeTaskById(task.getId());
        JOptionPane.showMessageDialog(null, "Task removed!");
    }

    private void markCompleted() {
        ArrayList<Task> activeTasks = manager.filterByStatus(TaskStatus.ACTIVE);
        Task task = chooseTaskByTitle("Select task to mark as completed:", activeTasks);
        if (task == null) return;

        task.markCompleted();
        JOptionPane.showMessageDialog(null, "Task marked as completed!");
    }

    private void filterTasks() {
        String[] options = {"ACTIVE", "COMPLETED"};
        String statusStr = (String) JOptionPane.showInputDialog(
                null,
                "Select status:",
                "Filter Tasks",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (statusStr == null) return;

        TaskStatus status = TaskStatus.valueOf(statusStr);
        ArrayList<Task> filtered = manager.filterByStatus(status);

        if (filtered.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No " + status + " tasks found.");
            return;
        }

        StringBuilder sb = new StringBuilder(status + " Tasks:\n\n");
        for (Task t : filtered) {
            sb.append(t).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
