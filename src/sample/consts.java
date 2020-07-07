package sample;

public class consts {
    public static final String USER_TABLE = "users"; //название таблицы в БД
    public static final String USERS_ID = "idusers";
    public static final String USERS_FIRSTNAME = "users_firstname";
    public static final String USERS_LASTNAME = "users_lastname";
    public static final String USERS_USERNAME = "users_username";
    public static final String USERS_PASSWORD = "users_password";

    public static final String MAGAZINES_TABLE = "magazines";
    public static final String MAGAZINES_ID = "idmagazines";
    public static final String MAGAZINES_NAME = "magazine_name";
    public static final String MAGAZINES_PUBLISHER = "publishers_idpublishers";
    public static final String MAGAZINES_THEME = "themes_idthemes";

    //publishers
    public static final String PUBLISHERS_TABLE = "publishers";
    public static final String PUBLISHERS_ID = "idpublishers";
    public static final String PUBLISHERS_NAME = "publishers_name";

    //themes
    public static final String THEMES_TABLE ="themes";
    public static final String THEMES_ID = "idthemes";
    public static final String THEMES_NAME = "themes_name";

    //numbers
    public static final String NUMBERS_TABLE = "magazines_numbers";
    public static final String NUMBERS_ID = "idmagazines_numbers";
    public static final String NUMBERS_NUMBER = "magazines_numbers_number";
    public static final String NUMBERS_YEAR = "magazines_numbers_year";
    public static final String NUMBERS_PAGES = "magazines_numbers_pages";
    public static final String NUMBERS_RELDATE = "magazines_numbers_rel_date";
    public static final String NUMBERS_MAGAZINEID = "magazines_idmagazines";

    //articles
    public static final String ARTICLES_TABLE = "magazines_articles";
    public static final String ARTICLES_ID = "idmagazines_articles";
    public static final String ARTICLES_NAME = "magazines_articles_name";
    public static final String ARTICLES_AUTHOR = "magazines_articles_authors";
    public static final String ARTICLES_BEGINPAGE = "magazines_articles_begin_page";
    public static final String ARTICLES_ENDPAGE = "magazines_articles_end_page";
    public static final String ARTICLES_NUMBERID = "magazines_numbers_idmagazines_numbers";
    public static final String ARTICLES_THEMEID= "themes_idthemes";

}
