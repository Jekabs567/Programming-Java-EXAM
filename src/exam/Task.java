package exam;

import java.time.LocalDateTime;

public class Task {
	private int id;
	private String title;
	private TaskStatus status;
	private Priority priority;
	private LocalDateTime deadline;
	
	public Task(int id, String title, Priority priority, LocalDateTime deadline) { // konstruktors
		this.id = id;
		this.title = title;
		this.priority = priority;
		this.status = TaskStatus.ACTIVE;
		this.deadline = deadline;
	}
	
	public Task(int id, String title, Priority priority) { // konstruktora pārslogošana, basically shortcut, izmanto lai izveidotu task bez deadline
		this(id, title, priority, null);
	}
	
	@Override
	public String toString() { // override aizvieto metodi no parent class. ja nav deadline, tad rāda "none." Savādāk rāda deadline tekstu.
		String dl = (deadline == null) ? "None": deadline.toString(); // different kind of "if" statement, means: if condition is true, use this; otherwise, use that. condition ? valueIfTrue : valueIfFalse
		return "Task " + id + ": " + title + " | Status: " + status + " | Priority: " + priority + " | Deadline: " + dl;
	}
	
	//getter
	public int getId() {
		return id;
	}
	
	public String getTitle() {
	    return title;
	}
	
	public Priority getPriority() {
	    return priority;
	}
	
	public void markCompleted() {
		status = TaskStatus.COMPLETED;
	}
	
	public void setTitle(String newTitle) {
		if (newTitle != null && !newTitle.isBlank()) {
			title = newTitle;
		}
	}
	
	public void setPriority(Priority newPriority) {
		if (newPriority != null) {
			priority = newPriority;
		}
	}
	
	public TaskStatus getStatus() {
		return status;
	}
	
	public LocalDateTime getDeadline() {
		return deadline;
	}
	
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	

}
