package Listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
//this interface receive notifications about changes to the servlet context of the web application they are part of. 
public class ContextListener implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		scheduler = Executors.newSingleThreadScheduledExecutor();
		UpdateContexts command = new UpdateContexts(); 
		scheduler.scheduleAtFixedRate(command, 0, 15, TimeUnit.MINUTES);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		// to process or wait until all pending jobs are done
		scheduler.shutdownNow();
	}

	
}
