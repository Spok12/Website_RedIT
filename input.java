import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Objects;

@WebServlet("/in")
public class input extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    public input() {
//        super();
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        String password = request.getParameter("password");
        Validate userRerit = new Validate();
        String message = "";
    String client_firstname = "";
    String client_lastname = "";
    String client_surname = "";
    String client_birthday = "";
    String client_phone = "";
    String client_hobby = "";
    String client_address = "";
    String client_country = "";
    try {

        User user1 = null;
        String IPadr=request.getRemoteAddr();
        user1 = userRerit.checkUser(email,password, IPadr);
        String destPage = "/openWeb.jsp";
        if (user1 != null) {
            HttpSession session = request.getSession();
            request.setAttribute("firstname", user1.get_client_firstName());
            session.setAttribute("lastname",user1.get_client_lastName());
            request.setAttribute("surname", user1.get_client_surName());
            request.setAttribute("birthday", user1.get_client_birthday());
            request.setAttribute("email", user1.get_client_Email());
            request.setAttribute("phone", user1.get_client_phone());
            request.setAttribute("hobby", user1.get_client_hobby());
            request.setAttribute("address", user1.get_client_address());
            request.setAttribute("country", user1.get_client_country());
            //request.setAttribute("count", user1.get_client_Count());
            destPage = "/updatesuser.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);
        //con2.close();

    } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException ex) {
        throw new ServletException(ex);
    }


    }
}

