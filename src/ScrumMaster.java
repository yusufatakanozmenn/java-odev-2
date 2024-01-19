import java.sql.*;

public class ScrumMaster extends TeamMember{

	private String url = "jdbc:mysql://localhost:3306/yazm457hw2";
	private String username = "root";
	private String password = "";
	

	public ScrumMaster(int teamSize, int sprintCount) {
		super(teamSize, "ScrumMaster", sprintCount);
	}

	@Override
	public void operate() {
    System.out.println("Connecting database ...");

    try {
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected!");

        String selectTasksQuery = "SELECT * FROM product_backlog LIMIT ?";
        PreparedStatement selectPreparedStatement = connection.prepareStatement(selectTasksQuery);
        selectPreparedStatement.setInt(1, teamSize - 2);

        ResultSet resultSet = selectPreparedStatement.executeQuery();

        String insertTaskQuery = "INSERT INTO sprint_backlog (taskname, backlogId, sprintId, priority) VALUES (?, ?, ?, ?)";
        PreparedStatement insertPreparedStatement = connection.prepareStatement(insertTaskQuery);

        int sprintId = 1; // Assuming the sprintId is 1 for this example

        while (resultSet.next()) {
            insertPreparedStatement.setString(1, resultSet.getString("taskname"));
            insertPreparedStatement.setInt(2, resultSet.getInt("backlogId"));
            insertPreparedStatement.setInt(3, sprintId);
            insertPreparedStatement.setInt(4, resultSet.getInt("priority"));
            insertPreparedStatement.executeUpdate();
        }

        String selectTasksQueryForBoard = "SELECT * FROM sprint_backlog";
        PreparedStatement selectPreparedStatementForBoard = connection.prepareStatement(selectTasksQueryForBoard);
        ResultSet resultSetForBoard = selectPreparedStatementForBoard.executeQuery();

        String insertTaskQueryForBoard = "INSERT INTO board (taskname, backlogId, sprintId, developerName, priority) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertPreparedStatementForBoard = connection.prepareStatement(insertTaskQueryForBoard);

        String developerName = "Developer1"; // Assuming all tasks are initially assigned to "Developer1"

        while (resultSetForBoard.next()) {
            insertPreparedStatementForBoard.setString(1, resultSetForBoard.getString("taskname"));
            insertPreparedStatementForBoard.setInt(2, resultSetForBoard.getInt("backlogId"));
            insertPreparedStatementForBoard.setInt(3, resultSetForBoard.getInt("sprintId"));
            insertPreparedStatementForBoard.setString(4, developerName); // Assigning the developer name
            insertPreparedStatementForBoard.setInt(5, resultSetForBoard.getInt("priority"));
            insertPreparedStatementForBoard.executeUpdate();
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
