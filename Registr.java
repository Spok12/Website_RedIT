
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

//сервлет для регистрации пользователя
/**
 * Сервлет для регистрации пользователя
 * Смотреть класс {@link Registr}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/register")
public class Registr extends HttpServlet {
    /**
     * Метод post - для регистрации пользователя
     * @see Registr#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String client_firstname = request.getParameter("firstname");
        String client_lastname = request.getParameter("lastname");
        String client_surname = request.getParameter("surname");
        String client_birthday = request.getParameter("birthday");
        String client_email = request.getParameter("email");
        String client_pass = request.getParameter("pass");
        String destPage = "";
        String message3 = "";
        InputStream inputStream = null;
        File initialFile = new File("D:\\учёба\\3 курс\\2 семестр\\Сетевое программирование\\red_it\\src\\main\\java\\icon.png");

        try {
            connectionBD c=new connectionBD();//создание объекта класса

            ResultSet rs = c.selectValidate(client_email);
            if(rs.next()) {
                destPage = "/reg.jsp";
                message3 = "Пользователь с такой почтой уже существует";
                request.setAttribute("message3", message3);

            }else{
                if(client_pass.length()>=6) {
                    inputStream = new FileInputStream(initialFile);
                    int i = c.insertClient(client_firstname,client_lastname,client_surname,client_birthday,client_email,client_pass,inputStream);
                    if (i > 0) {
                        out.println("Вы успешно зарегистрированы");
                        destPage = "/index.jsp";
                    }
                }else {
                    message3 = "Вы придумали слишком короткий пароль";
                    HttpSession session = request.getSession();

                    session.setAttribute("firstname", client_firstname);
                    session.setAttribute("lastname", client_lastname);
                    session.setAttribute("surname", client_surname);
                    session.setAttribute("birthday", client_birthday);
                    session.setAttribute("email", client_email);
                    request.setAttribute("message3", message3);
                    destPage = "/reg.jsp";
                }
            }

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
        }
        catch(Exception se) {
            se.printStackTrace();
        }

    }
}
