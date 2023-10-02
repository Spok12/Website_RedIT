import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/*import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;*/
// обновления данных пользователя в бд
/**
 * Сервлет для обновления данных пользователя в бд
 * Смотреть класс {@link UpdateUser}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/update")
public class UpdateUser extends HttpServlet {
//
    /**
     * Метод post - для обновления данных пользователя в бд
     * @see UpdateUser#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String client_email = request.getParameter("email");
        Validate userRerit = new Validate();
        String client_firstname = request.getParameter("firstname");;
        String client_lastname = request.getParameter("lastname");;
        String client_surname = request.getParameter("surname");;
        String client_birthday =request.getParameter("birthday");;
        String Client_email = request.getParameter("email");;
        String client_phone = request.getParameter("phone");;
        String client_hobby = request.getParameter("hobby");;
        String client_address = request.getParameter("address");;
        String client_country = request.getParameter("country");;
        connectionBD c=new connectionBD();//создание объекта класса
        User user = null;
//        DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH.mm.ss");
//        Date today = Calendar.getInstance().getTime();
//        String reportDate = df.format(today);
        int rs2 = c.updateDataUser(client_firstname,client_lastname,client_surname,client_birthday,client_email,
                client_phone, client_hobby, client_address,client_country);//выполняет обновление данных
        String destPage="/updatesuser.jsp";
        String message1 ="";
        if (rs2 != 0) {
            user = new User();
            user.set_client_firstName(client_firstname);
            user.set_client_lastName(client_lastname);
            user.set_client_surName(client_surname);
            user.set_client_birthday(client_birthday);
            user.set_client_Email(Client_email);
            user.set_client_phone(client_phone);
            user.set_client_hobby(client_hobby);
            user.set_client_address(client_address);
            user.set_client_country(client_country);
            destPage = "/updatesuser.jsp";
            message1 = "Изменены данные пользователя";

            //getServletContext().getRequestDiЧеspatcher("/openWeb.jsp").forward(request, response);
        }else {
            message1 = "Произошла ошибка";
        }
        request.setAttribute("message1", message1);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);
    }
}
