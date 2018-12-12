package cgg.informatique.jfl.webSocket.entites;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name="AVATAR")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;

    @Lob
    @Column(name="AVATAR", length=32767)
    private String avatar;

    @OneToOne( mappedBy = "avatar" )
    private Compte compte = null;

    public Avatar(){

    }

    public Avatar(String nom) {
        this.id = -1;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
