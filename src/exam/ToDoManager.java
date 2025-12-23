package exam;

public class ToDoManager {

	public static void main(String[] args) {
		Task task1 = new Task(1, "Study", Priority.HIGH);
		Task task2 = new Task(2, "Buy Groceries", Priority.LOW);
		
		System.out.println(task1);
		System.out.println(task2);

	}

}
