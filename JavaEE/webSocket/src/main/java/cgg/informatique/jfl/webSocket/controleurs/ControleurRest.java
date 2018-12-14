package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.Historique;
import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
import cgg.informatique.jfl.webSocket.dao.*;
import cgg.informatique.jfl.webSocket.entites.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ControleurRest {

    @Autowired
    AvatarDao avatarDao;

    @Autowired
    CombatDao combatDao;

    @Autowired
    CompteDao compteDao;

    @Autowired
    ExamenDao examenDao;

    @Autowired
    GroupeDao groupeDao;

    @Autowired
    RoleDao roleDao;

    @GetMapping
            (value = "/historique/{username}")
    public List<Historique> retournerHistorique(@PathVariable String username){

        Optional<Compte> opt = compteDao.findById(username);
        List<Historique> lstInfos = new ArrayList<>();

        if (opt.isPresent()){
            Compte c = opt.get();
            // générer l'historique
            /*List<Combat> lstMesCombats = combatDao.getTousMesCombats(c);

            for (Examen exam : c.getEvalues()){

            }*/
        }

        return lstInfos;
    }

    @GetMapping
            (value = "/examens/reussi/{evaluateur}/{evalue}")
    public HttpStatus examenReussi(@PathVariable("evaluateur") String evaluateur, @PathVariable("evalue") String evalue){
        System.out.print("dans examen reussi");
        List<String> lst = new ArrayList<>();

        // rajouter les autres infos
        Optional<Compte> optEvaluateur = compteDao.findById(evaluateur);
        Optional<Compte> optEvalue = compteDao.findById(evalue);

        if (optEvaluateur.isPresent() && optEvalue.isPresent()){
            int noCeintureCourrante = optEvalue.get().getGroupe().getId();

            if (noCeintureCourrante < 7 && optEvalue.get().getPoints() >= 100 && optEvalue.get().getCredits() >= 10){
                Date aujourdhui = new Date();
                Long date = aujourdhui.getTime();
                Examen exam = new Examen(date, true);

                exam.setEvaluateur(optEvaluateur.get());
                exam.setEvalue(optEvalue.get());
                exam.setCeinture(optEvalue.get().getGroupe());
                examenDao.saveAndFlush(exam);

                // modifier ceinture
                Optional<Groupe> nouvelleCeinture = groupeDao.findById(noCeintureCourrante + 1);
                optEvalue.get().setGroupe(nouvelleCeinture.get());
                compteDao.saveAndFlush(optEvalue.get());

                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping
            (value = "/examens/echoue/{evaluateur}/{evalue}")
    public HttpStatus examenEchoue(@PathVariable("evaluateur") String evaluateur, @PathVariable("evalue") String evalue){
        System.out.print("dans examen echoue");
        List<String> lst = new ArrayList<>();

        // rajouter les autres infos
        Optional<Compte> optEvaluateur = compteDao.findById(evaluateur);
        Optional<Compte> optEvalue = compteDao.findById(evalue);

        if (optEvaluateur.isPresent() && optEvalue.isPresent()){
            int noCeintureCourrante = optEvalue.get().getGroupe().getId();
            if (noCeintureCourrante < 7 && optEvalue.get().getPoints() >= 100 && optEvalue.get().getCredits() >= 10){
                Date aujourdhui = new Date();
                Long date = aujourdhui.getTime();
                Examen exam = new Examen(date, false);

                exam.setEvaluateur(optEvaluateur.get());
                exam.setEvalue(optEvalue.get());
                exam.setCeinture(optEvalue.get().getGroupe());
                examenDao.saveAndFlush(exam);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping
            (value = "/compte/all/username")
    public List<String> getAllComptesUsername(){
        List<String> allUsername = new ArrayList<>();
        for (Compte c : compteDao.findAll()){
            allUsername.add(c.getUsername());
        }
        return allUsername;
    }

    @GetMapping
            (value = "/compte/infos/base/{username}")
    public List<String> searchByUsername(@PathVariable String username){
        Optional<Compte> opt = compteDao.findById(username);
        List<String> lstInfos = new ArrayList<>();

        if (opt.isPresent()){
            Compte c = opt.get();
            int points = c.getPoints();
            int credits = c.getCredits();


            lstInfos.add(c.getUsername());
            lstInfos.add(c.getPassword());
            lstInfos.add(c.getFullname());
            lstInfos.add(c.getAvatar().getAvatar());
            lstInfos.add(c.getGroupe().getGroupe());
            lstInfos.add(c.getRole().getRole());
            lstInfos.add(String.valueOf(points));
            lstInfos.add(String.valueOf(credits));
        }

        return lstInfos;
    }

    @GetMapping
            (value = "/combat/nouveau/{gagnant}/{perdant}/{arbitre}/{blanc}/{rouge}")
    public HttpStatus nouveauCombat(@PathVariable("gagnant") String gagnant, @PathVariable("perdant") String perdant,
                                    @PathVariable("arbitre") String arbitre, @PathVariable("blanc") String blanc, @PathVariable("rouge") String rouge){

        List<String> lst = new ArrayList<>();

        // rajouter les autres infos
        Optional<Compte> optGagnant = compteDao.findById(gagnant);
        Optional<Compte> optPerdant = compteDao.findById(perdant);
        Optional<Compte> optArbitre = compteDao.findById(arbitre);

        Optional<Compte> optBlanc = compteDao.findById(blanc);
        Optional<Compte> optRouge = compteDao.findById(rouge);

        if (optGagnant.isPresent() && optPerdant.isPresent() && optArbitre.isPresent() && optBlanc.isPresent() && optRouge.isPresent()){
            Date aujourdhui = new Date();
            Long date = aujourdhui.getTime();

            // calculer points blanc
            int pointsBlanc = 0;
            if (optBlanc.get().getUsername().compareToIgnoreCase(optGagnant.get().getUsername()) == 0){
                pointsBlanc = optBlanc.get().calculerPointsCombat(optRouge.get());
            }

            // calculer points rouge
            int pointsRouge = 0;
            if (optRouge.get().getUsername().compareToIgnoreCase(optGagnant.get().getUsername()) == 0){
                pointsRouge = optRouge.get().calculerPointsCombat(optBlanc.get());
            }

            // créer le combat
            Combat cbt = new Combat(1, date, pointsBlanc, pointsRouge);

            cbt.setArbitre(optArbitre.get());
            cbt.setBlanc(optBlanc.get());
            cbt.setRouge(optRouge.get());
            cbt.setCeintureBlanc(optBlanc.get().getGroupe());
            cbt.setCeintureRouge(optRouge.get().getGroupe());
            combatDao.saveAndFlush(cbt);


            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping
            (value = "/combat/egalite/{arbitre}/{blanc}/{rouge}")
    public HttpStatus egaliteCombat(@PathVariable("arbitre") String arbitre, @PathVariable("blanc") String blanc, @PathVariable("rouge") String rouge){

        List<String> lst = new ArrayList<>();

        // rajouter les autres infos
        Optional<Compte> optArbitre = compteDao.findById(arbitre);

        Optional<Compte> optBlanc = compteDao.findById(blanc);
        Optional<Compte> optRouge = compteDao.findById(rouge);

        if (optArbitre.isPresent() && optBlanc.isPresent() && optRouge.isPresent()){
            Date aujourdhui = new Date();
            Long date = aujourdhui.getTime();

            // calculer points blanc
            int pointsBlanc = 10;

            // calculer points rouge
            int pointsRouge = 10;

            // créer le combat
            Combat cbt = new Combat(1, date, pointsBlanc, pointsRouge);

            cbt.setArbitre(optArbitre.get());
            cbt.setBlanc(optBlanc.get());
            cbt.setRouge(optRouge.get());
            cbt.setCeintureBlanc(optBlanc.get().getGroupe());
            cbt.setCeintureRouge(optRouge.get().getGroupe());
            combatDao.saveAndFlush(cbt);


            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping
            (value = "/combat/fautearbitre/{arbitre}/{blanc}/{rouge}")
    public HttpStatus fauteArbitre(@PathVariable("arbitre") String arbitre, @PathVariable("blanc") String blanc, @PathVariable("rouge") String rouge){

        List<String> lst = new ArrayList<>();

        // rajouter les autres infos
        Optional<Compte> optArbitre = compteDao.findById(arbitre);

        Optional<Compte> optBlanc = compteDao.findById(blanc);
        Optional<Compte> optRouge = compteDao.findById(rouge);

        if (optArbitre.isPresent() && optBlanc.isPresent() && optRouge.isPresent()){
            Date aujourdhui = new Date();
            Long date = aujourdhui.getTime();

            // calculer points blanc
            int pointsBlanc = 10;

            // calculer points rouge
            int pointsRouge = 10;

            // créer le combat
            Combat cbt = new Combat(0, date, pointsBlanc, pointsRouge);

            cbt.setArbitre(optArbitre.get());
            cbt.setBlanc(optBlanc.get());
            cbt.setRouge(optRouge.get());
            cbt.setCeintureBlanc(optBlanc.get().getGroupe());
            cbt.setCeintureRouge(optRouge.get().getGroupe());
            combatDao.saveAndFlush(cbt);


            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping
            (value = "/passage/ancien/{utilisateur}")
    public HttpStatus passageAAncien(@PathVariable("utilisateur") String utilisateur){

        // rajouter les autres infos
        Optional<Compte> optUtilisateur = compteDao.findById(utilisateur);

        if (optUtilisateur.isPresent()){
            int noRole = optUtilisateur.get().getRole().getId();
            if (noRole == 1){
                if (optUtilisateur.get().getNbCombatsArbitres() >= 30 && optUtilisateur.get().getCredits() >= 10){
                    optUtilisateur.get().setRole(roleDao.findById(noRole + 1).get());
                    compteDao.saveAndFlush(optUtilisateur.get());
                    return HttpStatus.OK;
                }
            }
            else {
                return HttpStatus.NOT_ACCEPTABLE;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }





}
