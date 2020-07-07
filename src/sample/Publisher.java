package sample;

public class Publisher {
    public Integer id;
    public String publisherName;
    public Integer sumOfMagazines;

    public Publisher(Integer id, String publisherName) {
        this.id = id;
        this.publisherName = publisherName;
    }

    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

    //конструктор для вывода в таблицу суммы журналов

    public Publisher(String publisherName, Integer sumOfMagazines) {
        this.publisherName = publisherName;
        this.sumOfMagazines = sumOfMagazines;
    }

    public Integer getSumOfMagazines() {
        return sumOfMagazines;
    }

    public void setSumOfMagazines(Integer sumOfMagazines) {
        this.sumOfMagazines = sumOfMagazines;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
