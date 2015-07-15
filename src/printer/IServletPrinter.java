package printer;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface IServletPrinter {
		
	public void printColumn(HttpServletResponse response) throws IOException;
	
	public void printResult(HttpServletResponse response) throws IOException;
	
	public void printEnd(HttpServletResponse response) throws IOException;

}
