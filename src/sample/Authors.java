package sample;

public class Authors {
    String authorsName;
    Integer authorsNumOfArticles, authorsNumOfMagazines, authorsNumOfThemes, authorsNumOfPages;

    public Authors(String authorsName, Integer authorsNumOfArticles,
                   Integer authorsNumOfMagazines, Integer authorsNumOfThemes,
                   Integer authorsNumOfPages) {
        this.authorsName = authorsName;
        this.authorsNumOfArticles = authorsNumOfArticles;
        this.authorsNumOfMagazines = authorsNumOfMagazines;
        this.authorsNumOfThemes = authorsNumOfThemes;
        this.authorsNumOfPages = authorsNumOfPages;
    }

    public String getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(String authorsName) {
        this.authorsName = authorsName;
    }

    public Integer getAuthorsNumOfArticles() {
        return authorsNumOfArticles;
    }

    public void setAuthorsNumOfArticles(Integer authorsNumOfArticles) {
        this.authorsNumOfArticles = authorsNumOfArticles;
    }

    public Integer getAuthorsNumOfMagazines() {
        return authorsNumOfMagazines;
    }

    public void setAuthorsNumOfMagazines(Integer authorsNumOfMagazines) {
        this.authorsNumOfMagazines = authorsNumOfMagazines;
    }

    public Integer getAuthorsNumOfThemes() {
        return authorsNumOfThemes;
    }

    public void setAuthorsNumOfThemes(Integer authorsNumOfThemes) {
        this.authorsNumOfThemes = authorsNumOfThemes;
    }

    public Integer getAuthorsNumOfPages() {
        return authorsNumOfPages;
    }

    public void setAuthorsNumOfPages(Integer authorsNumOfPages) {
        this.authorsNumOfPages = authorsNumOfPages;
    }
}
