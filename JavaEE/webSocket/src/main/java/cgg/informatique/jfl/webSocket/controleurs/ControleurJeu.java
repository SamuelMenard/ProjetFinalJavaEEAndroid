package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.UtilisateurKumite;
import cgg.informatique.jfl.webSocket.UtilisateurKumiteReponse;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entites.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ControleurJeu {

    @Autowired
    private CompteDao compteDao;


    List<UtilisateurKumiteReponse> lstSpectateurs = new ArrayList<>();
    List<UtilisateurKumiteReponse> lstAttentes = new ArrayList<>();
    //List<UtilisateurKumiteReponse> lstArbitres = new ArrayList<>();


    @CrossOrigin()
    @MessageMapping("/message/combat/position")
    @SendTo("/sujet/combat/position")
    public List<List<UtilisateurKumiteReponse>> reponseListes(UtilisateurKumite user) throws Exception {

        /*lstSpectateurs.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));
        lstSpectateurs.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));
        lstSpectateurs.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));

        lstAttentes.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));
        lstAttentes.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));
        lstAttentes.add(new UtilisateurKumiteReponse("courriel@courriel", "fullname", "data:image/jpeg;base64,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"));
*/
        // utilisateur qui envoie la requête
        UtilisateurKumiteReponse utilCourant = null;

        List<List<UtilisateurKumiteReponse>> lstRetour = new ArrayList<>();
        boolean booAuthorized = false;

        Map<String, String> listeJID = ReponseControleur.listeDesConnexions;

        String userConnexion = ReponseControleur.listeDesConnexions.get(user.getCourriel());

        if (userConnexion != null){
            if (userConnexion.compareToIgnoreCase(user.getJid()) == 0){
                booAuthorized = true;
                Compte cpt = compteDao.findById(user.getCourriel()).get();
                utilCourant = new UtilisateurKumiteReponse(cpt.getUsername(), cpt.getFullname(), cpt.getAvatar().getAvatar());
            }
        }

        if (booAuthorized){
            if (user.getPosition().compareToIgnoreCase("spectateur") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){

                    lstSpectateurs.add(utilCourant);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstSpectateurs.size(); i++){
                        if (lstSpectateurs.get(i).getUsername().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstSpectateurs.remove(i);
                        }
                    }
                }
            }
            else if (user.getPosition().compareToIgnoreCase("attente") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstAttentes.add(utilCourant);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstAttentes.size(); i++){
                        if (lstAttentes.get(i).getUsername().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstAttentes.remove(i);
                        }
                    }
                }
            }
            /*else if (user.getPosition().compareToIgnoreCase("arbitre") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstArbitres.add(utilCourant);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstArbitres.size(); i++){
                        if (lstArbitres.get(i).getUsername().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstArbitres.remove(i);
                        }
                    }
                }
            }*/

            lstRetour.add(lstSpectateurs);
            lstRetour.add(lstAttentes);
        }
        else if (user.getJid().compareToIgnoreCase("-1") == 0){
            // juste un visionneur du combat sur web browser
            System.out.println("User connected: web browser");
            lstRetour.add(lstSpectateurs);
            lstRetour.add(lstAttentes);
        }

        return lstRetour;
    }
}
