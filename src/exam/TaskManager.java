package exam;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;


public class TaskManager {
	private ArrayList<Task> tasks;
	private int nextId = 1;

	public TaskManager() {
		this.tasks = new ArrayList<>();
	}
	
	public void addTask(String title, Priority priority) {
	    tasks.add(new Task(nextId++, title, priority));
	}
	
	public void addTask(String title, Priority priority, LocalDateTime deadline) {
		tasks.add(new Task(nextId++, title, priority, deadline)); // overload, again, lai varetu veidot tasks bez un ar deadline. Lai nebūtu visur jāpadod null.
	}
	
	public boolean removeTaskById(int id) { // dzēst uzdevumu pēc ID
		for (int i = 0; i<tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				tasks.remove(i);
				return true;
			}

		}
		return false;
	}
	
	
	public ArrayList<Task> filterByStatus(TaskStatus status) { // filtrēt uzdevumus pēc statusa
		ArrayList<Task> result = new ArrayList<>();
		for (int i = 0; i<tasks.size(); i++) {
			if(tasks.get(i).getStatus() == status) {
				result.add(tasks.get(i));
			}
		}
		return result;
	}
	
	public ArrayList<Task> getAllTasks() {
	    return tasks;
	}
	
	public void saveToFile(String filename) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
	        for (Task t : tasks) {
	        	String safeTitle = escapeField(t.getTitle());
	        	String deadlineStr = (t.getDeadline() == null) ? "" : t.getDeadline().toString(); // different kind of "if" statement, means: if condition is true, use this; otherwise, use that. condition ? valueIfTrue : valueIfFalse
	        
	            writer.println(
	                t.getId() + ";" +
	                safeTitle + ";" +
	                t.getStatus() + ";" +
	                t.getPriority() + ";" +
	                deadlineStr
	            );
	        }
	    } catch (IOException e) {
	        System.out.println("Error saving file.");
	    }
	}
	
	public void loadFromFile(String filename) {
	    tasks.clear();
	    nextId = 1;

	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // ievietots try catch blokā, lai nodrošinātu pret bojātiem failiem.
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	if (line.isBlank()) continue;
	            ArrayList<String> parts = splitLine(line);
	            if(parts.size() < 4) continue;
	            
	            try {
	            	int id = Integer.parseInt(parts.get(0));
	            	String title = unescapeField(parts.get(1));
	            	TaskStatus status = TaskStatus.valueOf(parts.get(2));
	            	Priority priority = Priority.valueOf(parts.get(3));
	            
	            	LocalDateTime deadline = null;
	            	if (parts.size() >= 5 && !parts.get(4).isBlank()) {
	            		deadline = LocalDateTime.parse(parts.get(4));
	            	}


	            	Task task = new Task(id, title, priority, deadline);
	            	if (status == TaskStatus.COMPLETED) {
	            		task.markCompleted();
	            	}
	            	
	            	tasks.add(task);
	            	nextId = Math.max(nextId, id + 1);
	            }catch (Exception e) {
	            	continue;
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error loading file.");
	    }
	}

	// escapeField aizsargā saturu
	private String escapeField(String s) { // drošība pret nosaukumiem, kuros ir ; vai \\ vai \n.
		s = s.replace("\\", "\\\\");
		s = s.replace(";", "\\;");
		s = s.replace("\n", "\\n");
		return s;
	}
	
	private String unescapeField(String s) { // atjauno orginālo tekstu pēc load.
		s = s.replace("\\n", "\n");
		s = s.replace("\\;", ";");
		s = s.replace("\\\\", "\\");
		return s;
	}
	
	
	// splitLine aizsargā struktūru
	private ArrayList<String> splitLine(String line){ // \ ir "escape" signāls. ja šis parādās tekstā, tad jebkurš nākošais simbols ir aizsargāts.
		ArrayList<String> parts = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean escaped = false; // vai iepriekšējais simbols bija "\"? false = parasta sadalīšana. true = nākošais simbols ir aizsargāts.
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (escaped == true) {
				current.append(ch);
				escaped = false;
			} else if (ch == '\\') {
				current.append(ch);
				escaped = true;
			} else if (ch == ';') {
				parts.add(current.toString());
				current.setLength(0);
			} else {
				current.append(ch);
			}
		}
		
		parts.add(current.toString());
		return parts;
	}
}
