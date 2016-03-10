import javax.servlet.*;
import javax.sql.*;
import java.io.*;
import java.sql.*;

public class SQLservlet extends GenericServlet {

    DataSource ds;

    @Override
    public void init() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws
            ServletException, IOException {
        sendSqlForm(servletRequest, servletResponse);
    }

    private void sendSqlForm(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException{
        PrintWriter w = servletResponse.getWriter();
        w.println("<HTML>");
        w.println("<HEAD>");
        w.println("<TITLE>SQL Servlet</TITLE>");
        w.println("</HEAD>");
        w.println("<BODY>");
        w.println("<BR>please, type your request");
        w.println("<BR> <FORM action=/sql method=service>");
        w.println("<TEXTAREA Name=sql cols=90 rows=8>");
        String sql = servletRequest.getParameter("sql");
        if(sql != null) {
            w.print(sql);
        }
        w.println("</TEXTAREA>");
        w.println("<INPUT TYPE=submit value=execute>");
        w.println("</FORM>");
        w.println("<BR>");
        if(sql != null) {
            executeSql(sql.trim(), servletResponse);
        }
        w.println("</BODY>");
        w.println("</HTML>");
    }

    private void executeSql(String sql, ServletResponse response) throws ServletException,
            IOException{
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/db_first","root","19941124");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            for(int i=1;i<rs.getMetaData().getColumnCount();i++) {
                response.getWriter().print(" - " + rs.getMetaData().getColumnName(i));
            }
            response.getWriter().flush();

            while(rs.next()) {
                for(int i=1;i<rs.getMetaData().getColumnCount();i++) {
                    response.getWriter().print(" - " + rs.getString(i));
                }
                response.getWriter().println();
            }

//закрыть resultset, соединение
        } catch (SQLException ex) {
            ex.toString();
        }
    }
}