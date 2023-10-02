
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
// осуществление подключения к бд oracle
/**
 * Класс для подключения к бд oracle
 * Смотреть класс {@link connectionBD}
 * @author Anuchin Dmitry
 * @version 1.0
 */
public class connectionBD {
    /** Адрес базы данных*/
    private final String jdbcURL = "jdbc:oracle:thin:@localhost:1521/XE";
    /** Пользователь для подключения к базе данных*/
    private final String dbUser = "C##Friend1";
    /** Пароль для подключения к базе данных*/
    private final String dbPassword = "2002";
    /**
     * <p>Метод для открытия соединения с СУБД {@link connectionBD#dataB()}</p>
     * @return возвращает подключение к БД
     */
    public Connection dataB() throws ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("oracle.jdbc.OracleDriver").getDeclaredConstructor().newInstance();
        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }
    
    /**
     * <p>Метод для нахождения пользователя {@link connectionBD#selectValidate(String)}</p>
     * @return возвращает данные пользователя
     */
    public ResultSet selectValidate(String client_email) throws SQLException,
            ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД

        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM USERREDIT WHERE client_email=?");
        ps2.setString(1, client_email);

        return ps2.executeQuery();
    }
    
    /**
     * <p>Метод добавления пользователя {@link connectionBD#insertClient(String, String, String, String, String, String, InputStream)}</p>
     * @return успешное добавление пользователя в БД
     */
    public int insertClient(String client_firstname, String client_lastname, String client_surname,
                            String client_birthday, String client_email, String client_pass, InputStream inputStream){
        Connection con = null;//подключение к БД
        int i=0;
        try {
            con = dataB();
            PreparedStatement ps = con.prepareStatement("insert into userRedit (client_firstname,client_lastname,client_surename," +
                    " client_birthday, client_email,client_pass,CLIENT_IMAGE,client_count) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, client_firstname);
            ps.setString(2, client_lastname);
            ps.setString(3, client_surname);
            ps.setString(4, client_birthday);
            ps.setString(5, client_email);
            ps.setString(6, client_pass);
            ps.setBlob(7, inputStream);
            ps.setString(8, "0");
            i = ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return i;
    }

    /**
     * <p>Метод обнавления кол-ва входов {@link connectionBD#updateUser(String)}</p>
     * @return возвращает обновление кол-во вхождения
     */
    public int updateUser(String client_email){
        int rs2=0;
        Connection con = null;//подключение к БД
        try {
            con = dataB();
            //обновление числа посещений
            String sql= "UPDATE USERREDIT SET CLIENT_COUNT = CLIENT_COUNT+1, CLIENT_NETWORK='В сети' WHERE client_email='" +client_email+ "'";

            Statement ps = con.createStatement();

            rs2 = ps.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return rs2;
    }

    /**
     * <p>Метод проверки {@link connectionBD#selectUser(String, String)}</p>
     * @return возвращает проверку нахождения клиента
     */
    public ResultSet selectUser(String client_email, String client_pass) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM USERREDIT WHERE client_email=? AND client_pass=?");
        ps2.setString(1, client_email);
        ps2.setString(2, client_pass);

        return ps2.executeQuery();
    }

    /**
     * <p>Метод обновления данных {@link connectionBD#updateDataUser(String, String, String, String, String, String, String, String, String)}</p>
     * @return возвращает обновлённые данные
     */
    public int updateDataUser(String client_firstname, String client_lastname, String client_surname, String client_birthday,String client_email,
                              String client_phone, String client_hobby, String client_address,String client_country){
        int rs3=0;
        Connection con = null;//подключение к БД
        try {
            con = dataB();
            //обновление числа посещений
            String sql= "UPDATE USERREDIT SET CLIENT_FIRSTNAME = '"+client_firstname+"', CLIENT_LASTNAME='"+client_lastname+"'," +
                    "CLIENT_SURENAME='"+client_surname+"',CLIENT_BIRTHDAY='"+client_birthday+"'," +
                    "CLIENT_EMAIL='"+client_email+"',CLIENT_PHONE='"+client_phone+"',CLIENT_HOBBY='"+client_hobby+"'," +
                    "CLIENT_ADDRESS='"+client_address+"', CLIENT_COUTRY = '"+client_country+"'WHERE client_email='" +client_email+ "'";

            Statement ps = con.createStatement();

            rs3 = ps.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return rs3;
    }

    /**
     * Метод - сохранения фото в бд
     * @see connectionBD#updateImage(InputStream, String)
     * @param file - путь файла
     * @param email - почта
     */
    public int updateImage(InputStream file, String email){
        int row=0;
        Connection con = null;//подключение к БД
        PreparedStatement preparedStatement;
        try {
            con = dataB();
            //обновление числа посещений
            String SQL = "UPDATE USERREDIT SET CLIENT_IMAGE=? WHERE CLIENT_EMAIL='" + email + "'";
            preparedStatement = con.prepareStatement(SQL);
            if (file != null) {

                preparedStatement.setBlob(1, file);
            }

            row = preparedStatement.executeUpdate();
            preparedStatement.execute();

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return row;
    }

    /**
     * <p>Метод возвращения данных друзей {@link connectionBD#selectCountFriends(String)}</p>
     * @return возвращает друзей
     */
    public ResultSet selectCountFriends(String client_id) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        //возвращает значение определенного запроса
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERREDIT u, FRIENDS f WHERE " +
                "((u.CLIENT_ID=f.FRIEND_ONE) OR (u.CLIENT_ID=f.FRIEND_TWO))AND( u.client_id=?)");

        ps.setString(1, client_id);//вставка параметров для поиска
        return ps.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод возращения друзей по 1 столбцу таблицы в бд {@link connectionBD#selectFriendOne(String)}</p>
     */
    public ResultSet selectFriendOne(String i1) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        PreparedStatement ps2= con.prepareStatement("SELECT * FROM USERREDIT u LEFT OUTER JOIN FRIENDS F on (u.CLIENT_ID = F.FRIEND_ONE) " +
                "WHERE ((F.STATUS=2)AND (u.CLIENT_ID=?))");
        ps2.setString(1,i1);
        return ps2.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки


    }

    /**
     * <p>Метод возращения друзей по 2 столбцу таблицы в бд {@link connectionBD#selectFriendTwo(String)}</p>
     */
    public ResultSet selectFriendTwo(String i1) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM USERREDIT u LEFT OUTER JOIN FRIENDS F on (u.CLIENT_ID = F.FRIEND_TWO) " +
                "WHERE ((F.STATUS=2)AND (u.CLIENT_ID=?))");
        ps2.setString(1,i1);
        return ps2.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод нахождения подписчиков {@link connectionBD#selectNotCountFriends(String)}</p>
     */
    public ResultSet selectNotCountFriends(String client_id) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        //возвращает значение определенного запроса
        PreparedStatement ps3 = con.prepareStatement("SELECT * FROM USERREDIT u, FRIENDS f WHERE (u.CLIENT_ID=f.FRIEND_TWO)AND( u.client_id=?)");
        ps3.setString(1, client_id);//вставка параметров для поиска
        return ps3.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод возращения подписчиков по 1 столбцу таблицы в бд  {@link connectionBD#selectNotFriendOne(String)}</p>
     */
    public ResultSet selectNotFriendOne(String i1) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        PreparedStatement ps4= con.prepareStatement("SELECT * FROM USERREDIT u LEFT OUTER JOIN FRIENDS F on (u.CLIENT_ID = F.FRIEND_ONE) " +
                "WHERE (F.STATUS=1) AND (u.CLIENT_ID=?) ");
        ps4.setString(1,i1);
        return ps4.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод Нахождения друзей в бд {@link connectionBD#selectSearchFriends(String)}</p>
     */
    public ResultSet selectSearchFriends(String word) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        String sql="SELECT * FROM USERREDIT u  WHERE u.CLIENT_FIRSTNAME  like '%"+word+"%' OR u.CLIENT_LASTNAME like '%"+word+"%'" +
                " OR u.CLIENT_SURENAME like '%"+word+"%' OR u.CLIENT_ADDRESS like '%"+word+"%' OR u.CLIENT_HOBBY like '%"+word+"%'";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();//выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод вывод друзей {@link connectionBD#selectApplicationFriend(String, String)}</p>
     */
    public ResultSet selectApplicationFriend(String client_id1,String client_id2) throws SQLException, ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = dataB();//подключение к БД
        PreparedStatement ps3 = con.prepareStatement("SELECT * FROM FRIENDS WHERE (FRIEND_ONE=? AND FRIEND_TWO=?)OR (FRIEND_ONE=? AND FRIEND_TWO=?)");
        //System.out.println("3 "+client_id);
        ps3.setString(1, client_id1);
        ps3.setString(2, client_id2);
        ps3.setString(3, client_id2);
        ps3.setString(4, client_id1);

        return ps3.executeQuery();
        //выполняет выборку (SELECT) и возвращает объект ResultSet, содержащий результат выборки

    }

    /**
     * <p>Метод обновления таблицы друзей {@link connectionBD#updateApplicationFriend(String, String)}</p>
     */
    public int updateApplicationFriend(String client_id1, String client_id2){
        int rs2=0;
        Connection con = null;//подключение к БД
        try {
            con = dataB();
            //обновление числа посещений
            String sql= "UPDATE FRIENDS SET STATUS = '"+2+"'WHERE (FRIEND_ONE='"+client_id1+ "'AND FRIEND_TWO='" +
                    ""+client_id2+"')OR (FRIEND_ONE='"+client_id2+"' AND FRIEND_TWO='"+client_id1+"')";

            Statement ps = con.createStatement();

            rs2 = ps.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return rs2;
    }

    /**
     * <p>Метод добавления нового друга {@link connectionBD#insertApplicationFriend(String, String)}</p>
     */
    public int insertApplicationFriend(String client_id1, String client_id2){
        Connection con = null;//подключение к БД
        int i=0;
        try {
            con = dataB();
            PreparedStatement ps = con.prepareStatement("insert into FRIENDS (friend_one,friend_two, status) values(?,?,?)");
            ps.setString(1, client_id1);
            ps.setString(2, client_id2);
            ps.setString(3, "1");
            i = ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * <p>Метод удаления друга {@link connectionBD#deleteFriend(String, String)}</p>

     */
    public int deleteFriend(String client_id1, String client_id2){
        Connection con = null;//подключение к БД
        int i=0;
        try {
            con = dataB();
            PreparedStatement ps = con.prepareStatement("DELETE FROM FRIENDS WHERE (friend_one=? AND friend_two=?) OR (friend_one=? AND friend_two=?)");
            ps.setString(1, client_id1);
            ps.setString(2, client_id2);
            ps.setString(3, client_id2);
            ps.setString(4, client_id1);
            i = ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * <p>Метод обновления пароля {@link connectionBD#updatePassword(String, String)}</p>
     */
    public int updatePassword(String client_email,String client_pas){
        int rs2=0;
        Connection con = null;//подключение к БД
        try {
            con = dataB();
            //обновление числа посещений
            String sql = "UPDATE USERREDIT SET CLIENT_PASS =" + client_pas + "  WHERE client_email='" + client_email + "'";

            Statement ps = con.createStatement();

            rs2 = ps.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return rs2;
    }
}
