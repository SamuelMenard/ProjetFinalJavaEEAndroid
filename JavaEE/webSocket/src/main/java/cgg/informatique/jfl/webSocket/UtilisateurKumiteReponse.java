package cgg.informatique.jfl.webSocket;

public class UtilisateurKumiteReponse {

    private String username;
    private String fullname;
    private String avatar;

    public UtilisateurKumiteReponse(){

    }

    public UtilisateurKumiteReponse(String username, String fullname, String avatar) {
        this.username = username;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UtilisateurKumite{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
