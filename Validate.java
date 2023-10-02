import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
//класс для проверки логина и пароля
/**
 * Класс для проверки логина и пароля
 * Смотреть класс {@link Validate}
 * @author Anuchin Dmitry
 * @version 5.0.0
 */
public class Validate {
    /**
     * Метод - проверки
     * @see Validate#checkUser(String, String, String)
     * @param client_email - почта пользователя
     * @param client_pass - пароль пользователя
     * @return данные пользователя
     */
    public User checkUser(String client_email,String client_pass,String IPD) throws SQLException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, IOException {
        connectionBD c=new connectionBD();//создание объекта класса

        User user = null;
        //обновление числа посещений
        int rs2 = c.updateUser(client_email);

        if (rs2 != 0) {
            ResultSet rs = c.selectUser( client_email, client_pass);

            if (rs.next()) {
                Blob blob = rs.getBlob("CLIENT_IMAGE");
//                Client_time = rs.getString("CLIENT_TIME");
//                Client_IPadr = rs.getString("CLIENT_IPADR");
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                user = new User();
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
//                user.set_client_Pass(client_pass);
//                user.set_client_Time(Client_time);
//                user.set_client_IPadr(Client_IPadr);
                user.set_client_Count(Integer.parseInt(rs.getString("CLIENT_COUNT")));
//
            }

        }
            return user;
    }
}
