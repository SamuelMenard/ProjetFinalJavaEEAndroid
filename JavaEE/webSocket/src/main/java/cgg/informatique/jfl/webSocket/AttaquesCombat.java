package cgg.informatique.jfl.webSocket;

public class AttaquesCombat {

    private String courriel;
    private String attaque;
    private String couleur;

    public AttaquesCombat(){

    }

    public AttaquesCombat(String courriel, String attaque, String couleur) {
        this.courriel = courriel;
        this.attaque = attaque;
        this.couleur = couleur;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getAttaque() {
        return attaque;
    }

    public void setAttaque(String attaque) {
        this.attaque = attaque;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return "AttaquesCombat{" +
                "courriel='" + courriel + '\'' +
                ", attaque=" + attaque + '\'' +
                ", couleur=" + couleur + '\'' +
                '}';
    }
}
