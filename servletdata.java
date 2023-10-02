import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Base64;

//сервлет для обновления данных страницы пользователя

/**
 * Сервлет для обновления данных страницы пользователя
 * Смотреть класс {@link servletdata}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/servletdata")
public class servletdata extends HttpServlet {
    /**
     * Метод post - для обновления данных
     * @see servletdata#doPost(HttpServletRequest, HttpServletResponse) 
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        String message = "";
        try {
            connectionBD c=new connectionBD();//создание объекта класса
            User user = null;
            user = new User();
            ResultSet rs = c.selectValidate(email);//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

            if (rs.next()) {
                Blob blob = rs.getBlob("CLIENT_IMAGE");
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                user.set_client_Id(Integer.parseInt(rs.getString("CLIENT_ID")));
                user.set_client_firstName(rs.getString("CLIENT_FIRSTNAME"));
                user.set_client_lastName(rs.getString("CLIENT_LASTNAME"));
                user.set_client_surName(rs.getString("CLIENT_SURENAME"));
                user.set_client_birthday(rs.getString("CLIENT_BIRTHDAY"));
                user.setImageData(base64Image);
                user.set_client_Email(rs.getString("CLIENT_EMAIL"));
                user.set_client_phone(rs.getString("CLIENT_PHONE"));
                user.set_client_hobby(rs.getString("CLIENT_HOBBY"));
                user.set_client_address(rs.getString("CLIENT_ADDRESS"));
                user.set_client_country(rs.getString("CLIENT_COUTRY"));
                user.set_client_network(rs.getString("CLIENT_NETWORK"));
                user.set_client_Count(Integer.parseInt(rs.getString("CLIENT_COUNT")));
            }

            String destPage = "/openWeb.jsp";

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
            session.setAttribute("image",user.getImageData());

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException ex) {
            throw new ServletException(ex);
        }

    }
}
