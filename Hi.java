import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
//сервлет для проверки зарегистрированного пользователя
/**
 * Сервлет для проверки зарегистрированного пользователя
 * Смотреть класс {@link Hi}
 * @author Anuchin Dmitry
 * @version 5.0.0
 */
@WebServlet("/hi")
public class Hi extends HttpServlet {
    /**
     * Метод post - проверка пользователя
     * @see Hi#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //response.setContentType("text/html;charset=UTF-8");
        Validate userRerit = new Validate();
        String imageName = request.getPathInfo();
        String message = "";
        try {
            User user = null;
            String IPadr=request.getRemoteAddr();
            user = userRerit.checkUser(email,password, IPadr);//возвращения данных пользователя после проверки


            String destPage = "/index.jsp";

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("id", user.get_client_Id());
                session.setAttribute("firstname", user.get_client_firstName());
                session.setAttribute("lastname", user.get_client_lastName());
                session.setAttribute("surname", user.get_client_surName());
                session.setAttribute("birthday", user.get_client_birthday());
                session.setAttribute("email", user.get_client_Email());
                session.setAttribute("count", user.get_client_Count());
                session.setAttribute("phone", user.get_client_phone());
                session.setAttribute("hobby", user.get_client_hobby());
                session.setAttribute("address", user.get_client_address());
                session.setAttribute("country", user.get_client_country());
                session.setAttribute("network", user.get_client_network());
                session.setAttribute("image",user.getImageData());//getServletContext().getMimeType(imageName));
                //response.setContentLength(user.getImageData().length);
                //response.getOutputStream().write(user.getImageData());
//                request.setAttribute("time", user.get_client_Time());
//                request.setAttribute("IP", user.get_client_IPadr());
                destPage = "/openWeb.jsp";
                message="";
                //getServletContext().getRequestDiЧеspatcher("/openWeb.jsp").forward(request, response);
            } else {
                    if((email.equals(""))||(password.equals(""))){
                        message="";
                        request.setAttribute("message", message);
                    }
                    else {
                        message="Неверные почта или пароль";
                        request.setAttribute("message", message);
                    }
            }

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException ex) {
                throw new ServletException(ex);
    }

    }
}