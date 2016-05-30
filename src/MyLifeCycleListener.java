import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Utils.Utils;

@WebListener
public class MyLifeCycleListener implements ServletContextListener {

      public void contextInitialized(ServletContextEvent event) {
    	  Utils.connectDB();
          Utils.getNoAccess();
      }

      public void contextDestroyed(ServletContextEvent event) {
          Utils.updateNoAccess();
      }
}