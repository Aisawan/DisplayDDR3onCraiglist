package collector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class DataAccessObject {
	
	private static Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	private static final String MYSQL_USERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    private static final String MYSQL_PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    private static final String HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    private static final String PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	private static final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/CraigList";

	public static void connectDatabase() throws SQLException
	{
		try
		{
			connection = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		}
		catch (SQLException e)
		{
			throw e;
		}
	}
	public void insertData(List<DDR3Context> contexts) throws SQLException
	{
		System.out.println("Inserting data from Craiglist...");
		try
		{
			DataAccessObject.connectDatabase();
		
			for (DDR3Context context: contexts)
			{
				String storedLink = null;
			
			//need to cast date because having change java.util.date object to java.sql.date object. But two of them are the same
				Date date = (Date) context.getDate();
				String title = context.getTitle();
				String link = context.getLink();
				double price = context.getPrice();
			
				statement = connection.createStatement();
				ResultSet subResultSet = statement.executeQuery("SELECT link FROM DDR3 WHERE link ="+link);
				while (subResultSet.next())
				{
					storedLink = subResultSet.getString("Link");
				}
			
				if (storedLink.equals(null))
				{
					preparedStatement = connection.prepareStatement("INSERT INTO DDR3 VALUES(?,?,?,?)");
					preparedStatement.setDate(1, date);
					preparedStatement.setString(2, title);
					preparedStatement.setString(3, link);
					preparedStatement.setDouble(3, price);
				
					preparedStatement.executeUpdate();
				}
			}
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			close();
			System.out.println("Inserting data completed!");
		}
	}
	@SuppressWarnings("finally")
	public List<DDR3Context> readData() throws SQLException
	{
		List<DDR3Context> readContexts = new ArrayList<DDR3Context>();
		try
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM DDR3");
			while (resultSet.next())
			{
				Date date = resultSet.getDate("Date");
				String title = resultSet.getString("Title");
				String link = resultSet.getString("Link");
				double price = resultSet.getDouble("Price");
			
				DDR3Context readContext = new DDR3Context();
				readContext.setDate(date);
				readContext.setTitle(title);
				readContext.setLink(link);
				readContext.setPrice(price);
			
				readContexts.add(readContext);
			}
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			close();
			return readContexts;
		}
	}

	private void close() throws SQLException
	{
		try
		{
			if (resultSet != null)
			{
				resultSet.close();
			}
			if (connection != null)
			{
				connection.close();
			}
			if (statement != null)
			{
				statement.close();
			}
			if (preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
