import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class ProductOwner extends TeamMember{

	private String url = "jdbc:mysql://localhost:3306/yazm457hw2";
	private String username = "root";
	private String password = "";

	// bu i� s�n�ftaki alanlar� ve metotlar� istedi�iniz �ekilde de�i�tirebilirsiniz
	// ancak veri taban�ndaki backlog tablosu ile uyumlu olmas�na dikkat edin.
	public static class Task {

		String name;
		int backlogId;
		int priority;

		public Task(String name, int bid, int p) {
			this.name = name;
			this.backlogId = bid;
			this.priority = p;
		}
		
		public static Task generateTask(int backlogId) {
			
			String[] gorevler = {"testing", "documenting", "coding"};
			Random ran = new Random();
	        // Returns number between 0-2 
			int index = ran.nextInt(3);
			String taskName = gorevler[index];
			
	        // Returns number between 0-9 
	        int priority = ran.nextInt(10);
			
			return new Task(taskName, backlogId, priority);
		}
		@Override
		public String toString() {
			return String.format("(name:%s, p:%d, backlogId:%d)", 
					name, priority, backlogId);
		}
	}

	public ProductOwner(int teamSize, int sprintCount) {
		super(teamSize, "ProductOwner", sprintCount);
	}

	@Override
	public void operate() {
		System.out.println("Connecting database ...");

		try{
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			
			String insertTaskQuery = "INSERT INTO product_backlog (taskname, backlogId, priority) VALUES (?, ?, ?)";
PreparedStatement preparedStatement = connection.prepareStatement(insertTaskQuery);

for (int i = 0; i < teamSize - 2; i++) {
    Task task = Task.generateTask(i + 1);
    preparedStatement.setString(1, task.name);
    preparedStatement.setInt(2, task.backlogId);
    preparedStatement.setInt(3, task.priority);
    preparedStatement.executeUpdate();
}
			connection.close();
			System.out.println("Connection closed!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	@Override
	public void run() {
		
		for (int i=1;i <= sprintCount; i++){
            System.out.println(threadName + " (sprint"+i+")");
            try {
            	operate();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
		System.out.println(threadName + " bitti...");
	}

}
