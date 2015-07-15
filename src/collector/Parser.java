package collector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
//import java.io.Reader;
import java.util.regex.Pattern;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


public class Parser
{
	private URL feedSource;
	private List<DDR3Context> contexts = new ArrayList<DDR3Context>();
	private SyndFeed parsedFeed;
	
	public SyndFeed parseFeed(String url) throws IOException, IllegalArgumentException, FeedException
	{
		this.feedSource = new URL(url);
		//like Scanner in = new Scanner(System.in)
		XmlReader xmlReader = new XmlReader(feedSource);
		SyndFeedInput input = new SyndFeedInput();
		//like int numbers = in.nextInt()
		this.parsedFeed = input.build(xmlReader);
		return parsedFeed;
	}

	public List<DDR3Context> convertFeed(SyndFeed parsedFeed) throws ParseException
	{	
		// to get every entry from the parsed feed storing in list
		// putting "?" to indicate that this is a list of any type that returns from parsedFeed.getEntries()
		List<?> entries = parsedFeed.getEntries();
		// to create iterator alternative way to implement For loop, in case we don't know the data type of the list element.
		// Iterator object can call hasNext() to reiterate the loop as long as the iterator has remaining element.
		Iterator<?> it = entries.iterator();
		// to check if the iterator has next element. 
		while (it.hasNext())
		{
			//each entry in entries
			SyndEntry entry = (SyndEntry) it.next();
			//to get title, link, date, description using get...() method
			String link = entry.getLink();
			String title = entry.getTitle();
			//to get date in Dublin Core module date specified in the source of the feed
			Date date = entry.getPublishedDate();
			SyndContent description = entry.getDescription();
			String text = description.getValue();
			//to use regular expression to extract price form a text
			//to extract the highest number that followed "$" and add to arraylist
			// using \ to escape \ to use \ as a char
			// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE); in case wanting to ignore case sensitivity
			Pattern pattern = Pattern.compile("\\$"+"\\s*[0-9.,]+[-0-9.,]*\\b");
			// create matching engine which use the pattern
			Matcher matcher = pattern.matcher(text);
			// check all occurrence
			List<Double> prices = new ArrayList<Double>();
			while (matcher.find())
			{
				// start index of string is 0
				int startMatchedIndex = matcher.start();
				int endMatchedIndex = matcher.end();
				String match = text.substring(startMatchedIndex, endMatchedIndex);
				Pattern numPattern = Pattern.compile("[0-9.,]+[-0-9.,]*");
				Matcher numMatcher = numPattern.matcher(match);
				int startMatchedIndexOfNum = numMatcher.start();
				int endMatchedIndexOfNum = numMatcher.end();
				String stringNum = match.substring(startMatchedIndexOfNum, endMatchedIndexOfNum);
				double price = (double) NumberFormat.getNumberInstance(java.util.Locale.US).parse(stringNum);
				prices.add(price);
			}
			double maxPrice = 0;
			for (double price : prices )
			{
				if (price > maxPrice)
				{
					maxPrice = price;
				}
			}
			DDR3Context context = new DDR3Context();
			context.setTitle(title);
			context.setLink(link);
			context.setDate(date);
			context.setPrice(maxPrice);
			
			contexts.add(context);
		}
		return contexts;
		
	}
	public List<DDR3Context> getContext()
	{
		return this.contexts;
	}

}
