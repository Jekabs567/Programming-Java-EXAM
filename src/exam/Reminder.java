package exam;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Reminder {
	private final TaskManager manager;
	private final ScheduledExecutorService scheduler; // mainīgais kas satur background taimeri, kas ļauj kodam strādāt fonā
	
	private final long remindWithinMinutes;
	
	private final Set<Integer> notifiedTaskIds = new HashSet<>(); // Pret spam, nerādīs paziņojums par uzdevumiem, kuriem jau parādijās paziņojums
	
	public Reminder(TaskManager manager, long remindWithinMinutes) { // konstruktors
		this.manager = manager;
		this.remindWithinMinutes = remindWithinMinutes;
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
	}
	
	public void start() {
		checkDeadlines(); // pārbauda vai nav kāds uzdevums, kam tuvojas deadline uzreiz ar programmatūras sākšanu
		scheduler.scheduleAtFixedRate(this::checkDeadlines, 0, 10, TimeUnit.SECONDS); // ( the task to execute, 
																				//	the time to delay first execution,
																				//	the period between successive executions,
																				//  the time unit of the initialDelay and period parameters)
																				//  this:: is a runnable, nozīmē "palaid šo kodu vēlāk"
	}
	
	public void stop() {
		scheduler.shutdownNow();
	}
	
	private void checkDeadlines() {
		LocalDateTime now = LocalDateTime.now();
		String msg;
		for (Task t : manager.getAllTasks()) {
			if (t.getStatus() != TaskStatus.ACTIVE) { // skip ja uzdevums nav aktīvs
				continue;
			};
			if (t.getDeadline() == null) { // skip ja uzdevumam nav deadline
				continue;
			};
			
			long minutesLeft = Duration.between(now, t.getDeadline()).toMinutes();
			
			if (minutesLeft <= remindWithinMinutes && !notifiedTaskIds.contains(t.getId())) {
				if (minutesLeft < 0) {
					msg = "OVERDUE by " + Math.abs(minutesLeft)+ " minutes\n"+ "Task: " + t.getTitle();
				} else {
					msg = "Due in " + minutesLeft + " minutes\n" + "Task: " + t.getTitle();
				}
				showPopup(msg);
				notifiedTaskIds.add(t.getId());
			}
			if (minutesLeft > remindWithinMinutes) {
				notifiedTaskIds.remove(t.getId());
			}
			
		}
	}
	
	private void showPopup (String message) {
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, message, "Task reminder", JOptionPane.WARNING_MESSAGE)); // pasaka, lai Swing rāda popup logu UI "ceļā" \ thread, nevis background ceļā \ thread
	} // () -> method() is a runnable, nozīmē "palaid šo kodu vēlāk"
}
