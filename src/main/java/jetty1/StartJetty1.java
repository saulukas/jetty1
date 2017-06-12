package jetty1;

import java.lang.management.ManagementFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class StartJetty1 {

    public static void main(String[] args) throws Exception {
        int port = 7890;
        String contextPath = "/ohoho";

        Server server = new Server(port);
        ServerConnector connector = server.getBean(ServerConnector.class);
        HttpConfiguration config = connector.getBean(HttpConnectionFactory.class).getHttpConfiguration();
        config.setSendDateHeader(true);
        config.setSendServerVersion(true);

        ServletContextHandler context
                = new ServletContextHandler(ServletContextHandler.NO_SECURITY | ServletContextHandler.NO_SESSIONS);
        context.setContextPath(contextPath);
        server.setHandler(context);

        context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/");
        context.addServlet(JsonServlet.class, "/json");
        context.addServlet(PlaintextServlet.class, "/plaintext");
        context.addServlet(AggregatingServlet.class, "/a");

        server.start();
        System.out.println("JVM up time: " + ManagementFactory.getRuntimeMXBean().getUptime());
        System.out.println("http://localhost:" + port + contextPath);
        server.join();
    }
}
