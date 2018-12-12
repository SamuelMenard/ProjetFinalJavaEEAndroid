package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.UtilisateurKumite;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ControleurJeu {
    List<UtilisateurKumite> lstAilleurs = new ArrayList<>();
    List<UtilisateurKumite> lstSpectateurs = new ArrayList<>();
    List<UtilisateurKumite> lstAttentes = new ArrayList<>();
    List<UtilisateurKumite> lstArbitres = new ArrayList<>();


    @CrossOrigin()
    @MessageMapping("/message/combat/position")
    @SendTo("/sujet/combat/position")
    public List<List<UtilisateurKumite>> reponseListes(UtilisateurKumite user) throws Exception {


        List<List<UtilisateurKumite>> lstRetour = new ArrayList<>();
        boolean booAuthorized = false;

        Map<String, String> listeJID = ReponseControleur.listeDesConnexions;

        String userConnexion = ReponseControleur.listeDesConnexions.get(user.getCourriel());
        if (userConnexion != null){
            if (userConnexion.compareToIgnoreCase(user.getJid()) == 0){
                booAuthorized = true;
            }
        }

        if (booAuthorized){
            if (user.getPosition().compareToIgnoreCase("ailleur") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstAilleurs.add(user);

                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstAilleurs.size(); i++){
                        if (lstAilleurs.get(i).getCourriel().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstAilleurs.remove(i);
                        }
                    }
                }
            }
            else if (user.getPosition().compareToIgnoreCase("spectateur") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstSpectateurs.add(user);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstSpectateurs.size(); i++){
                        if (lstSpectateurs.get(i).getCourriel().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstSpectateurs.remove(i);
                        }
                    }
                }
            }
            else if (user.getPosition().compareToIgnoreCase("attente") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstAttentes.add(user);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstAttentes.size(); i++){
                        if (lstAttentes.get(i).getCourriel().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstAttentes.remove(i);
                        }
                    }
                }
            }
            else if (user.getPosition().compareToIgnoreCase("arbitre") == 0){
                if (user.getAjoutouretire().compareToIgnoreCase("ajouter") == 0){
                    lstArbitres.add(user);
                }else if(user.getAjoutouretire().compareToIgnoreCase("retirer") == 0){
                    for (int i = 0; i < lstArbitres.size(); i++){
                        if (lstArbitres.get(i).getCourriel().compareToIgnoreCase(user.getCourriel()) == 0){
                            lstArbitres.remove(i);
                        }
                    }
                }
            }

            lstRetour.add(lstAilleurs);
            lstRetour.add(lstSpectateurs);
            lstRetour.add(lstAttentes);
            lstRetour.add(lstArbitres);
        }

        return lstRetour;
    }
}
