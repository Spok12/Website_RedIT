import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
// сервлет для удаления друга
/**
 * Сервлет для удаления друга
 * Смотреть класс {@link DeleteFriend}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/DeleteFriend")
public class DeleteFriend extends HttpServlet {
    /**
     * Метод post - удаления друзей
     * @see DeleteFriend#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String client_id1 = request.getParameter("id1");
        String client_id2 = request.getParameter("id2");
        String destPage = "";
        try {
            connectionBD c=new connectionBD();//создание объекта класса
            int i = c.deleteFriend(client_id1,client_id2);
            if (i > 0) {
                out.println("<input type='text' name='id1' value='" + client_id1 + "' style= 'display:none'/><br/>");
                destPage = "/fr";
            }
            HttpSession session = request.getSession();
            session.setAttribute("id", client_id1);
            System.out.println(client_id1);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        }
        catch(Exception se) {
            se.printStackTrace();
        }
    }
}