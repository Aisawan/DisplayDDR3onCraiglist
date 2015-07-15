package collector;

import java.util.Date;

public class DDR3Context 
{
	// <title>
	private String title;
	// <link>
	private String link;
	//<description>
	private String description;
	//<dc: date>
	private Date date;
	//<regex from description>
	private double price;
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public String getLink()
	{
		return link;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public Date getDate()
	{
		return date;
	}
	public void setPrice(Double price)
	{
		this.price = price;
	}
	public Double getPrice()
	{
		return price;
	}
//	public String toString()
//	{
//		
//	}
}
