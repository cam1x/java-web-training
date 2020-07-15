package by.training.listener;

import by.training.application.ApplicationContext;
import org.apache.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext.initialize();
        LOG.info("Context was initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ApplicationContext.getInstance().destroy();
        LOG.info("Context was destroyed");
    }
}