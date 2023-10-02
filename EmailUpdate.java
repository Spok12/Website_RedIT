import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;

import java.io.IOException;

//сервлет для обновления пароля

/**
 * сервлет для обновления пароля
 * Смотреть класс {@link EmailUpdate}
 * @author Anuchin Dmitry
 * @version 1.0
 */
@WebServlet("/EmailUpdate")
public class EmailUpdate extends HttpServlet {
    /**
     * Метод post - для обновления пароля
     * @see EmailUpdate#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String message = null;
        String destPage = "";
        String client_email = request.getParameter("email");
        String client_pas = request.getParameter("pas");
        if(client_pas.length()>=6) {//проверка пароля на кол-во символов
            connectionBD c=new connectionBD();//создание объекта класса
            int rs2 = c.updatePassword(client_email,client_pas);
            if (rs2 != 0) {
                destPage = "/index.jsp";
            }
        }else {
            message = "Вы придумали слишком короткий пароль";
            destPage = "/recovery.jsp";
    }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);

    }

}
