package abidien.chuongga;

import abidien.common.Config;
import abidien.handler.*;
import abidien.models.DomainEntity;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(Config.port);
        connector.setAcceptorPriorityDelta(Runtime.getRuntime().availableProcessors());
        connector.setAcceptQueueSize(5000);
        connector.addBean(new QueuedThreadPool(Runtime.getRuntime().availableProcessors() * 4));

        server.setConnectors(new Connector[]{connector});

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setResourceBase("web");
        webapp.setParentLoaderPriority(true);

        //3. Including the JSTL jars for the webapp.
//        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*tiles.*\\.jar$");

        //4. Enabling the Annotation based configuration
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        webapp.addServlet(new ServletHolder(new DashboardServlet()), "/web/dashboard");
        webapp.addServlet(new ServletHolder(new LoginServlet()), "/web/login");
        webapp.addServlet(new ServletHolder(new GAAccountServlet()), "/web/ga_account/*");
        webapp.addServlet(new ServletHolder(new ReportServlet()), "/web/report");
        webapp.addServlet(new ServletHolder(new UserServelt()), "/web/user/*");
        FakeLinkServlet flServlet = new FakeLinkServlet();
        webapp.addServlet(new ServletHolder(flServlet), "/fakelink");
        webapp.addServlet(new ServletHolder(flServlet), "/genlink");

        webapp.addServlet(new ServletHolder(new RestServlet<DomainEntity>(null) {
            @Override
            public DomainEntity factory() {
                return new DomainEntity(null, 0);
            }
        }), "/smart/*");

        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
