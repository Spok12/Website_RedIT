import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
// сервлет с поиском друзей по имени фамилии отчеству хобби адресу проживания

/**
 * Сервлет с поиском друзей по имени фамилии отчеству хобби адресу проживания
 * Смотреть класс {@link SearchFriend}
 * @author Anuchin Dmitry
 * @version 5.0
 */
@WebServlet("/searchf")
public class SearchFriend extends HttpServlet {
//
    /**
     * Метод post с поиском друзей по имени фамилии отчеству хобби адресу проживания
     * @see SearchFriend#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String client_id1 = request.getParameter("id1");
        ConvectorPhoto conver = new ConvectorPhoto();
        File f1=new File("D:\\учёба\\3 курс\\2 семестр\\Сетевое программирование\\red_it\\src\\main\\java\\kisspng-computer-icons-canada-organization-company-sponsor-5af56042995d00.2474135415260304026282.jpg");
        FileInputStream f11=new FileInputStream(f1);
        String base64Icon = conver.images(f11);
        try {
            String word = request.getParameter("word");
            String client_id = "";
            int client_id2 = 0;
            String client_firstname = "";
            String client_lastname = "";
            String Client_email = "";
            ArrayList<friendsList> listS = new ArrayList<friendsList>();//список найденных пользователей
            connectionBD c=new connectionBD();
            ResultSet rs = c.selectSearchFriends(word);
            while (rs.next()) {
                friendsList friends=new friendsList();
                client_id = rs.getString("CLIENT_ID");
                client_id2 = Integer.parseInt(client_id);
                client_firstname = rs.getString("CLIENT_FIRSTNAME");
                client_lastname = rs.getString("CLIENT_LASTNAME");
                Blob blob = rs.getBlob("CLIENT_IMAGE");
                Client_email=rs.getString("CLIENT_EMAIL");
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                if(!client_id1.equals(client_id)) {
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    friends.set_client_id(client_id);
                    friends.set_client_firstName(client_firstname);
                    friends.set_client_lastName(client_lastname);
                    friends.setImageData(base64Image);
                    //friends.set_client_status(status);
                    friends.set_client_Email(Client_email);
                    listS.add(friends);
                }
        }
            response.setContentType("text/html");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head>");
                out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                out.println("<title>Поиск</title>" +
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
                        "        width:700px;\n" +
                        "        font-family: 'Open Sans',sans-serif;\n" +
                        "        margin: 0;\n" +
                        "        background-color: #312E3D;\n" +
                        "        border-top: 1px solid #312E3D;\n" +
                        "        border-left: 1px solid #312E3D;\n" +
                        "        border-right: 1px solid #312E3D;\n" +
                        "        border-bottom: 1px solid #312E3D;\n" +
                        "        border-radius: 6px;\n" +
                        "        box-shadow:4px 4px 5px rgba(0, 0, 0,.4);\n" +
                        "        text-align: center;" +
                        "        margin-left: auto;\n" +
                        "        margin-right: auto;\n" +
                        "    }\n" +
                        "    #form{\n" +
                        "        width:400px;\n" +
                        "        height: 300px;\n" +
                        "        text-align: center;" +
                        "       position: relative;\n" +
                        "        left:80%;\n" +
                        "        font-family: 'Open Sans',sans-serif;\n" +
                        "        margin: 0;\n" +
                        "        background-color: #312E3D;\n" +
                        "        border-top: 1px solid #312E3D;\n" +
                        "        border-left: 1px solid #312E3D;\n" +
                        "        border-right: 1px solid #312E3D;\n" +
                        "        border-bottom: 1px solid #312E3D;\n" +
                        "    }\n" +
                        "    #form2{\n" +
                        "        position: relative;\n" +
                        "        top: 5%;\n" +
                        "        left:2%;\n" +
                        "        width:1430px;\n" +
                        "        height: 300px;\n" +
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
                        "        text-align: center;\n" +
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
                        "    }\n" +
                        "    .card-image{\n" +
                        "\n" +
                        "        width:100%;\n" +
                        "    }\n " +
                        ".footer{\n" +
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
                        "    }" +
                        "</style></head> <body ><fieldset id='form2'>");
                out.println("<br><br> <a href='logout2' >Вернутся на основную страницу</a>");
                out.println("<form id='login4' action='searchf' method='post' >");
                out.println("<div id='form'");
                out.println("<b><h1>Найдены пользователи </h1></b>");
                out.println("</div></form></fieldset>");

                out.println("<br ><br ><br ><br ><br >");
                out.println("<table id='table1' width = '700px' align = 'center' style = 'border:1px solid #6d2323;' >");
                out.println("<tr > <td colspan = 8 align = 'center' > </td > </tr >");
                out.println("<tr > <td ><b > Фото </b ></td > <td ><b > Имя </b ></td > <td ><b > Фамилия </b ></td > <td ><b > Почта </b ></td > <td ><b > Подружиться </b ></td > </tr >");
                //<%
                int count = 0;
                String color = "#F9EBB3";

                    for(friendsList o:listS){
                        if ((count % 2) == 0) {
                            color = "#eeffee";
                        } else {
                            color = "#F9EBB3";
                        }
                        count++;
                        //var empList = (ArrayList) itr.next();

                        out.println("<tr style = 'background-color:<%="+color+"%>;' >");
                        out.println("<td ><img src='data:image/jpg;base64,"+o.getImageData()+"' width= '100' height= '100' alt=''/></td >");
                        out.println("<td ><b>"+o.get_client_firstName()+"</b> </td >");
                        out.println("<td ><b>"+o.get_client_lastName() +"</b></td >");
                        out.println("<td > <form action='place' method='post' > <label>\n" +
                                "\n" +"<input type='text' name= 'email' value='"+o.get_client_Email()+"' style= 'display:none'/>\n" +
                                "</label> <div> <a> <button class='but' type='submit'>"+o.get_client_Email()+"</button> </a> </div></form></td >");

//                        if (o.get_client_status().equals("1")){
//                            out.println("<td ><td > <form action='DeleteFriend' method='post' > <label>" +
//                                    "<input type='text' name= 'email' value='" + o.get_client_Email() + "' style= 'display:none'/>\n" +
//                                    "<input type='text' name= 'id2' value='" + o.get_client_id() + "' style= 'display:none'/>\n" +
//                                    "<input type='text' name= 'id1' value='" + client_id1 + "' style= 'display:none'/>\n" +
//                                    "</label><button type='submit'>" +
//                                    "<img src='data:image/jpg;base64," + base64Icon2 + "' width= '30' height= '30' alt=''/></button> </form></td >");
//                        }else
                            //if (o.get_client_status() == null){
                            out.println("<td > <form action='applicationFriend' method='post' > <label>" +
                                    "<input type='text' name= 'id2' value='" + o.get_client_id() + "' style= 'display:none'/>\n" +
                                    "<input type='text' name= 'id1' value='" + client_id1 + "' style= 'display:none'/>\n" +
                                    "</label><button class='but' type='submit'>" +
                                    "<img src='data:image/jpg;base64," + base64Icon + "' width= '30' height= '30' alt=''/></button> </form></td >");
                       // }
                    }

                if (count == 0) {

                out.println("<tr > <td colspan = 8 align = 'center' ><b > Не найдены </b ></td > </tr >");

                }
                out.println("</table ><footer class='footer'>" +
                "    <h4 class='footer-author'> REDit 2023. Курсовой проект сделан Анучиным Дмитрием бРИС-201.</h4>" +
                        "</footer>\n" +
                        " </body > </html >");
            }

    } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
    InstantiationException | IllegalAccessException ex) {
        throw new ServletException(ex);
    }
    }
}
