package exam;

public class ToDoManager {

	public static void main(String[] args) {
		TaskManager manager = new TaskManager();
		manager.addTask(new Task(1, "Study", Priority.HIGH));
		manager.printAllTasks();
		boolean ok = manager.markTaskCompleteById(1);
		System.out.println("Completed: " + ok);
		manager.printAllTasks();


	}

}
