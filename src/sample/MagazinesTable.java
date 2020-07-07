package sample;

public class MagazinesTable {
    private int magazinesId;
    private String  magazinesName, magazinesPublisher, magazinesTheme;
    private int magazinesPublisherId, magazinesThemesId;
    //для сортировки по дате
    //private int numOfNumbers, numOfPages;

    //для вывода только названий журналов и запоминаний id
   /* public MagazinesTable(int magazinesId, String magazinesName) {
        this.magazinesId = magazinesId;
        this.magazinesName = magazinesName;
    }*/

    //для вывода журналов с сортировкой по дате
    //для задания паблишера и темы по индексами
    public MagazinesTable(String magazinesName, int magazinesPublisherId, int magazinesThemesId) {
        this.magazinesName = magazinesName;
        this.magazinesPublisherId = magazinesPublisherId;
        this.magazinesThemesId = magazinesThemesId;
    }

    //для изменения данных журнала в бд


    public MagazinesTable(int magazinesId, String magazinesName, int magazinesPublisherId, int magazinesThemesId) {
        this.magazinesId = magazinesId;
        this.magazinesName = magazinesName;
        this.magazinesPublisherId = magazinesPublisherId;
        this.magazinesThemesId = magazinesThemesId;
    }

    public int getMagazinesPublisherId() {
        return magazinesPublisherId;
    }

    public void setMagazinesPublisherId(int magazinesPublisherId) {
        this.magazinesPublisherId = magazinesPublisherId;
    }

    public int getMagazinesThemesId() {
        return magazinesThemesId;
    }

    public void setMagazinesThemesId(int magazinesThemesId) {
        this.magazinesThemesId = magazinesThemesId;
    }

    //для задания паблишера и темы строками
    public MagazinesTable(int magazinesId, String magazinesName, String magazinesPublisher, String magazinesTheme) {
        this.magazinesId = magazinesId;
        this.magazinesName = magazinesName;
        this.magazinesPublisher = magazinesPublisher;
        this.magazinesTheme = magazinesTheme;
    }

    public MagazinesTable(String magazinesName, String magazinesPublisher, String magazinesTheme) {
        this.magazinesName = magazinesName;
        this.magazinesPublisher = magazinesPublisher;
        this.magazinesTheme = magazinesTheme;
    }

    public MagazinesTable() {
    }

    public int getMagazinesId() {
        return magazinesId;
    }

    public void setMagazinesId(int magazinesId) {
        this.magazinesId = magazinesId;
    }

    public String getMagazinesName() {
        return magazinesName;
    }

    public void setMagazinesName(String magazinesName) {
        this.magazinesName = magazinesName;
    }

    public String getMagazinesPublisher() {
        return magazinesPublisher;
    }

    public void setMagazinesPublisher(String magazinesPublisher) {
        this.magazinesPublisher = magazinesPublisher;
    }

    public String getMagazinesTheme() {
        return magazinesTheme;
    }

    public void setMagazinesTheme(String magazinesTheme) {
        this.magazinesTheme = magazinesTheme;
    }
}
