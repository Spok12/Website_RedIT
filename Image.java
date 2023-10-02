import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

//сервлет для сохранения фото в бд

/**
 * сервлет для сохранения фото в бд
 * Смотреть класс {@link Image}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/image")

@MultipartConfig(maxFileSize = 16177215)
public class Image  extends HttpServlet {
    /**
     * Метод post - проверка обновления
     * @see Image#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = null;
        String message2 = null;
        connectionBD c = new connectionBD();
        Part filePart = request.getPart("photo");
        System.out.println(filePart);
        String client_email = request.getParameter("email");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            inputStream = filePart.getInputStream();
            System.out.println("  " + inputStream);
        }
        int row = c.updateImage(inputStream, client_email);
        if (row > 0) {message2 = "Фото обновилось ";} else {message2 = "Фото не обновилось ";}
        String destPage = "/updatesuser.jsp";
        request.setAttribute("message2", message2);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);

    }
}