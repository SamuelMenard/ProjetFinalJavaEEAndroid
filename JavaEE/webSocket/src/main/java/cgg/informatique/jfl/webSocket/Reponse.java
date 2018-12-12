package cgg.informatique.jfl.webSocket;

import java.util.Date;

public class Reponse {
    private Long id;
    private String de;
    private String texte;
    private String date;

    private String avatar = new String();
    private String type;

    public String getAvatar() {
        return avatar;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reponse() {
    }

    public Reponse(String courriel) {
        this.de = courriel;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Reponse(Long id, String de, String texte, String date, String avatar, String type) {
        this.id = id;
        this.de = de;
        this.texte = texte;

        this.date = date;
        this.avatar = avatar;
        this.type = type;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "de='" + de + '\'' +
                ", texte='" + texte + '\'' +
                ", date=" + date +
                ", avatar=" + avatar + ", type=" + type +
                '}';
    }
}


