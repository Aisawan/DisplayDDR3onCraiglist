package Listener;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.io.FeedException;

import collector.DDR3Context;
import collector.DataAccessObject;
import collector.Parser;

public class UpdateContexts implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Parser parser = new Parser();
		List<DDR3Context> contexts = new ArrayList<DDR3Context>();
		DataAccessObject dao = new DataAccessObject();
		try 
		{
			contexts = parser.convertFeed((parser.parseFeed("https://phoenix.craigslist.org/search/syp?query=ddr3&format=rss")));
			DataAccessObject.connectDatabase();
			dao.insertData(contexts);
		} 
		catch (IllegalArgumentException | IOException | FeedException | ParseException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
