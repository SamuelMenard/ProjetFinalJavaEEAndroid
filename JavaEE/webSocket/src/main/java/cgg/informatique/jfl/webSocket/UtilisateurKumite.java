package cgg.informatique.jfl.webSocket;

public class UtilisateurKumite {

    private String courriel;
    private String jid;
    private String position;
    private String ajoutouretire;

    public UtilisateurKumite(){

    }

    public UtilisateurKumite(String courriel, String jid, String position, String ajoutouretire) {
        this.courriel = courriel;
        this.jid = jid;
        this.position = position;
        this.ajoutouretire = ajoutouretire;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAjoutouretire() {
        return ajoutouretire;
    }

    public void setAjoutouretire(String ajoutouretire) {
        this.ajoutouretire = ajoutouretire;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    @Override
    public String toString() {
        return "UtilisateurKumite{" +
                "courriel='" + courriel + '\'' +
                ", jid='" + jid + '\'' +
                ", position='" + position + '\'' +
                ", ajoutouretire='" + ajoutouretire + '\'' +
                '}';
    }
}
