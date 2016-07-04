package abidien.chuongga;

import abidien.controllers.UserServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
//import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Arrays;

public class JettyJspExampleMain {

    public static void main(String[] args) throws Exception {

        Server server = new Server();

        // 2. Creating the WebAppContext for the created content
        WebAppContext ctx = new WebAppContext();

//        ctx.setWar("web");

        ctx.setResourceBase("web");
        ctx.setContextPath("/web");
        //3. Including the JSTL jars for the webapp.
        ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
        //4. Enabling the Annotation based configuration
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        connector.setAcceptorPriorityDelta(Runtime.getRuntime().availableProcessors());
        connector.setAcceptQueueSize(5000);
        connector.addBean(new QueuedThreadPool(Runtime.getRuntime().availableProcessors() * 4));
//        connector.setThreadPool(new QueuedThreadPool(Runtime.getRuntime().availableProcessors() * 4));

        server.setConnectors(new Connector[]{connector});

        ServletHandler servlet = new ServletHandler();
        servlet.addServletWithMapping(UserServlet.class, "/rest/user/*");

        HandlerCollection handlers = new HandlerCollection();
        ctx.addServlet(UserServlet.class, "/rest");
        handlers.setHandlers(new Handler[]{ctx});
        //handlers.addHandler(ctx);
        //handlers.addHandler(servlet);
        server.setHandler(handlers);
        try {
            server.start();
            server.join();
        }
        catch (Throwable ex){
            System.err.println(ex);
        }
    }
}
