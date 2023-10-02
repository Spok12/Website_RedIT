import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;
import java.util.*;
//Сервлет для вывода друзей и подписчиков, также осуществляется строка с поиском
/**
 * Сервлет для вывода друзей и подписчиков, также осуществляется строка с поиском
 * Смотреть класс {@link outputYourFrinds}
 * @author Anuchin Dmitry
 * @version 1.0
 */
@WebServlet("/fr")
public class Friends  extends HttpServlet {
    /**
     * Метод post - вывода друзей и подписчиков
     * @see Friends#doPost(HttpServletRequest, HttpServletResponse)
     * @param response - управляет ответом на запрос
     * @param request - хранит информацию о запросе
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String client_id = request.getParameter("id");
        String i1="";
        ConvectorPhoto conver = new ConvectorPhoto();
        //изображения иконок кнопок
        File f1=new File("D:\\учёба\\3 курс\\2 семестр\\Сетевое программирование\\red_it\\src\\main\\java\\kisspng-computer-icons-canada-organization-company-sponsor-5af56042995d00.2474135415260304026282.jpg");
        FileInputStream f11=new FileInputStream(f1);
        String base64Icon = conver.images(f11);
        File f2=new File("D:\\учёба\\3 курс\\2 семестр\\Сетевое программирование\\red_it\\src\\main\\java\\episodul-doisprezece-mastermytime.jpg");
        FileInputStream f22=new FileInputStream(f2);
        String base64Icon2 = conver.images(f22);
        String status ="";
        String status2 ="";
        ArrayList<friendsList> listF = new ArrayList<friendsList>();//список друзей
        ArrayList<friendsList> listnotF = new ArrayList<friendsList>();//список подписчиков
        try {
            ///вывод друзей
            outputYourFrinds oyf=new outputYourFrinds();
            listF=oyf.listFriends(client_id);
            connectionBD c=new connectionBD();//создание объекта класса
            String j="";
            ResultSet rs3 = c.selectNotCountFriends(client_id);
            while (rs3.next()) {///////////
                status2=rs3.getString("STATUS");
                j= (rs3.getString("FRIEND_ONE"));
                ResultSet rs4 = c.selectNotFriendOne(j);
                if(rs4.next()) {
                    friendsList fll =new friendsList();
                    Blob blob = rs4.getBlob("CLIENT_IMAGE");
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] b = new byte[4096];
                    int bytesR = -1;

                    while ((bytesR = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, bytesR);
                    }
                    byte[] imageB = outputStream.toByteArray();

                    String base64Image = Base64.getEncoder().encodeToString(imageB);
                    //FileInputStream inputStream = (FileInputStream) blob.getBinaryStream();

                    //listImage.add(base64Image);
                    fll.set_client_id(rs4.getString("CLIENT_ID"));
                    fll.set_client_firstName(rs4.getString("CLIENT_FIRSTNAME"));
                    fll.set_client_lastName(rs4.getString("CLIENT_LASTNAME"));
                    fll.set_client_Email(rs4.getString("CLIENT_EMAIL"));
                    //fl.set_client_status(rs4.getString("STATUS"));
                    fll.setImageData(base64Image);
                    fll.set_client_network(rs4.getString("CLIENT_NETWORK"));
                    listnotF.add(fll);
                }
            }
            try (PrintWriter out = response.getWriter()) {//html
                out.println("<!DOCTYPE html>");
                out.println("<html><head>");
                out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                out.println("<title>Друзья</title> " +
                        "<style>" +
                        "    html{" +
                        "        width: 100%;" +
                        "        height: 100%;" +
                        "        overflow: visible;" +
                        "        font-style: italic;" +
                        "    }" +
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
                        "        top: 115%;\n" +
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
                out.println("<b><h1>Найти друга</h1></b>");
                out.println("<b>Введите слово для поиска:</b>" +
                                "<input type='text' name='id1' value='" + client_id + "' style= 'display:none'/><br/>"+
                        "<input type='text' name='word' /><br/>");
                out.println("<br><br>");
                out.println("<button class='but' type='submit'>Поиск</button>");
                out.println("</div>");
                out.println("<br ><br ><br ></form></fieldset>");
//-----------------------------------------------------------------------
                out.println("<div style='text-align: center'>");

                out.println("<br >");
                out.println("<table id='table1' style = 'border:1px solid #6d2323;' >");
                out.println("<b><h1>На вас подписаны</h1></b>");
                out.println("<tr > <td colspan = 8 align = 'center' > </td > </tr >");
                out.println("<tr >  <td ><b > Фото </b ></td > <td ><b > Имя </b ></td > <td ><b > Фамилия </b ></td > <td ><b > Почта </b ></td > <td ><b >Подписаться</b ></td > </tr >");
                //<%
                int count = 0;
                String color = "#F9EBB3";

                for(friendsList o:listnotF){
                    if ((count % 2) == 0) {
                        color = "#eeffee";
                    } else {
                        color = "#F9EBB3";
                    }
                    count++;
                    //var empList = (ArrayList) itr.next();

                    out.println("<tr style = 'background-color:<%=color%>;' >");
                    out.println("<td ><img src='data:image/jpg;base64,"+o.getImageData()+"' width= '100' height= '100' alt=''/></td >");
                    out.println("<td ><b>"+o.get_client_firstName()+" </b></td >");
                    out.println("<td ><b>"+o.get_client_lastName() +"</b></td >");
                    out.println("<td > <form action='place' method='post' > <label>\n" +
                            "\n" +"<input type='text' name= 'email' value='"+o.get_client_Email()+"' style= 'display:none'/>\n" +
                            "<input type='text' name= 'id1' value='" + client_id + "' style= 'display:none'/>\n" +
                            "</label> <div> <a> <button class='but' type='submit'>"+o.get_client_Email()+"</button> </a> </div></form></td >");
                    //out.println("<td >"+o.get_client_network()+" </td >");
                   // System.out.println(o.get_client_status()+"          f                 ");
                    //if (o.get_client_status().equals("1")){
//                    out.println("<td ><td > <form action='DeleteFriend' method='post' > <label>" +
//                            "<input type='text' name= 'email' value='" + o.get_client_Email() + "' style= 'display:none'/>\n" +
//                            "<input type='text' name= 'id2' value='" + o.get_client_id() + "' style= 'display:none'/>\n" +
//                            "<input type='text' name= 'id1' value='" + client_id + "' style= 'display:none'/>\n" +
//                            "</label><button type='submit'>" +
//                            "<img src='data:image/jpg;base64," + base64Icon2 + "' width= '30' height= '30' alt=''/></button> </form></td >");
                    out.println("<td > <form action='applicationFriend' method='post' > <label>" +
                                "<input type='text' name= 'id2' value='" + o.get_client_id() + "' style= 'display:none'/>\n" +
                                "<input type='text' name= 'id1' value='" + client_id + "' style= 'display:none'/>\n" +
                                "<input type='text' name= 'email' value='" + o.get_client_Email() + "' style= 'display:none'/>\n" +
                                "</label><button class='but' type='submit'>" +
                                "<img src='data:image/jpg;base64," + base64Icon + "' width= '30' height= '30' alt=''/></button> </form></td >");
                }

                if (count == 0) {

                    out.println("<tr > <td colspan = 8 align = 'center' ><b > Не найдены </b ></td > </tr >");

                }

                out.println("</div></table >");


//-----------------------------------------------------------------------
                out.println("<div >");

                out.println("<br >");
                out.println("<table id='table1' style = 'border:1px solid #6d2323;' >");
                out.println("<b><h1>Ваши друзья</h1></b>");
                out.println("<tr > <td colspan = 8 align = 'center' > </td > </tr >");
                out.println("<tr >  <td ><b > Фото </b ></td > <td ><b > Имя </b ></td > <td ><b > Фамилия </b ></td > <td ><b > Почта </b ></td >  <td ><b >Отписаться </b ></td > </tr >");
                //<%
                int count0 = 0;
                String color0 = "#F9EBB3";
                for(friendsList o:listF){
                    if ((count0 % 2) == 0) {color0 = "#eeffee";} else {color0 = "#F9EBB3";}
                    count0++;
                    out.println("<tr style = 'background-color:<%=color%>;' >");
                    out.println("<td ><img src='data:image/jpg;base64,"+o.getImageData()+"' width= '100' height= '100' alt=''/></td >");
                    out.println("<td ><b>"+o.get_client_firstName()+"</b> </td >");
                    out.println("<td ><b>"+o.get_client_lastName() +"</b></td >");
                    out.println("<td > <form action='place' method='post' > <label>\n" +
                            "\n" +"<input type='text' name= 'email' value='"+o.get_client_Email()+"' style= 'display:none'/>\n" +
                            "</label> <div> <a> <button class='but' type='submit'>"+o.get_client_Email()+"</button> </a> </div></form></td >");
                    //out.println("<td >"+o.get_client_network()+" </td >");
                    //System.out.println(o.get_client_status()+"          f                 ");
                    //if (o.get_client_status().equals("1")){
                        out.println("<td > <form action='DeleteFriend' method='post' > <label>" +
                                "<input type='text' name= 'email' value='" + o.get_client_Email() + "' style= 'display:none'/>\n" +
                                "<input type='text' name= 'id2' value='" + o.get_client_id() + "' style= 'display:none'/>\n" +
                                "<input type='text' name= 'id1' value='" + client_id + "' style= 'display:none'/>\n" +
                                "</label><button class='but' type='submit'>" +
                                "<img src='data:image/jpg;base64," + base64Icon2 + "' width= '30' height= '30' alt=''/></button> </form></td >");
                }
                if (count0 == 0) {
                    out.println("<tr > <td colspan = 8 align = 'center' ><b > Не найдены </b ></td > </tr >");
                }
                out.println("</div>");

                out.println("</table ><br ><br ><footer class='footer'>" +
                        "    <h4 class='footer-author'> REDit 2023. Курсовой проект сделан Анучиным Дмитрием бРИС-201.</h4>" +
                        "</footer>\n" +
                        "</body > </html >");
            }
        }
        catch(Exception se) {
            se.printStackTrace();
        }

    }
}
