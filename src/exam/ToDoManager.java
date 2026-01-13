package exam;


public class ToDoManager {

	public static void main(String[] args) {
		TaskManager manager1 = new TaskManager();
		
		Reminder reminders = new Reminder(manager1, 10);
		reminders.start();
		
	    TaskUI ui = new TaskUI(manager1);
	    ui.start();

	}

}
