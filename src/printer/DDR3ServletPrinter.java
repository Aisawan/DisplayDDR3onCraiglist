package printer;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class DDR3ServletPrinter implements IServletPrinter
{
	private int tableLength;
	private int tableWidth;
	
	@Override
	public void printColumn(HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printResult(HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printEnd(HttpServletResponse response) throws IOException 
	{	
		// TODO Auto-generated method stub
		response.getWriter().print("Please choose 1 or 2");
		
	}

}
