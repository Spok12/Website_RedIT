import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
//сервлет для отправки почты с кодом для восстановления пароля

/**
 * Сервлет для отправки почты с кодом для восстановления пароля
 * Смотреть класс {@link Mail}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/Mail")
public class Mail extends HttpServlet {
    /**
     * Метод post - отправка почты с кодом
     * @see Place#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = null;
        String destPage ="";
        String client_email = request.getParameter("email");
        try {
            connectionBD c = new connectionBD();
            ResultSet rs = c.selectValidate(client_email);
            if (rs.next()) {
                message = "Письмо отправлено";
                int x = 0;
                x = rnd(1000,9999);
                // Необходимо указать адрес электронной почты отправителя
                FileInputStream fileInputStream = new FileInputStream("D:\\учёба\\3 курс\\2 семестр\\Сетевое программирование\\red_it\\src\\main\\java\\Mail.properties");
                // Получить свойства системы
                Properties properties = System.getProperties();
                properties.load(fileInputStream);
                String user = properties.getProperty("mail.user");
                String password = properties.getProperty("mail.password");
                String host = properties.getProperty("mail.host");
//работа
                String hostSMTP = "smtp.yandex.ru";
                Integer port = 465;
                Properties prop = new Properties();
            // Настроить почтовый сервер
                prop.put("mail.smtp.host", hostSMTP);
                prop.put("mail.smtp.ssl.enable", "true");
                prop.put("mail.smtp.port", port);
                prop.put("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user,password);
                }
                 });
                Transport transport = session.getTransport("smtp");
                transport.connect();
            try{//создание письма
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(user));
                InternetAddress[] addresses = {new InternetAddress(client_email)};
                msg.setRecipients(Message.RecipientType.TO,addresses);
                msg.setSubject("REDit. Восстановление пароля.");
                msg.setSentDate(new Date());
                msg.setText("Добрый день! ");
                msg.setText("Код для восстановления пароля: "+x);
                transport.sendMessage(msg,msg.getAllRecipients());
                HttpSession session0 = request.getSession();
                session0.setAttribute("cod", x);
                session0.setAttribute("email", client_email);
            } catch (MessagingException e) {
                e.printStackTrace();
            } finally {
                transport.close();
            }
        } else {
            message = "Такой почты не существует ";
        }
            destPage = "/mail.jsp";
            request.setAttribute("message", message);
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | MessagingException e) {
        throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destPage);
        dispatcher.forward(request, response);

    }
    /**
     * Метод - рандомный код
     * @see Mail#rnd(int, int)
     * @return код востановления
     */
    public static int rnd(int min, int max) { max -= min; return (int) (Math.random() * ++max) + min; }
}