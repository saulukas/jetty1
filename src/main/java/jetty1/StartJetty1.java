package jetty1;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class StartJetty1 {

    public static void main(String[] args) throws Exception {
        int port = 7890;
        String contextPath = "/";

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

        server.start();
        System.out.println("http://localhost:" + port + contextPath);
        server.join();
    }
}
