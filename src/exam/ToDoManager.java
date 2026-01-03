package exam;

public class ToDoManager {

	public static void main(String[] args) {
		TaskManager manager1 = new TaskManager();
	    TaskUI ui = new TaskUI(manager1);
	    ui.start();
		

	}

}
