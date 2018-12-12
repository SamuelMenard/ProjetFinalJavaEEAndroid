package cgg.informatique.jfl.webSocket.entites;

import javax.persistence.Id;

import cgg.informatique.jfl.webSocket.dao.CombatDao;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="COMPTES")
public class Compte implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    private String  username;
    private Long    anciendepuis;
    //Valeurs de 1 à 10. Utilisé lors des examens.
    private int     chouchou;
    //Valeurs de 1 à 10. Utilisé lors des combats.
    private int     entrainement;
    private String  fullname;
    private String  password;

    //Valeurs de 1 à 10. Utilisé lors des combats.
    private int     talent;

    //Identification
    @OneToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name="avatar_id" )
    private Avatar avatar = new Avatar();

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name="groupe_id" )
    private Groupe groupe = new Groupe();

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name="role_id" )
    private Role role = new Role();



    //Combats
    @OneToMany(  mappedBy = "rouge" )
    private Set<Combat> rouges  = new HashSet<>();

    @OneToMany(  mappedBy = "blanc" )
    private Set<Combat> blancs  = new HashSet<>();

    @OneToMany(  mappedBy = "arbitre" )
    private Set<Combat> arbitres  = new HashSet<>();

    //Examens
    @OneToMany(  mappedBy = "evaluateur" )
    private Set<Examen> evaluateurs  = new HashSet<>();

    @OneToMany(  mappedBy = "evalue" )
    private Set<Examen> evalues  = new HashSet<>();

    public Compte(){

    }

    public Compte(String username, String fullname, String password, int talent, int entrainement, int chouchou, Long anciendepuis) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.talent = talent;
        this.entrainement = entrainement;
        this.chouchou = chouchou;
        this.anciendepuis = anciendepuis;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTalent() {
        return talent;
    }

    public void setTalent(int talent) {
        this.talent = talent;
    }

    public int getEntrainement() {
        return entrainement;
    }

    public void setEntrainement(int entrainement) {
        this.entrainement = entrainement;
    }

    public int getChouchou() {
        return chouchou;
    }

    public void setChouchou(int chouchou) {
        this.chouchou = chouchou;
    }

    public Long getAnciendepuis() {
        return anciendepuis;
    }

    public void setAnciendepuis(Long anciendepuis) {
        this.anciendepuis = anciendepuis;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Set<Combat> getRouges() {
        return rouges;
    }

    public void setRouges(Set<Combat> rouges) {
        this.rouges = rouges;
    }

    public Set<Combat> getBlancs() {
        return blancs;
    }

    public void setBlancs(Set<Combat> blancs) {
        this.blancs = blancs;
    }

    public Set<Combat> getArbitres() {
        return arbitres;
    }

    public void setArbitres(Set<Combat> arbitres) {
        this.arbitres = arbitres;
    }

    public Set<Examen> getEvaluateurs() {
        return evaluateurs;
    }

    public void setEvaluateurs(Set<Examen> evaluateurs) {
        this.evaluateurs = evaluateurs;
    }

    public Set<Examen> getEvalues() {
        return evalues;
    }

    public void setEvalues(Set<Examen> evalues) {
        this.evalues = evalues;
    }

    public int calculerPointsCombat(Compte perdant){
        // calculer différence ceintures
        int points = 0;
        int difference = perdant.getGroupe().getId() - this.groupe.getId();

        switch (difference){
            case 0: points = 10; break;
            case 1: points = 12; break;
            case 2: points = 15; break;
            case 3: points = 20; break;
            case 4: points = 25; break;
            case 5: points = 30; break;
            case 6: points = 50; break;
            case -1: points = 9; break;
            case -2: points = 7; break;
            case -3: points = 5; break;
            case -4: points = 3; break;
            case -5: points = 2; break;
            case -6: points = 1; break;
        }
        return points;
    }

    public int getPoints(){
        /*
                CALCULER LES POINTS
             */

        long dateDerniereExamenReussi = Long.MIN_VALUE;
        int points = 0;

        for (Examen e : this.getEvalues()){
            if (e.getaReussi()){
                if (e.getDate() > dateDerniereExamenReussi){
                    dateDerniereExamenReussi = e.getDate();
                }
            }
        }

        // en tant que blanc
        for (Combat cb : this.getBlancs()){
            if (dateDerniereExamenReussi != Long.MIN_VALUE){
                if (cb.getDate() > dateDerniereExamenReussi){
                    points += cb.getPointsBlanc();
                }
            }
            else{
                points += cb.getPointsBlanc();
            }
        }

        // en tant que rouge
        for (Combat cb : this.getRouges()){
            if (dateDerniereExamenReussi != Long.MIN_VALUE){
                if (cb.getDate() > dateDerniereExamenReussi){
                    points += cb.getPointsRouge();
                }
            }
            else{
                points += cb.getPointsRouge();
            }
        }
        return points;
    }

    public int getCredits(){
        /*
                CALCULER LES CRÉDITS
         */

        int credits = 10;
        if(this.getRole().getRole().equalsIgnoreCase("BLANC")){
            credits -= 10;
        }
        for (Combat cb2 : this.arbitres){
            credits += cb2.getCreditsArbitre();
        }
        for (Examen e : this.getEvalues()){
            if (e.getaReussi()){
                credits -= 10;
            }
            else {
                credits -= 5;
            }
        }
        return credits;
    }

    public int getNbExamensEvalue(){
        return this.evalues.size();
    }

    public boolean resultatDernierExamen(){
        boolean dernierResultat = true;
        Long plusGrandeDate = Long.MIN_VALUE;

        for (Examen exam : this.evalues){
            if (exam.getDate() > plusGrandeDate){
                dernierResultat = exam.getaReussi();
                plusGrandeDate = exam.getDate();
            }
        }
        return dernierResultat;
    }



    public int getNbCombatsArbitres(){
        return this.arbitres.size();
    }
}
