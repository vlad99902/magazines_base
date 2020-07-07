package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.print.attribute.standard.NumberUp;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseHandler extends configs {
    Connection dbConnection;

    public Connection getDbConnection()

            throws ClassNotFoundException, SQLException{
        String ConnectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?&serverTimezone=UTC&useSSL = false&verifyServerCertificate=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(ConnectionString, dbUser,
                dbPass);
        return dbConnection;
    }

    //регистрация пользователя
    public void signUpUser (User user){
        String insert = "INSERT INTO " + consts.USER_TABLE + "(" +
                consts.USERS_FIRSTNAME + "," + consts.USERS_LASTNAME + "," +
                consts.USERS_USERNAME + "," + consts.USERS_PASSWORD + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // поиск пользователя в базе данных при входе
    public ResultSet getUser (User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + consts.USER_TABLE + " WHERE " +
                consts.USERS_USERNAME + "=? AND " + consts.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    //ad magazine
    public void addMagazine (MagazinesTable Magazine) {
        String insert = "INSERT INTO " + consts.MAGAZINES_TABLE + "(" +
                consts.MAGAZINES_NAME + "," + consts.MAGAZINES_PUBLISHER + "," +
                consts.MAGAZINES_THEME + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, Magazine.getMagazinesName());
            prSt.setInt(2, Magazine.getMagazinesPublisherId());
            prSt.setInt(3, Magazine.getMagazinesThemesId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //add number
    public void addNumber (MagazineNumber number) {
        String insert = "INSERT INTO " + consts.NUMBERS_TABLE + "(" +
                consts.NUMBERS_NUMBER + "," + consts.NUMBERS_YEAR + "," +
                consts.NUMBERS_PAGES + "," +
                consts.NUMBERS_RELDATE + "," +
                consts.NUMBERS_MAGAZINEID + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, number.getMagazineNumberNumber());
            prSt.setInt(2, number.getMagazineNumberYear());
            prSt.setInt(3, number.getMagazineNumberPages());
            prSt.setDate(4, convertUtilToSql(number.getMagazineNumberDate()));
            prSt.setInt(5, number.getMagazineId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //add article
    public void addArticle (NumberArticle article) {
        String insert = "INSERT INTO " + consts.ARTICLES_TABLE + "(" +
                consts.ARTICLES_NAME + "," + consts.ARTICLES_AUTHOR + "," +
                consts.ARTICLES_BEGINPAGE + "," +
                consts.ARTICLES_ENDPAGE + "," +
                consts.ARTICLES_NUMBERID + ", "
                + consts.ARTICLES_THEMEID +")" +
                "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, article.getNumberArticleName());
            prSt.setString(2, article.getNumberArticleAuthor());
            prSt.setInt(3, article.getNumberArticleBeginPage());
            prSt.setInt(4, article.getNumberArticleEndPage());
            prSt.setInt(5, article.getNumberArticleIdNumber());
            prSt.setInt(6, article.getNumberArticleIdTheme());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //ad new item to directory
    public void addItemToDirectory (Publisher item, String tableRef, String nameRef){
        String insert = "INSERT INTO " + tableRef + "(" +
                nameRef + ")" +
                "VALUES(?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, item.getPublisherName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //return magazines by date from db
    public ObservableList<MagazinesTable> returnMagazinesByDateFromDb (Integer themeId, String beginDate, String endDate) {
        ObservableList<MagazinesTable> data = FXCollections.observableArrayList();
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT magazine_name, " +
                    "COUNT(magazines_numbers.magazines_idmagazines) as numOfNumbers, " +
                    "SUM(magazines_numbers.magazines_numbers_pages) as numOfPages " +
                    "FROM db_magazines_release.magazines " +
                    "JOIN db_magazines_release.magazines_numbers ON magazines_numbers.magazines_idmagazines = magazines.idmagazines " +
                    "WHERE magazines_numbers.magazines_numbers_rel_date BETWEEN " + beginDate + " AND "+ endDate +
                    " AND (magazines.themes_idthemes = "+ themeId +") " +
                    "GROUP BY magazine_name " +
                    "ORDER BY magazine_name");

            while (result.next()) {
                String name = result.getString("magazine_name");
                Integer numOfNum = result.getInt("numOfNumbers");
                Integer numOfPages = result.getInt("numOfPages");
                data.add(new MagazinesTable(name,numOfNum,numOfPages));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;

    }
    // take directoryes items
    public ObservableList<Publisher> returnListFromDb (String tableName, String tableColumn, String tableColumnId) {
        ObservableList<Publisher> data = FXCollections.observableArrayList();
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT " + tableColumn
                    + ", " + tableColumnId + " FROM "+ tableName + " ORDER BY " + tableColumnId);

            while (result.next()) {
                Integer id = result.getInt(tableColumnId);
                String Name = result.getString(tableColumn);
                Publisher publisher = new Publisher(id, Name);
                data.add(publisher);
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;

    }

    //return directories items by other item
    public ObservableList<Publisher> returnListFromDb (String tableName, String tableColumn,
                                                       String tableColumnId, Integer tableOtherItemId, String tableItemId) {
        ObservableList<Publisher> data = FXCollections.observableArrayList();
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT " + tableColumn
                    + ", " + tableColumnId + " FROM "+ tableName + " WHERE " +
                    tableItemId + " = " + tableOtherItemId);

            while (result.next()) {
                Integer id = result.getInt(tableColumnId);
                String Name = result.getString(tableColumn);
                Publisher publisher = new Publisher(id, Name);
                data.add(publisher);
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;

    }

    //выборка таблицы паблишеров c суммой журналов
    public ObservableList<Publisher> returnListFromDb (String tableName){
        ObservableList<Publisher> data = FXCollections.observableArrayList();
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT publishers_name, " +
                    "COUNT(publishers_idpublishers) " +
                    "FROM " + tableName +
                    " left outer JOIN db_magazines_release.magazines ON " +
                    "publishers.idpublishers = magazines.publishers_idpublishers " +
                    "group by publishers_name " +
                    "order by publishers_name ");

            while (result.next()) {
                String name = result.getString("publishers_name");
                Integer count = result.getInt("COUNT(publishers_idpublishers)");
                Publisher publisher = new Publisher(name,count);
                data.add(publisher);
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //load magazines from db to show it
    public ObservableList<MagazinesTable> returnMagazinesTableFromDb () {
        ObservableList<MagazinesTable> data = null;
        try {
            DataBaseHandler db = new DataBaseHandler();
            Connection con = db.getDbConnection();
            data = FXCollections.observableArrayList();
            ResultSet result = con.createStatement().executeQuery
                    ("SELECT idmagazines, magazine_name, publishers_name, themes_name FROM magazines" +
                            " JOIN publishers ON magazines.publishers_idpublishers = publishers.idpublishers" +
                            " JOIN themes ON magazines.themes_idthemes = themes.idthemes");

            while (result.next()) {
                Integer Id = result.getInt(consts.MAGAZINES_ID);
                String Name = result.getString(consts.MAGAZINES_NAME);
                String Publisher = result.getString(consts.PUBLISHERS_NAME);
                String Theme = result.getString(consts.THEMES_NAME);
                data.add(new MagazinesTable(Id, Name, Publisher, Theme));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }


    //выборка таблицы номеров журналов
    public ObservableList<MagazineNumber> returnMagazinesNumbersTableFromDb (Integer magazineId) {
        ObservableList<MagazineNumber> data = null;
        try {
            DataBaseHandler db = new DataBaseHandler();
            Connection con = db.getDbConnection();
            data = FXCollections.observableArrayList();
            ResultSet result = con.createStatement().executeQuery
                    ("SELECT " + consts.NUMBERS_NUMBER + ", " +
                            consts.NUMBERS_YEAR + ", " + consts.NUMBERS_PAGES + ", " + consts.NUMBERS_RELDATE
                            +" FROM " + consts.NUMBERS_TABLE +
                            " WHERE " + consts.NUMBERS_MAGAZINEID + " = " + magazineId);

            while (result.next()) {
                Integer number = result.getInt(consts.NUMBERS_NUMBER);
                Integer pages = result.getInt(consts.NUMBERS_PAGES);
                Integer year = result.getInt(consts.NUMBERS_YEAR);
                Date date = result.getDate(consts.NUMBERS_RELDATE);
                data.add(new MagazineNumber(number,year,pages,date));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //выборка таблицы статей журналов по номеру
    public ObservableList<NumberArticle> returnMagazinesNumberArticleTableFromDb (Integer numberId) {
        ObservableList<NumberArticle> data = null;
        try {
            DataBaseHandler db = new DataBaseHandler();
            Connection con = db.getDbConnection();
            data = FXCollections.observableArrayList();
            ResultSet result = con.createStatement().executeQuery
                    ("SELECT " + consts.ARTICLES_NAME + ", " +
                            consts.ARTICLES_AUTHOR + ", " + consts.ARTICLES_BEGINPAGE + ", "
                            + consts.ARTICLES_ENDPAGE + ", " +consts.THEMES_NAME +" FROM " + consts.ARTICLES_TABLE +
                            " JOIN " + consts.THEMES_TABLE + " ON " +
                            consts.ARTICLES_TABLE + "." + consts.ARTICLES_THEMEID + " = " +
                            consts.THEMES_TABLE + "." + consts.THEMES_ID + " WHERE " + consts.ARTICLES_NUMBERID + " = " + numberId);

            while (result.next()) {
               String name = result.getString(consts.ARTICLES_NAME);
               String author = result.getString(consts.ARTICLES_AUTHOR);
               Integer beginPage = result.getInt(consts.ARTICLES_BEGINPAGE);
               Integer endPage = result.getInt(consts.ARTICLES_ENDPAGE);
               String theme = result.getString(consts.THEMES_NAME);
               data.add(new NumberArticle(name,author,beginPage,endPage,theme));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //выборка полной таблицы статей журналов (перегрузка предыдущего метода)
    public ObservableList<NumberArticle> returnMagazinesNumberArticleTableFromDb (String nameSubstring) {
        ObservableList<NumberArticle> data = null;
        //Integer lNameSubstring = nameSubstring.getLength();
        try {
            DataBaseHandler db = new DataBaseHandler();
            Connection con = db.getDbConnection();
            data = FXCollections.observableArrayList();
            ResultSet result = con.createStatement().executeQuery ("SELECT " + consts.ARTICLES_NAME + ", " +
                    consts.ARTICLES_AUTHOR + ", " + consts.ARTICLES_BEGINPAGE + ", "
                    + consts.ARTICLES_ENDPAGE + ", " +consts.THEMES_NAME + ", " + consts.NUMBERS_NUMBER
                    + ", " + consts.MAGAZINES_NAME +
                    " FROM " + consts.ARTICLES_TABLE +
                    " JOIN " + consts.THEMES_TABLE + " ON " +
                    consts.ARTICLES_TABLE + "." + consts.ARTICLES_THEMEID + " = " +
                    consts.THEMES_TABLE + "." + consts.THEMES_ID +
                    " JOIN " + consts.NUMBERS_TABLE + " ON " +
                    consts.ARTICLES_TABLE + "." + consts.ARTICLES_NUMBERID + " = " +
                    consts.NUMBERS_TABLE + "." + consts.NUMBERS_ID +
                    " JOIN " + consts.MAGAZINES_TABLE + " ON " +
                    consts.ARTICLES_TABLE + "." + consts.ARTICLES_NUMBERID + " = " +
                    consts.NUMBERS_TABLE + "." + consts.NUMBERS_ID + " AND " +
                    consts.NUMBERS_TABLE + "." + consts.NUMBERS_MAGAZINEID + " = " +
                    consts.MAGAZINES_TABLE + "." + consts.MAGAZINES_ID +
                    " WHERE " + consts.ARTICLES_NAME + " LIKE '%" + nameSubstring + "%'");

            while (result.next()) {
                String name = result.getString(consts.ARTICLES_NAME);
                String author = result.getString(consts.ARTICLES_AUTHOR);
                Integer beginPage = result.getInt(consts.ARTICLES_BEGINPAGE);
                Integer endPage = result.getInt(consts.ARTICLES_ENDPAGE);
                String theme = result.getString(consts.THEMES_NAME); //themesname
                String numberNum = result.getString(consts.NUMBERS_NUMBER);
                String magName = result.getString(consts.MAGAZINES_NAME);
                data.add(new NumberArticle(magName, name, author, beginPage, endPage, numberNum,theme));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //Выборка таблицы авторов
    public ObservableList<Authors> returnAuthorsFromDb (){
        ObservableList<Authors> data = null;
        //Integer lNameSubstring = nameSubstring.getLength();
        try {
            DataBaseHandler db = new DataBaseHandler();
            Connection con = db.getDbConnection();
            data = FXCollections.observableArrayList();
            ResultSet result = con.createStatement().executeQuery ("SELECT magazines_articles_authors, \n" +
                    "COUNT(magazines_articles_authors) as numOfArticles, \n" +
                    "COUNT(distinct magazines_numbers_idmagazines_numbers) as numOfMagazines,\n" +
                    "SUM(ABS(magazines_articles_end_page-magazines_articles_begin_page+1)) as razn,\n" +
                    "COUNT(distinct themes_idthemes) as numOfThemes\n" +
                    "FROM db_magazines_release.magazines_articles\n" +
                    "group by magazines_articles_authors\n" +
                    "order by magazines_articles_authors");

            while (result.next()) {
                String name = result.getString("magazines_articles_authors");
                Integer numArticles = result.getInt("numOfArticles");
                Integer numMagazines = result.getInt("numOfMagazines");
                Integer numThemes = result.getInt("numOfThemes");
                Integer pages = result.getInt("razn");
                data.add(new Authors(name,numArticles,numMagazines,numThemes,pages));
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     *
     * запросы для изменения значений строк БД
     * Изменения журналов
     */
    //выборка id темы и паблишеров по id журнала
    public MagazinesTable returnThemeAndPubIdFromDb (Integer magazineId){
        MagazinesTable data = new MagazinesTable();
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT idmagazines, magazine_name, " +
                    "publishers_idpublishers, themes_idthemes\n" +
                    "FROM db_magazines_release.magazines\n" +
                    "where magazines.idmagazines = " + magazineId);

            while (result.next()) {
                Integer id = result.getInt("idmagazines");
                String name = result.getString("magazine_name");
                Integer idPublisher = result.getInt("publishers_idpublishers");
                Integer idTheme = result.getInt("themes_idthemes");
                MagazinesTable magazine = new MagazinesTable(id, name, idPublisher, idTheme);
                data = magazine;
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //изменение журнала
    public void editMagazine (MagazinesTable magazine) {
        String update = "UPDATE " + consts.MAGAZINES_TABLE + " SET " +
                consts.MAGAZINES_NAME + " = ?"  + ", " +
                consts.MAGAZINES_PUBLISHER + " = ?" + ", " +
                consts.MAGAZINES_THEME + " = ?"  +
                " WHERE " + consts.MAGAZINES_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, magazine.getMagazinesName());
            prSt.setInt(2, magazine.getMagazinesPublisherId());
            prSt.setInt(3, magazine.getMagazinesThemesId());
            prSt.setInt(4, magazine.getMagazinesId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //удаление журнала
    public void deleteMagazine (MagazinesTable magazine) {
        String update = "DELETE FROM " + consts.MAGAZINES_TABLE +
                " WHERE " + consts.MAGAZINES_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, magazine.getMagazinesId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  изменение номеров журналов
     *
     */

    //выборка данных номера по номеру номера и id журнала
    public MagazineNumber returnMagazineNumberId (MagazineNumber number){
        MagazineNumber data = null;
        ResultSet result = null;
        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection con = dbHandler.getDbConnection();

            String sql = "SELECT "+ consts.NUMBERS_ID + ", "+ consts.NUMBERS_NUMBER + ", " +
                    consts.NUMBERS_YEAR + ", " + consts.NUMBERS_PAGES +", " +
                    consts.NUMBERS_RELDATE + ", " + consts.NUMBERS_MAGAZINEID +
                    " FROM " + consts.NUMBERS_TABLE +
                    " WHERE " + consts.NUMBERS_NUMBER + " = ? AND " + consts.NUMBERS_MAGAZINEID + " = ?";

            //вставка значений вместо ?
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, number.getMagazineNumberNumber());
            prSt.setInt(2, number.getMagazineId());
            result = prSt.executeQuery();

            //получение выборки журнала
            while (result.next()) {
                Integer id = result.getInt(consts.NUMBERS_ID);
                Integer numNumber = result.getInt(consts.NUMBERS_NUMBER);
                Integer magNumYear = result.getInt( consts.NUMBERS_YEAR);
                Integer numOfPages = result.getInt(consts.NUMBERS_PAGES);
                Date date = result.getDate(consts.NUMBERS_RELDATE);
                Integer magId = result.getInt(consts.NUMBERS_MAGAZINEID);
                data = new MagazineNumber(id,numNumber,magNumYear,numOfPages,date,magId);
            }

        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //изменение номера журнала
    public void editNumber (MagazineNumber number){
        String update = "UPDATE " + consts.NUMBERS_TABLE + " SET " +
                consts.NUMBERS_NUMBER + " = ?, " +
                consts.NUMBERS_YEAR + " =?, " +
                consts.NUMBERS_PAGES + " =?, " +
                consts.NUMBERS_RELDATE + " =?, " +
                consts.NUMBERS_MAGAZINEID + " =? " +
                " WHERE " + consts.NUMBERS_ID + " =? ";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, number.getMagazineNumberNumber());
            prSt.setInt(2, number.getMagazineNumberYear());
            prSt.setInt(3, number.getMagazineNumberPages());
            prSt.setDate(4, convertUtilToSql(number.getMagazineNumberDate()));
            prSt.setInt(5, number.getMagazineId());
            prSt.setInt(6, number.getMagazineNumberID());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //удаление номера журнала
    public void deleteNumber (MagazineNumber number){
        String update = "DELETE FROM "+ consts.NUMBERS_TABLE +
                " WHERE " + consts.NUMBERS_ID + " =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, number.getMagazineNumberID());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //конвертер даты
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }


}
