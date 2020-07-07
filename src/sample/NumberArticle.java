package sample;

public class NumberArticle {
    private Integer numberArticleId;
    private String numberArticleMagazineName;
    private String numberArticleName, numberArticleAuthor;
    private Integer numberArticleBeginPage, numberArticleEndPage, numberArticleIdNumber, numberArticleIdTheme;
    private String numberArticleNumber, numberArticleTheme;


    public NumberArticle(String numberArticleName, String numberArticleAuthor,
                         Integer numberArticleBeginPage, Integer numberArticleEndPage,
                         String numberArticleTheme) {
        this.numberArticleName = numberArticleName;
        this.numberArticleAuthor = numberArticleAuthor;
        this.numberArticleBeginPage = numberArticleBeginPage;
        this.numberArticleEndPage = numberArticleEndPage;
        this.numberArticleTheme = numberArticleTheme;
    }

    //конструктор для получения ID темы
    public NumberArticle(String numberArticleName, String numberArticleAuthor,
                         Integer numberArticleBeginPage, Integer numberArticleEndPage,
                         Integer numberArticleIdNumber, Integer numberArticleIdTheme) {
        this.numberArticleName = numberArticleName;
        this.numberArticleAuthor = numberArticleAuthor;
        this.numberArticleBeginPage = numberArticleBeginPage;
        this.numberArticleEndPage = numberArticleEndPage;
        this.numberArticleIdNumber = numberArticleIdNumber;
        this.numberArticleIdTheme = numberArticleIdTheme;
    }

    //конструктор для полного вывода с журналами и номерами
    public NumberArticle(String numberArticleMagazineName, String numberArticleName,
                         String numberArticleAuthor, Integer numberArticleBeginPage,
                         Integer numberArticleEndPage, String numberArticleNumber,
                         String numberArticleTheme) {
        this.numberArticleMagazineName = numberArticleMagazineName;
        this.numberArticleName = numberArticleName;
        this.numberArticleAuthor = numberArticleAuthor;
        this.numberArticleBeginPage = numberArticleBeginPage;
        this.numberArticleEndPage = numberArticleEndPage;
        this.numberArticleNumber = numberArticleNumber;
        this.numberArticleTheme = numberArticleTheme;
    }

    public String getNumberArticleMagazineName() {
        return numberArticleMagazineName;
    }

    public void setNumberArticleMagazineName(String numberArticleMagazineName) {
        this.numberArticleMagazineName = numberArticleMagazineName;
    }

    public Integer getNumberArticleId() {
        return numberArticleId;
    }

    public void setNumberArticleId(Integer numberArticleId) {
        this.numberArticleId = numberArticleId;
    }

    public String getNumberArticleName() {
        return numberArticleName;
    }

    public void setNumberArticleName(String numberArticleName) {
        this.numberArticleName = numberArticleName;
    }

    public String getNumberArticleAuthor() {
        return numberArticleAuthor;
    }

    public void setNumberArticleAuthor(String numberArticleAuthor) {
        this.numberArticleAuthor = numberArticleAuthor;
    }

    public Integer getNumberArticleBeginPage() {
        return numberArticleBeginPage;
    }

    public void setNumberArticleBeginPage(Integer numberArticleBeginPage) {
        this.numberArticleBeginPage = numberArticleBeginPage;
    }

    public Integer getNumberArticleEndPage() {
        return numberArticleEndPage;
    }

    public void setNumberArticleEndPage(Integer numberArticleEndPage) {
        this.numberArticleEndPage = numberArticleEndPage;
    }

    public Integer getNumberArticleIdNumber() {
        return numberArticleIdNumber;
    }

    public void setNumberArticleIdNumber(Integer numberArticleIdNumber) {
        this.numberArticleIdNumber = numberArticleIdNumber;
    }

    public Integer getNumberArticleIdTheme() {
        return numberArticleIdTheme;
    }

    public void setNumberArticleIdTheme(Integer numberArticleIdTheme) {
        this.numberArticleIdTheme = numberArticleIdTheme;
    }

    public String getNumberArticleNumber() {
        return numberArticleNumber;
    }

    public void setNumberArticleNumber(String numberArticleNumber) {
        this.numberArticleNumber = numberArticleNumber;
    }

    public String getNumberArticleTheme() {
        return numberArticleTheme;
    }

    public void setNumberArticleTheme(String numberArticleTheme) {
        this.numberArticleTheme = numberArticleTheme;
    }
}
