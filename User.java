import java.io.Serializable;
/**
 * Класс получения данных пользователя
 *
 * Смотреть класс {@link User}
 * @author Anuchin Dmitry
 * @version 1.0
 */
public class User implements Serializable{//процесс сохранения состояния объекта в последовательность байт
    //класс основного пользователя
    /** id пользователя в бд*/
    private int client_id;
    /** Кол-во посещений страницы пользователя*/
    private int client_count;
    /** Имя пользователя*/
    private String client_firstname;
    /** Фамилия пользователя*/
    private String client_lastname;
    /** Отчестов пользователя*/
    private String client_surname ;
    /** День рождения пользователя*/
    private String client_birthday ;
    /** Фото пользователя*/
    private String imageData;
    /** Почта пользователя*/
    private String client_email;
    /** Пароль пользователя*/
    private String client_pass;
    private String client_time;
    private String client_IPadr;
    /** Телефон пользователя*/
    private String client_phone;
    /** Хобби пользователя*/
    private String client_hobby;
    /** Адрес проживания пользователя*/
    private String client_address;
    /** Страна пользователя*/
    private String client_country;
    /** Нахождение в сети пользователя*/
    private String client_network;
    /**
     * Конструктор - создание нового объекта
     * @see User#User()
     */
    public User(){ }

    /**
     * Метод - получения нового id пользователя
     * @see User#get_client_Id()
     * 
     */
    int get_client_Id(){return client_id;}
    /**
     * Метод - записи нового id пользователя
     * @see User#set_client_Id(int)
     *
     */
    void set_client_Id(int client_id)
    {
        this.client_id=client_id;
    }
    /**
     * Метод - получения кол-ва посещений пользователя
     * @see User#get_client_Count()
     *
     */
    int get_client_Count(){return client_count;}
    /**
     * Метод - запись кол-ва посещений пользователя
     * @see User#set_client_Count(int)
     */
    void set_client_Count(int client_count)
    {
        this.client_count=client_count;
    }
    /**
     * Метод - получения имя пользователя
     * @see User#get_client_firstName() 
     *
     */
    String get_client_firstName(){return client_firstname;}
    /**
     * Метод - запись имени пользователя
     * @see User#set_client_firstName(String) 
     *
     */
    void set_client_firstName(String client_firstname)
    {
       this.client_firstname=client_firstname;
    }
    /**
     * Метод - получения фамилии пользователя
     * @see User#get_client_lastName() 
     */
    String get_client_lastName(){return client_lastname;}
    /**
     * Метод - запись фамилии пользователя
     * @see User#set_client_lastName(String) 
     *
     */
    void set_client_lastName(String client_lastname)
    {
        this.client_lastname=client_lastname;
    }
    /**
     * Метод - получения отчества пользователя
     * @see User#get_client_surName() 
     */
    String get_client_surName(){return client_surname;}
    /**
     * Метод - запись отчества пользователя
     * @see User#set_client_surName(String) 
     */
    void set_client_surName(String client_surname)
    {
        this.client_surname=client_surname;
    }
    /**
     * Метод - получения дня рождения пользователя
     * @see User#get_client_birthday() 
     *
     */
    String get_client_birthday(){return client_birthday;}
    /**
     * Метод - запись дня рождения пользователя
     * @see User#set_client_birthday(String)
     */
    void set_client_birthday(String client_birthday)
    {
        this.client_birthday=client_birthday;
    }
    /**
     * Метод - получения фото пользователя
     * @see User#getImageData() 
     *
     */
    public String getImageData() {
        return imageData;
    }
    /**
     * Метод - запись фото пользователя
     * @see User#setImageData(String)
     */
    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
    /**
     * Метод - получения почты пользователя
     * @see User#get_client_Email() 
     *
     */
    String get_client_Email(){return client_email;}
    /**
     * Метод - запись почты пользователя
     * @see User#set_client_Email(String) 
     *
     */
    void set_client_Email(String client_email)
    {
        this.client_email=client_email;
    }
    /**
     * Метод - получения телефона пользователя
     * @see User#get_client_phone() 
     *
     */
    String get_client_Pass(){return client_pass;}
    void set_client_Pass(String client_pas)
    {
        this.client_pass=client_pass;
    }

    String get_client_Time(){return client_time;}
    void set_client_Time(String client_time)
    {
        this.client_time=client_time;
    }
    String get_client_IPadr(){return client_IPadr;}
    void set_client_IPadr(String client_IPadr)
    {
        this.client_IPadr=client_IPadr;
    }
    
    String get_client_phone(){return client_phone;}
    /**
     * Метод - запись телефона пользователя
     * @see User#set_client_phone(String)
     */
    void set_client_phone(String client_phone)
    {
        this.client_phone=client_phone;
    }
    /**
     * Метод - получения хобби пользователя
     * @see User#get_client_hobby()
     */
    String get_client_hobby(){return client_hobby;}
    /**
     * Метод - запись хобби пользователя
     * @see User#set_client_hobby(String)
     */
    void set_client_hobby(String client_hobby)
    {
        this.client_hobby=client_hobby;
    }
    /**
     * Метод - получения адреса пользователя
     * @see User#get_client_address() 
     *
     */
    String get_client_address(){return client_address;}
    /**
     * Метод - запись адреса пользователя
     * @see User#set_client_address(String) 
     *
     */
    void set_client_address(String client_address)
    {
        this.client_address=client_address;
    }
    /**
     * Метод - получения страны пользователя
     * @see User#get_client_country() 
     *
     */
    String get_client_country(){return client_country;}
    /**
     * Метод - запись страны пользователя
     * @see User#set_client_country(String) 
     *
     */
    void set_client_country(String client_country)
    {
        this.client_country=client_country;
    }
    /**
     * Метод - получения в сети пользователя
     * @see User#get_client_network() 
     *
     */
    String get_client_network(){return client_network;}
    /**
     * Метод - запись в сети пользователя
     * @see User#set_client_network(String)
     *
     */
    void set_client_network(String client_network)
    {
        this.client_network=client_network;
    }

}
