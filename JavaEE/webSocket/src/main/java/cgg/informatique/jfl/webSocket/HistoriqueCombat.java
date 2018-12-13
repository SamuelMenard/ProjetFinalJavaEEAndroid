package cgg.informatique.jfl.webSocket;

public class HistoriqueCombat {

    private Long date;
    private String arbitre;
    private int credits;
    private String rouge;
    private String ceinturerouge;
    private int pointsrouge;
    private String blanc;
    private String ceintureblanc;
    private int pointsblanc;

    public HistoriqueCombat(){

    }

    public HistoriqueCombat(Long date, String arbitre, int credits, String rouge, String ceinturerouge, int pointsrouge, String blanc, String ceintureblanc, int pointsblanc) {
        this.date = date;
        this.arbitre = arbitre;
        this.credits = credits;
        this.rouge = rouge;
        this.ceinturerouge = ceinturerouge;
        this.pointsrouge = pointsrouge;
        this.blanc = blanc;
        this.ceintureblanc = ceintureblanc;
        this.pointsblanc = pointsblanc;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getArbitre() {
        return arbitre;
    }

    public void setArbitre(String arbitre) {
        this.arbitre = arbitre;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getRouge() {
        return rouge;
    }

    public void setRouge(String rouge) {
        this.rouge = rouge;
    }

    public String getCeinturerouge() {
        return ceinturerouge;
    }

    public void setCeinturerouge(String ceinturerouge) {
        this.ceinturerouge = ceinturerouge;
    }

    public int getPointsrouge() {
        return pointsrouge;
    }

    public void setPointsrouge(int pointsrouge) {
        this.pointsrouge = pointsrouge;
    }

    public String getBlanc() {
        return blanc;
    }

    public void setBlanc(String blanc) {
        this.blanc = blanc;
    }

    public String getCeintureblanc() {
        return ceintureblanc;
    }

    public void setCeintureblanc(String ceintureblanc) {
        this.ceintureblanc = ceintureblanc;
    }

    public int getPointsblanc() {
        return pointsblanc;
    }

    public void setPointsblanc(int pointsblanc) {
        this.pointsblanc = pointsblanc;
    }
}
