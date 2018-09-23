package abidien.FanpageRenaming;

import abidien.FanpageRenaming.handlers.RenameServlet;
import abidien.autopost.handler.DashboardServlet;
import abidien.autopost.handler.DomainServlet;
import abidien.autopost.handler.FacebookAccountServlet;
import abidien.autopost.handler.MoneyReportServlet;
import abidien.common.Config;
import abidien.handler.FakeLinkServlet;
import abidien.handler.LoginServlet;
import abidien.handler.UserServelt;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(Config.renamePort);
        connector.setAcceptorPriorityDelta(Runtime.getRuntime().availableProcessors());
        connector.setAcceptQueueSize(5000);
        connector.addBean(new QueuedThreadPool(Runtime.getRuntime().availableProcessors() * 4));

        server.setConnectors(new Connector[]{connector});

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        ResourceCollection resources = new ResourceCollection(new String[] {"web", Config.uploadDir});
        webapp.setBaseResource(resources);
        webapp.setParentLoaderPriority(true);

        //3. Including the JSTL jars for the webapp.
//        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*tiles.*\\.jar$");

        //4. Enabling the Annotation based configuration
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        webapp.addServlet(new ServletHolder(new RenameServlet()), "/web/rename/*");
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
