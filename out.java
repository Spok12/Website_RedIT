import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
/**
 * Сервлет для выхода
 * Смотреть класс {@link out}
 * @author Anuchin Dmitry
 * @version 5.0
 */
// сервлет для выхода
@WebServlet("/logout")
public class out extends HttpServlet {
//
    /**
     * Метод get - вывода
     * @see out#doGet(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String client_email = request.getParameter("email");
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                connectionBD c=new connectionBD();
                Connection con = c.dataB();
                String sql = "UPDATE USERREDIT SET CLIENT_NETWORK='Не в сети' WHERE client_email='" + client_email + "'";
                Statement ps = con.createStatement();
                int rs2 = ps.executeUpdate(sql);
                if (rs2 != 0) {session.removeAttribute("user");}
            }catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                    InstantiationException | IllegalAccessException ex) {
                throw new ServletException(ex);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
