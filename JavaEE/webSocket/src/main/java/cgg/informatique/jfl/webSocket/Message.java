package cgg.informatique.jfl.webSocket;


import java.util.Date;

public class Message{
    private String de;
    private String texte;
    private String   date;
    private String avatar  = new String();
    private String type;

    private String id_avatar;
    private String sessionRest;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Message() {
    }

    public Message(String de, String texte, String date, String avatar, String type, String id_avatar, String sessionRest) {
        this.de = de;
        this.texte = texte;
        this.date = date;
        this.avatar = avatar;
        this.type = type;
        this.id_avatar = id_avatar;
        this.sessionRest = sessionRest;
    }

    public String getCreation() {
        return date;
    }

    public void setCreation(String creation) {
        this.date = creation;
    }

    public Message(String de, String texte) {
        this.de = de;
        this.texte = texte;
    }

    public Message(  String texte) {
        this.de = "anonyme";
        this.texte = texte;
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

    public String getSessionRest() {

        return sessionRest;
    }

    public void setSessionRest(String sessionRest) {
        this.sessionRest = sessionRest;
    }

    public String getId_avatar() {
        return id_avatar;
    }

    public void setId_avatar(String id_avatar) {
        this.id_avatar = id_avatar;
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

    public void setTexte(String text) {
        this.texte = text;
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
}

