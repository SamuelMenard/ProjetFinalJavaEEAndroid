package cgg.informatique.jfl.webSocket;

import java.util.List;

public class Historique {

    private Long date;
    private int points;
    private int credits;
    private String ceinture;
    private boolean reussi;

    private List<HistoriqueCombat> lstcombats;

    public Historique(){

    }

    public Historique(Long date, int points, int credits, String ceinture, boolean reussi, List<HistoriqueCombat> lstcombats) {
        this.date = date;
        this.points = points;
        this.credits = credits;
        this.ceinture = ceinture;
        this.reussi = reussi;
        this.lstcombats = lstcombats;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCeinture() {
        return ceinture;
    }

    public void setCeinture(String ceinture) {
        this.ceinture = ceinture;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public List<HistoriqueCombat> getLstcombats() {
        return lstcombats;
    }

    public void setLstcombats(List<HistoriqueCombat> lstcombats) {
        this.lstcombats = lstcombats;
    }
}
