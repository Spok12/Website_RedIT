import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
//класс для нахождения друзей в бд
/**
 * Класс поиска друзей
 * Смотреть класс {@link outputYourFrinds}
 * @author Anuchin Dmitry
 * @version 1.0
 */
public class outputYourFrinds {
    /**
     * Метод - нахождения друзей
     * @see outputYourFrinds#listFriends(String)
     * @param client_id - id пользователя
     */
    public ArrayList<friendsList> listFriends(String client_id) throws ServletException, IOException {

        ArrayList<friendsList> listF = new ArrayList<friendsList>();//список друзей
        String i1="";
        try {
            connectionBD c=new connectionBD();//создание объекта класса
///вывод друзей
            ResultSet rs = c.selectCountFriends(client_id);//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

            while (rs.next()) {/////////
                //получение значения id друга
                i1= (rs.getString("FRIEND_TWO"));

                if(i1.equals(client_id)) {
                    i1 = (rs.getString("FRIEND_ONE"));
                    ResultSet rs2 = c.selectFriendOne(i1);//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки
                    if(rs2.next()){
                        friendsList fl =new friendsList();//создание списка друзей
                        //получение данных друзей
                        Blob blob = rs2.getBlob("CLIENT_IMAGE");
                        InputStream inputStream = blob.getBinaryStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] imageBytes = outputStream.toByteArray();
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        //listImage.add(base64Image);
                        fl.set_client_id(rs2.getString("CLIENT_ID"));
                        fl.set_client_firstName(rs2.getString("CLIENT_FIRSTNAME"));
                        fl.set_client_lastName(rs2.getString("CLIENT_LASTNAME"));
                        fl.set_client_Email(rs2.getString("CLIENT_EMAIL"));
                        fl.set_client_status(rs2.getString("STATUS"));
                        fl.setImageData(base64Image);
                        fl.set_client_network(rs2.getString("CLIENT_NETWORK"));
                        listF.add(fl);//заполнение списка
                    }

                }
                else {
                    //то же самое получение списка друзей
                    ResultSet rs2 = c.selectFriendTwo(i1);
                    if (rs2.next()) {
                        friendsList fl = new friendsList();

                        Blob blob = rs2.getBlob("CLIENT_IMAGE");
                        InputStream inputStream = blob.getBinaryStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] imageBytes = outputStream.toByteArray();
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        //listImage.add(base64Image);
                        fl.set_client_id(rs2.getString("CLIENT_ID"));
                        fl.set_client_firstName(rs2.getString("CLIENT_FIRSTNAME"));
                        fl.set_client_lastName(rs2.getString("CLIENT_LASTNAME"));
                        fl.set_client_Email(rs2.getString("CLIENT_EMAIL"));
                        fl.set_client_status(rs2.getString("STATUS"));
                        fl.setImageData(base64Image);
                        fl.set_client_network(rs2.getString("CLIENT_NETWORK"));
                        listF.add(fl);
                    }
                }
            }
        }
        catch(Exception se) {
            se.printStackTrace();
        }
        return listF;
    }
}
