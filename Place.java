import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
//сервлет с интерфейсом вывод страницы друга

/**
 * Сервлет с интерфейсом вывод страницы друга
 * Смотреть класс {@link Place}
 * @author Anuchin Dmitry
 * @version 5.0
 */
 @WebServlet("/place")
public class Place  extends HttpServlet {
     /**
      * Метод post - вывода друзей
      * @see Place#doPost(HttpServletRequest, HttpServletResponse)
      * @param response - управляет ответом на запрос
      * @param request - хранит информацию о запросе
      */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");//получение параметра
        String imageName = request.getPathInfo();
        try {
            connectionBD c=new connectionBD();//создание объекта класса
            User user = null;
            ResultSet rs = c.selectValidate(email);//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки
            if (rs.next()) {//проверка выполения запроса
                //получение из БД значений пользователя и запись в user
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
                user.set_client_Count(Integer.parseInt(rs.getString("CLIENT_COUNT")));

                ArrayList<friendsList> listF = new ArrayList<friendsList>();//список с друзьями пользователя
                outputYourFrinds oyf=new outputYourFrinds();//создание объекта
                String r=""+(user.get_client_Id());
                listF=oyf.listFriends(r);//получение списка друзей

                try (PrintWriter out = response.getWriter()) {//создание html для вывода данных в браузер
                    response.setContentType("text/html;charset=UTF-8");
                    out.println("<!DOCTYPE html>");
                    out.println("<html><head>");
                    out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");

                    out.println("<title>Пользователь "+user.get_client_firstName()+" "+user.get_client_lastName()+"</title>" +
                            "<style>\n" +
                            "    html{\n" +
                            "        width: 100%;\n" +
                            "        height: 100%;\n" +
                            "        overflow: visible;\n" +
                            "        font-style: italic;\n" +
                            "    }\n" +
                            "    body{\n" +
                            "        width: 100%;\n" +
                            "        height: 100%;\n" +
                            "        font-family: 'Open Sans',sans-serif;\n" +
                            "        margin: 0;\n" +
                            "        background-color: #665687;\n" +
                            "    }\n" +
                            "    #table1{\n" +
                            "        width:300px;\n" +
                            "        height: 400px;\n" +
                            "        font-family: 'Open Sans',sans-serif;\n" +
                            "        margin: 0;\n" +
                            "        background-color: #312E3D;\n" +
                            "        border-top: 1px solid #312E3D;\n" +
                            "        border-left: 1px solid #312E3D;\n" +
                            "        border-right: 1px solid #312E3D;\n" +
                            "        border-bottom: 1px solid #312E3D;\n" +
                            "        border-radius: 6px;\n" +
                            "        box-shadow:4px 4px 5px rgba(0, 0, 0,.4);\n" +
                            "    }\n" +
                            "    #form{\n" +
                            "        width:600px;\n" +
                            "        height: 800px;\n" +
                            "        font-family: 'Open Sans',sans-serif;\n" +
                            "        margin: 0;\n" +
                            "        background-color: #312E3D;\n" +
                            "        border-top: 1px solid #312E3D;\n" +
                            "        border-left: 1px solid #312E3D;\n" +
                            "        border-right: 1px solid #312E3D;\n" +
                            "        border-bottom: 1px solid #312E3D;\n" +
                            "        border-radius: 6px;\n" +
                            "        box-shadow:4px 4px 5px rgba(0, 0, 0,.4);\n" +
                            "    }\n" +
                            "    #form2{\n" +
                            "        position: relative;\n" +
                            "        top: 5%;\n" +
                            "        left:2%;\n" +
                            "        width:1430px;\n" +
                            "        height: 100px;\n" +
                            "        font-family: 'Open Sans',sans-serif;\n" +
                            "        margin: 0;\n" +
                            "        background-color: #312E3D;\n" +
                            "        border-top: 1px solid #312E3D;\n" +
                            "        border-left: 1px solid #312E3D;\n" +
                            "        border-right: 1px solid #312E3D;\n" +
                            "        border-bottom: 1px solid #312E3D;\n" +
                            "        border-radius: 6px;\n" +
                            "        box-shadow:4px 4px 5px rgba(0, 0, 0,.4);\n" +
                            "    }\n" +
                            "\n" +
                            "    #form2 h1{\n" +
                            "        color: #fff;\n" +
                            "        /*text-shadow:0 0 10px;*/\n" +
                            "        letter-spacing: 1px;\n" +
                            "        text-align: center;\n" +
                            "    }\n" +
                            "\n" +
                            "    #login3{\n" +
                            "        position: relative;\n" +
                            "        top: 13%;\n" +
                            "        left:50%;\n" +
                            "        margin: -150px 0 0 -150px;\n" +
                            "        width: 300px;\n" +
                            "        height: 300px;\n" +
                            "    }\n" +
                            "    #login4{\n" +
                            "        position: relative;\n" +
                            "        top: 34%;\n" +
                            "        left:30%;\n" +
                            "        margin: -150px 0 0 -150px;\n" +
                            "        width: 300px;\n" +
                            "        height: 300px;\n" +
                            "    }\n" +
                            "    #login3 h1{\n" +
                            "        color: #fff;\n" +
                            "        /*text-shadow:0 0 10px;*/\n" +
                            "        letter-spacing: 1px;\n" +
                            "        text-align: center;\n" +
                            "    }\n" +
                            "\n" +
                            "    #login4 h1{\n" +
                            "        color: #fff;\n" +
                            "        /*text-shadow:0 0 10px;*/\n" +
                            "        letter-spacing: 1px;\n" +
                            "        text-align: center;\n" +
                            "    }\n" +
                            "\n" +
                            "    a,c{\n" +
                            "        letter-spacing: 1px;\n" +
                            "        text-align: left;\n" +
                            "        color: #fff;\n" +
                            "    }\n" +
                            "    b{\n" +
                            "        letter-spacing: 1px;\n" +
                            "        text-align: left;\n" +
                            "        color: #fff;\n" +
                            "    }\n" +
                            "    h1{\n" +
                            "        font-size: 2em;\n" +
                            "        margin: 0.67em 0;\n" +
                            "    }\n" +
                            "    label {\n" +
                            "        font-size: 14px;\n" +
                            "        color: white;\n" +
                            "        text-transform: uppercase;\n" +
                            "        font-weight: 50;\n" +
                            "        padding-top: 100px;\n" +
                            "        text-align: center;\n" +
                            "    }\n" +
                            "    input{\n" +
                            "        width: 278px;\n" +
                            "        height: 18px;\n" +
                            "        margin-bottom: 10px;\n" +
                            "        outline: none;\n" +
                            "        padding: 10px;\n" +
                            "        font-size: 13px;\n" +
                            "        color: #fff;\n" +
                            "        text-shadow:1px 1px 1px;\n" +
                            "        border-top: 1px solid #312E3D;\n" +
                            "        border-left: 1px solid #312E3D;\n" +
                            "        border-right: 1px solid #312E3D;\n" +
                            "        border-bottom: 1px solid #56536A;\n" +
                            "        border-radius: 4px;\n" +
                            "        background-color: #2D2D3F;\n" +
                            "    }\n" +
                            "    .but{\n" +
                            "        width: 200px;\n" +
                            "        min-height: 20px;\n" +
                            "        position:relative;\n" +
                            "        top:17px;\n" +
                            "        background-color: #F7F9F7;\n" +
                            "        border: 1px solid #750D37;\n" +
                            "        color: #750D37;\n" +
                            "        padding: 9px 14px;\n" +
                            "        font-size: 15px;\n" +
                            "        box-sizing: border-box;\n" +
                            "        line-height: normal;\n" +
                            "        border-radius: 5px;\n" +
                            "        margin: auto;\n" +
                            "    }\n" +
                            "    .but:hover {\n" +
                            "        background-color:#665687;\n" +
                            "        transition: 0.7s;\n" +
                            "    }\n" +
                            "    .image2{\n" +
                            "        width:30px;\n" +
                            "\n" +
                            "        display: block;\n" +
                            "        margin-bottom:1px;\n" +
                            "        /*margin-left: auto;*/\n" +
                            "        /*margin-right: auto;*/\n" +
                            "    }\n" +
                            "    .card-image{\n" +
                            "\n" +
                            "        width:100%;\n" +
                            "        /*display: block;*/\n" +
                            "        /*margin-left: auto;*/\n" +
                            "        /*margin-right: auto;*/\n" +
                            "    }\n" +
                            "    .footer{\n" +
                            "        height:300px;\n" +
                            "        font-family: 'Open Sans',sans-serif;\n" +
                            "        background-color:black;\n" +
                            "        position: relative;\n" +
                            "        top: 100%;\n" +
                            "\n" +
                            "    }\n" +
                            "    .footer-author{\n" +
                            "        color:white;\n" +
                            "        font-size:24px;\n" +
                            "    }\n" +
                            ".scroll-table-body {\n" +
                            "\theight: 200px;\n" +
                            "\toverflow-x: auto;\n" +
                            "\tmargin-top: 0px;\n" +
                            "\tmargin-bottom: 20px;\n" +
                            "\tborder-bottom: 1px solid #eee;\n" +
                            "}\n" +
                            ".scroll-table table {\n" +
                            "\twidth:100%;\n" +
                            "\ttable-layout: fixed;\n" +
                            "\tborder: none;\n" +
                            "}\n" +
                            ".scroll-table thead th {\n" +
                            "\tfont-weight: bold;\n" +
                            "\ttext-align: left;\n" +
                            "\tborder: none;\n" +
                            "\tpadding: 10px 15px;\n" +
                            "\tbackground: #d8d8d8;\n" +
                            "\tfont-size: 14px;\n" +
                            "\tborder-left: 1px solid #ddd;\n" +
                            "\tborder-right: 1px solid #ddd;\n" +
                            "}\n" +
                            ".scroll-table tbody td {\n" +
                            "\ttext-align: left;\n" +
                            "\tborder-left: 1px solid #ddd;\n" +
                            "\tborder-right: 1px solid #ddd;\n" +
                            "\tpadding: 10px 15px;\n" +
                            "\tfont-size: 14px;\n" +
                            "\tvertical-align: top;\n" +
                            "}\n" +
                            ".scroll-table tbody tr:nth-child(even){\n" +
                            "\tbackground: #f3f3f3;\n" +
                            "}\n" +
                            " \n" +
                            "/* Стили для скролла */\n" +
                            "::-webkit-scrollbar {\n" +
                            "\twidth: 6px;\n" +
                            "} \n" +
                            "::-webkit-scrollbar-track {\n" +
                            "\tbox-shadow: inset 0 0 6px rgba(0,0,0,0.3); \n" +
                            "} \n" +
                            "::-webkit-scrollbar-thumb {\n" +
                            "\tbox-shadow: inset 0 0 6px rgba(0,0,0,0.3); \n" +
                            "}" +
                            "</style></head> <body >");
                    // out.println("<form action='searchf' method='post' >");
                    out.println("<fieldset id='form2' style='text-align: center'>" +
                            "<br> <a style='text-align: left' href='logout2' >Вернутся на основную страницу</a>" +
                            "<h1>Пользователь</h1> </fieldset >");

                    out.println("<div class='scroll-table-body' id='login4' style='text-align: center'>" +

                            "<table id='table1' style='border:1px solid #6d2323;'>" +
                            "    <td><h1>Друзья</h1></td>");
                    out.println("<tr > <td colspan = 8 align = 'center' > </td > </tr >");
                    out.println("<tr >  <td ><b > \\/</b ></td ></tr>");
                    int count0 = 0;
                    String color0 = "#F9EBB3";
                    for(friendsList o:listF){
                        if ((count0 % 2) == 0) {
                            color0 = "#eeffee";
                        } else {
                            color0 = "#F9EBB3";
                        }
                        count0++;
                        out.println("<tr style = 'background-color:<%=color%>;' >");
                        out.println("<td > <form action='place' method='post' > <label>" +
                                "<input type='text' name= 'email' value='"+o.get_client_Email()+"' style= 'display:none'/>" +
                                "</label> <div> <a> <button class='but' type='submit'><img src='data:image/jpg;base64,"+o.getImageData()+"' width= '30' height= '30' alt=''/>"+o.get_client_firstName()+" "+o.get_client_lastName()+"</button> </a> </div></form></td >");
                    }
                    if (count0 == 0) {
                        out.println("<tr > <td colspan = 8 align = 'center' ><b > Не найдены </b ></td > </tr >");
                    }
                    out.println("</table ></div>");
                    out.println("<div id='login3' style='text-align: center'>\n" +
                            "    <fieldset id='form'>\n" +
                            "        <br><br>\n" +
                            "    <img src='data:image/jpg;base64,"+user.getImageData()+"' width='300' height='300' alt=''/>\n" +
                            "\n" +
                            "    <label>\n" +
                            "\n" +
                            "        <input type='text' name='email' value='"+user.get_client_Email()+"' size='30' style='display:none'/>\n" +
                            "    </label>\n" +
                            "    <br><br>\n" +
                            "    <b>Число посещений: "+user.get_client_Count()+"</b>\n" +
                            "\n" +
                            "    <br><br>\n" +
                            "    <b> "+user.get_client_firstName()+" "+user.get_client_lastName()+"</b>\n" +
                            "    <br><br>\n" +
                            "    <b> "+user.get_client_surName()+"</b>\n" +
                            "    <br>\n" +
                            "    <b class='image2' > <img src='https://e7.pngegg.com/pngimages/377/949/png-clipart-email-address-computer-icons-email-miscellaneous-angle.png' class='card-image' alt=''></b><b>Почта: "+user.get_client_Email()+"</b>\n" +
                            "\n" +
                            "    <b class='image2'><img src='https://sun6-21.userapi.com/s/v1/if1/EA-6ersvUbfudXajhnipuU84A6GdbfF6f0PAzwQeSAOnCWbS505xdKrian4d0o4k65Z2EQuS.jpg?size=1992x1992&quality=96&crop=70,70,1992,1992&ava=1' class='card-image' alt=''></b><b>Телефон: "+user.get_client_phone()+"</b>\n" +
                            "\n" +
                            "    <b class='image2'><img src='https://cdn1.iconfinder.com/data/icons/party-and-celeberation/78/17-512.png' class='card-image' alt=''></b><b>День рождения: "+user.get_client_birthday()+"</b>\n" +
                            "\n" +
                            "    <b class='image2' ><img src='https://krez.info-dvd.ru/img/questions-clipart-problem-statement-6.png' class='card-image' alt=''></b><b>Хобби: "+user.get_client_hobby()+"</b>\n" +
                            "\n" +
                            "    <b class='image2'><img src='https://grizly.club/uploads/posts/2022-12/1670861130_grizly-club-p-adres-ikonka-png-47.png' class='card-image' alt=''></b><b>Адрес проживания:"+user.get_client_country()+" "+user.get_client_address()+"</b>\n" +
                            "\n" +
                            "<br>" +
                            "        </fieldset >\n" +
                            "    <br>\n" +
                            "</div>\n" +
                            "<footer class=\"footer\">\n" +
                            "    <h4 class=\"footer-author\"> REDit 2023. Курсовой проект сделан Анучиным Дмитрием бРИС-201.</h4>\n" +
                            "</footer>\n" +
                            "<br>");
                    out.println("</div></body > </html >");
                }
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException ex) {
            throw new ServletException(ex);
        }
    }
}
