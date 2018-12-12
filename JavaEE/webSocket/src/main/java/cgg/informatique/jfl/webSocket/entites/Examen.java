package cgg.informatique.jfl.webSocket.entites;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name="EXAMENS")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    Boolean aReussi;
    Long    date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ceinture_id" )
    private Groupe   ceinture = new Groupe();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evaluateur_id" )
    private Compte   evaluateur = new Compte();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evalue_id" )
    private Compte   evalue = new Compte();

    public Examen(){

    }

    public Examen(Long date, Boolean aReussi) {
        this.id = -1;
        this.date = date;
        this.aReussi = aReussi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Boolean getaReussi() {
        return aReussi;
    }

    public void setaReussi(Boolean aReussi) {
        this.aReussi = aReussi;
    }

    public Groupe getCeinture() {
        return ceinture;
    }

    public void setCeinture(Groupe ceinture) {
        this.ceinture = ceinture;
    }

    public Compte getEvaluateur() {
        return evaluateur;
    }

    public void setEvaluateur(Compte evaluateur) {
        this.evaluateur = evaluateur;
    }

    public Compte getEvalue() {
        return evalue;
    }

    public void setEvalue(Compte evalue) {
        this.evalue = evalue;
    }
}
