import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

//сервлет для возвращения на основную страницу

/**
 * Сервлет для возвращения на основную страницу
 * Смотреть класс {@link out2}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/logout2")
public class out2 extends HttpServlet {
//
    /**
     * Метод get - для возвращения на основную страницу
     * @see out2#doGet(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //session.removeAttribute("user");
            friendsList f=new friendsList();

            RequestDispatcher dispatcher = request.getRequestDispatcher("/openWeb.jsp");
            dispatcher.forward(request, response);
        }
    }
}