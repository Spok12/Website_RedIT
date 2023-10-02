import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
//сервлет проверка кода для востановления пароля
/**
 * Сервлет для провеки кода
 * Смотреть класс {@link Recovery}
 * @author Anuchin Dmitry
 * @version 1.0
 */
@WebServlet("/Recovery")
public class Recovery extends HttpServlet {
//
    /**
     * Метод post - вывода друзей
     * @see Recovery#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        InputStream inputStream = null;

        String message2 = null;
        String client_email = request.getParameter("email");
        String cod = request.getParameter("cod");
        String client_code = request.getParameter("code");
        String destPage = "";
        if (cod.equals(client_code)) {
           destPage = "/recovery.jsp";
        } else {
            message2 = "Не верный код ";
            destPage = "/mail.jsp";
        }
        HttpSession session0 = request.getSession();
        session0.setAttribute("email", client_email);

        request.setAttribute("message2", message2);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);

    }
}