package cgg.informatique.jfl.webSocket.controleurs;



        import cgg.informatique.jfl.webSocket.Message;
        import cgg.informatique.jfl.webSocket.Reponse;
        import cgg.informatique.jfl.webSocket.dao.CompteDao;
        import cgg.informatique.jfl.webSocket.entites.Compte;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.messaging.handler.annotation.MessageMapping;
        import org.springframework.messaging.handler.annotation.SendTo;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.CrossOrigin;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.util.HtmlUtils;

        import java.util.HashMap;
        import java.util.Map;
        import java.util.Optional;

@RestController
public class ReponseControleur {
    static public Map<String, String> listeDesConnexions = new HashMap();
    static long id = 1;
    @Autowired
    private CompteDao compteDao;

    @CrossOrigin()
    @MessageMapping("/message/android")
    @SendTo("/message/reponseandroid")
    public String reponseAndroid(Message message) throws Exception {
        Optional<Compte> unCompte = compteDao.findById( message.getId_avatar());
        String courriel = unCompte.get().getUsername();
        String sessionRest = listeDesConnexions.get(courriel);

        if ( ( sessionRest != null) && (sessionRest.equals(message.getSessionRest())))
            System.out.println("/message/android sessionREST OK");
        else
            System.out.println("/message/android sessionREST OK");

        String messageString = new String();
        String deString = new String();

        String creationString = new Long( message.getCreation()).toString();
        if  (message.getDe().equals(""))
            deString = "Anonyme";
        else
            deString = message.getDe();

        messageString = deString + "-" +creationString + "-android";


        System.out.println(messageString);
        return messageString ;
    }

    @CrossOrigin()
    @MessageMapping("/message/publique")
    @SendTo("/sujet/reponsepublique")
    //Ne pas oublier de la changer par après
    public Reponse reponse(Message message) throws Exception {
        System.err.println(message.toString());
        System.out.println(message.toString());
        Map<String, String> listeJID = ReponseControleur.listeDesConnexions;

        String role = "";
        boolean booAuthorized = false;
        String userConnexion = ReponseControleur.listeDesConnexions.get(message.getDe());
        System.out.println(userConnexion);
        if (userConnexion != null){
            if (userConnexion.compareToIgnoreCase(message.getSessionRest()) == 0){
                booAuthorized = true;
                Optional<Compte> compte = compteDao.findById(message.getDe());
                role = compte.get().getRole().getRole();
            }
        }
        if(!booAuthorized || (role.compareToIgnoreCase("NOUVEAU") == 0)){
            System.out.println("Peut pas se connecter, car il n'est sois pas connecté ou il est nouveau");
            return new Reponse(message.getDe());
        }
        return new Reponse( id++, message.getDe(), message.getTexte() ,message.getDate() , message.getAvatar(), message.getType());
    }

    @CrossOrigin()
    @MessageMapping("/message/prive")
    @SendTo("/sujet/reponseprive")
    public Reponse reponsePrive(Message message) throws Exception {
        System.err.println(message.toString());
        Map<String, String> listeJID = ReponseControleur.listeDesConnexions;

        String role = "";
        boolean booAuthorized = false;
        String userConnexion = ReponseControleur.listeDesConnexions.get(message.getDe());
        System.out.println(userConnexion);
        if (userConnexion != null){
            if (userConnexion.compareToIgnoreCase(message.getSessionRest()) == 0){
                booAuthorized = true;
                Optional<Compte> compte = compteDao.findById(message.getDe());
                role = compte.get().getRole().getRole();
            }
        }
        if(!booAuthorized){
            System.out.println("Peut pas envoyer le message privé, car l'utilisateur n'est pas connecté");
            return new Reponse(message.getDe());
        }
        return new Reponse( id++, message.getDe(), message.getTexte() ,message.getDate() , message.getAvatar(), message.getType());
    }

}
