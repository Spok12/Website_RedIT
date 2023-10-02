import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.sql.*;

//сервлет добавления в друзья
/**
 * Сервлет для добавления друга
 * Смотреть класс {@link applicationFriend}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/applicationFriend")
public class applicationFriend extends HttpServlet {
/**
 * Метод post - добавление друзей
 * @see applicationFriend#doPost(HttpServletRequest, HttpServletResponse)
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
        String status="";
        try {
            connectionBD c=new connectionBD();
            ResultSet rs3 = c.selectApplicationFriend(client_id1,client_id2);
            if (rs3.next()) {
                status=rs3.getString("STATUS");
                int rs2 = c.updateApplicationFriend(client_id1,client_id2);
                if (rs2 != 0) {
                    destPage = "/fr";
                }
            }else{
                int i = c.insertApplicationFriend(client_id1,client_id2);
                if (i > 0) {
                    out.println("<input type='text' name='id1' value='" + client_id1 + "' style= 'display:none'/><br/>");
                    //out.println("Вы успешно подружились");
                    destPage = "/fr";
                }
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