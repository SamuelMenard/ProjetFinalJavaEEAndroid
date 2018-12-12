package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.dao.CombatDao;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entites.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ControleurMVC {

    @Autowired
    private CompteDao compteDao;

    @Autowired
    private CombatDao combatDao;

    @RequestMapping(value = {"/", "/dojo"}, method = RequestMethod.GET)
    public String racine(@Autowired Authentication auth, Map<String, Object> model) {

        if (auth != null){
            UserDetails details = (UserDetails)auth.getPrincipal();

            if (details != null){
                Optional opt = compteDao.findById(details.getUsername());
                Compte compte = (Compte)opt.get();

                model.put("courriel", compte.getUsername());
                model.put("alias", compte.getFullname());
                model.put("role", compte.getRole().getRole());
                model.put("groupe", compte.getGroupe().getGroupe());
                model.put("avatar", compte.getAvatar().getAvatar());
            }
        }
        else{
            model.put("courriel", "visiteur@visiteur.org");
            model.put("alias", "visiteur");
            model.put("role", "aucun rôle");
            model.put("groupe","aucun groupe");
            model.put("avatar", "");
        }


        return "dojo";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Map<String, Object> model) {
        return "login";
    }

    @RequestMapping(value = "/examen", method = RequestMethod.GET)
    public String examen(Map<String, Object> model) {
        List<Compte> lstAdmissibles = new ArrayList<>();
        List<Compte> lstHontes = new ArrayList<>();
        List<Compte> lstDevenirAncien = new ArrayList<>();
        List<Compte> lstDevenirSensei = new ArrayList<>();
        List<Compte> lstRetirerSensei = new ArrayList<>();

        List<Compte> comptes = this.compteDao.findAll();

        for (Compte cpt : comptes){
            if (cpt.getPoints() >= 100 && cpt.getCredits(this.combatDao) >= 10){
                if (cpt.getRole().getRole().compareToIgnoreCase("SENSEI") != 0 && cpt.getRole().getRole().compareToIgnoreCase("VENERABLE") != 0){
                    if (cpt.getGroupe().getGroupe().compareToIgnoreCase("NOIR") != 0){
                        lstAdmissibles.add(cpt);
                    }
                }
            }

            if (cpt.getNbExamensEvalue() > 0){
                if (cpt.resultatDernierExamen() == false){
                    lstHontes.add(cpt);
                }
            }

            if (cpt.getNbCombats() >= 30 && cpt.getCredits(this.combatDao) >= 10){
                if (cpt.getRole().getRole().compareToIgnoreCase("NOUVEAU") == 0){
                    lstDevenirAncien.add(cpt);
                }
            }

            if (cpt.getGroupe().getGroupe().compareToIgnoreCase("NOIR") == 0 && cpt.getRole().getRole().compareToIgnoreCase("SENSEI") != 0 && cpt.getRole().getRole().compareToIgnoreCase("VENERABLE") != 0){
                lstDevenirSensei.add(cpt);
            }

            if (cpt.getGroupe().getGroupe().compareToIgnoreCase("NOIR") == 0 && cpt.getRole().getRole().compareToIgnoreCase("SENSEI") == 0){
                lstRetirerSensei.add(cpt);
            }
        }

        model.put("lstAdmissibles", lstAdmissibles);
        model.put("lstHontes", lstHontes);
        model.put("lstDevenirAncien", lstDevenirAncien);
        model.put("lstDevenirSensei", lstDevenirSensei);
        model.put("lstRetirerSensei", lstRetirerSensei);

        return "examen";
    }

}
