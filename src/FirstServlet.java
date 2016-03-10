import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class FirstServlet extends GenericServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.getWriter().println(servletRequest.getServerName() + ":" + servletRequest.getServerPort());
        servletResponse.getWriter().print(servletRequest.getParameterNames());
    }
}