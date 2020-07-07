package sample;


import java.time.LocalDate;
import java.util.Date;

public class MagazineNumber {
    private Integer magazineNumberID, magazineNumberNumber, magazineNumberYear, magazineNumberPages;
    private Date magazineNumberDate;
    private Integer magazineId;


    //конструктор для получения данных для изменения номера из БД
    public MagazineNumber(Integer magazineNumberID, Integer magazineNumberNumber, Integer magazineNumberYear, Integer magazineNumberPages, Date magazineNumberDate, Integer magazineId) {
        this.magazineNumberID = magazineNumberID;
        this.magazineNumberNumber = magazineNumberNumber;
        this.magazineNumberYear = magazineNumberYear;
        this.magazineNumberPages = magazineNumberPages;
        this.magazineNumberDate = magazineNumberDate;
        this.magazineId = magazineId;
    }

    public MagazineNumber(Integer magazineNumberNumber, Integer magazineNumberYear, Integer magazineNumberPages, Date magazineNumberDate, Integer magazineId) {
        this.magazineNumberNumber = magazineNumberNumber;
        this.magazineNumberYear = magazineNumberYear;
        this.magazineNumberPages = magazineNumberPages;
        this.magazineNumberDate = magazineNumberDate;
        this.magazineId = magazineId;
    }

    //конструктор без ID для вывода таблицы
    public MagazineNumber(Integer magazineNumberNumber, Integer magazineNumberYear, Integer magazineNumberPages, Date magazineNumberDate) {
        this.magazineNumberNumber = magazineNumberNumber;
        this.magazineNumberYear = magazineNumberYear;
        this.magazineNumberPages = magazineNumberPages;
        this.magazineNumberDate = magazineNumberDate;
    }

    public MagazineNumber(Integer magazineNumberID, Integer magazineNumberNumber, Integer magazineNumberYear, Integer magazineNumberPages, Date magazineNumberDate) {
        this.magazineNumberID = magazineNumberID;
        this.magazineNumberNumber = magazineNumberNumber;
        this.magazineNumberYear = magazineNumberYear;
        this.magazineNumberPages = magazineNumberPages;
        this.magazineNumberDate = magazineNumberDate;
    }

    public Integer getMagazineNumberID() {
        return magazineNumberID;
    }

    public void setMagazineNumberID(Integer magazineNumberID) {
        this.magazineNumberID = magazineNumberID;
    }

    public Integer getMagazineNumberNumber() {
        return magazineNumberNumber;
    }

    public void setMagazineNumberNumber(Integer magazineNumberNumber) {
        this.magazineNumberNumber = magazineNumberNumber;
    }

    public Integer getMagazineNumberYear() {
        return magazineNumberYear;
    }

    public void setMagazineNumberYear(Integer magazineNumberYear) {
        this.magazineNumberYear = magazineNumberYear;
    }

    public Integer getMagazineNumberPages() {
        return magazineNumberPages;
    }

    public void setMagazineNumberPages(Integer magazineNumberPages) {
        this.magazineNumberPages = magazineNumberPages;
    }

    public Date getMagazineNumberDate() {
        return magazineNumberDate;
    }

    public void setMagazineNumberDate(Date magazineNumberDate) {
        this.magazineNumberDate = magazineNumberDate;
    }

    public Integer getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Integer magazineId) {
        this.magazineId = magazineId;
    }
}
