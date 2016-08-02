package abidien.chuongga;

import abidien.controllers.UserServlet;
import abidien.handler.DashboardServlet;
import abidien.handler.GAAccountServlet;
import abidien.handler.LoginServlet;
import abidien.handler.ReportServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
//import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        connector.setAcceptorPriorityDelta(Runtime.getRuntime().availableProcessors());
        connector.setAcceptQueueSize(5000);
        connector.addBean(new QueuedThreadPool(Runtime.getRuntime().availableProcessors() * 4));

        server.setConnectors(new Connector[]{connector});

        WebAppContext webapp = new WebAppContext();

    //3. Including the JSTL jars for the webapp.
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*tiles.*\\.jar$");

        //4. Enabling the Annotation based configuration
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        webapp.setContextPath("/");
        webapp.setWar("web");

        webapp.addServlet(new ServletHolder(new UserServlet()),"/rest/user/*");
        webapp.addServlet(new ServletHolder(new DashboardServlet()), "/web/dashboard");
        webapp.addServlet(new ServletHolder(new LoginServlet()), "/web/login");
        webapp.addServlet(new ServletHolder(new GAAccountServlet()), "/web/ga_account/*");
        webapp.addServlet(new ServletHolder(new ReportServlet()), "/web/report");

        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
