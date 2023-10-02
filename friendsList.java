
/**
 * Класс получения данных друзей
 *
 * Смотреть класс {@link friendsList}
 * @author Anuchin Dmitry
 * @version 1.0
 */
public class friendsList {//класс друзья
    /** id пользователя в бд*/
    private String client_id;
    /** Имя пользователя*/
    private String client_firstname;
    /** Фамилия пользователя*/
    private String client_lastname;
    /** Фото пользователя*/
    private String imageData;
    /** Почта пользователя*/
    private String client_email;
    /** Статус дружбы пользователя*/
    private String client_status;
    private String client_network;

    /**
     * Метод - получения нового id пользователя
     * @see User#get_client_Id()
     *
     */
    String get_client_id(){return client_id;}
    /**
     * Метод - записи нового id пользователя
     * @see User#set_client_Id(int)
     *
     */
    void set_client_id(String client_id)
    {
        this.client_id=client_id;
    }
    /**
     * Метод - получения имени пользователя
     * @see friendsList#get_client_firstName()
     */
    String get_client_firstName(){return client_firstname;}
    void set_client_firstName(String client_firstname)
    {
        this.client_firstname=client_firstname;
    }
    /**
     * Метод - получения фамилии пользователя
     * @see friendsList#get_client_lastName()
     */
    String get_client_lastName(){return client_lastname;}
    void set_client_lastName(String client_lastname)
    {
        this.client_lastname=client_lastname;
    }

    /**
     * Метод - получения почты пользователя
     * @see friendsList#get_client_Email()
     */
    String get_client_Email(){return client_email;}
    void set_client_Email(String client_email)
    {
        this.client_email=client_email;
    }
    /**
     * Метод - получения фото пользователя
     * @see friendsList#getImageData()
     */
    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
    String get_client_status(){return client_status;}

    void set_client_status(String client_status)
    {
        this.client_status=client_status;
    }
    String get_client_network(){return client_network;}
    void set_client_network(String client_network)
    {
        this.client_network=client_network;
    }

}
