var stompEstrades = null;


function connexion() {
    var socket1 = new SockJS('/webSocket');
    stompEstrades = Stomp.over(socket1);

    stompEstrades.connect({}, function (frame) {

        console.log('Connected stompEstrade: ' + frame);

        stompEstrades.subscribe('/sujet/combat/position', function (reponse) {

            // vider les sièges
            viderLesSieges("spectateur");
            viderLesSieges("competiteur");

            console.log(reponse.body.toString());
            remplirLesSieges(reponse.body.toString());

        });

        stompEstrades.send("/app/message/combat/position", {}, JSON.stringify({'courriel': "" , 'jid': "-1" , 'position': "", 'ajoutouretire': "" }));

    });
}


function notifierPositionAuServeur(position) {

    var positionActuelle = $("#positionactuelle").val();

    var courriel = $("#courriel").val();
    var avatar = $("#avatar").val();

    // retirer de l'ancienne liste
    if (positionActuelle === "spectateur"){
        stompSpectateur.send("/app/connexion/spectateur", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "competiteur"){
        stompCompetiteur.send("/app/connexion/competiteur", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "arbitre"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "blanc"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "rouge"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }

    // indiquer la nouvelle position actuelle
    $("#positionactuelle").val(position);

    // rajouter à la nouvelle liste
    if (position === "spectateur"){
        stompSpectateur.send("/app/connexion/spectateur", {}, JSON.stringify({'courriel': courriel , 'avatar': avatar , 'position': position, 'ajoutouretire': "AJOUT" }));
    }
    else if (position === "competiteur"){
        stompCompetiteur.send("/app/connexion/competiteur", {}, JSON.stringify({'courriel': courriel , 'avatar': avatar , 'position': position, 'ajoutouretire': "AJOUT" }));
    }
    else if (position === "arbitre"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': avatar , 'position': position, 'ajoutouretire': "AJOUT" }));
    }
    else if (position === "blanc"){
        stompTapis.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': avatar , 'position': position, 'ajoutouretire': "AJOUT" }));
    }
    else if (position === "rouge"){
        stompTapis.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': avatar , 'position': position, 'ajoutouretire': "AJOUT" }));
    }

}

function deconnexion(){
    var positionActuelle = $("#positionactuelle").val();
    var courriel = $("#courriel").val();
    var avatar = $("#avatar").val();

    // retirer de sa position actuelle
    if (positionActuelle === "spectateur"){
        stompSpectateur.send("/app/connexion/spectateur", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "competiteur"){
        stompCompetiteur.send("/app/connexion/competiteur", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
    }
    else if (positionActuelle === "arbitre"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
        stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': "" , 'attaque': "", 'couleur': "RETIRE" }));
    }
    else if (positionActuelle === "blanc"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
        stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': "" , 'attaque': "", 'couleur': "RETIRE" }));
    }
    else if (positionActuelle === "rouge"){
        stompCompetiteur.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "avatar" , 'position': positionActuelle, 'ajoutouretire': "RETIRE" }));
        stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': "" , 'attaque': "", 'couleur': "RETIRE" }));
    }

    booInGame = false;
    viderLesSieges("spectateur");
    viderLesSieges("competiteur");
    viderLesSieges("tapis");
    viderLesSieges("attaques");

    $("#btnJoindre").show();
    $("#btnQuitter").hide();

    $("#spectateur").prop("disabled", true);
    $("#competiteur").prop("disabled", true);
    $("#arbitre").prop("disabled", true);
    $("#blanc").prop("disabled", true);
    $("#rouge").prop("disabled", true);

    $("#spectateur").prop("checked", false);
    $("#competiteur").prop("checked", false);
    $("#arbitre").prop("checked", false);
    $("#blanc").prop("checked", false);
    $("#rouge").prop("checked", false);

    $("#roche").prop("checked", false);
    $("#papier").prop("checked", false);
    $("#sciseau").prop("checked", false);

    $("#groupe2").hide();
    $("#groupe3").hide();
    $("#groupe1").show();
    $("#tableau-gagnant").hide();
    $("#tableau-egalite").hide();

    $("#positionactuelle").val("AUCUNE");
}

function remplirLesSieges(listes){

    var base = JSON.parse(listes);
    for (var i = 0; i < base[0].length; i++){
        if (i < 12) {
            document.getElementById('spectateur_' + i).src = base[0][i].avatar;
        }
    }

    for (var i = 0; i < base[1].length; i++){
        if (i < 12) {
            document.getElementById('competiteur_' + i).src = base[0][i].avatar;
        }
    }
    /*var lstSpectateurs = JSON.parse(base[0]);
    var lstCompetiteurs = JSON.parse(base[1]);

    console.log("Spectateurs: " + lstSpectateurs.toString());
    console.log("Compétiteurs: " + lstCompetiteurs.toString());*/
}

function remplirLeTapis(utilisateur){
    var arbitreDisabled = false;
    var blancDisabled = false;
    var rougeDisabled = false;

    var obj = JSON.parse(utilisateur);
    for (var i = 0; i < obj.length; i++){
        if (i < 3){

            console.log("Courriel: " + obj[i].courriel + "Avatar: " + obj[i].avatar + "Position: " + obj[i].position + "Ajout ou Retire: " + obj[i].ajoutouretire);
            if (obj[i].position === "arbitre"){
                arbitreDisabled = true;
                document.getElementById('tapis_arbitre').src = obj[i].avatar;
            }
            else if (obj[i].position === "blanc"){
                blancDisabled = true;
                document.getElementById('tapis_blanc').src = obj[i].avatar;
            }
            else if (obj[i].position === "rouge"){
                rougeDisabled = true;
                document.getElementById('tapis_rouge').src = obj[i].avatar;
            }
        }
    }

    $("#arbitre").prop("disabled", arbitreDisabled);
    $("#blanc").prop("disabled", blancDisabled);
    $("#rouge").prop("disabled", rougeDisabled);

    if (arbitreDisabled && blancDisabled && rougeDisabled){
        // commencer le combat
        // bloquer les personnes sur le tapis
        commencerCombat();
    }
    else{
        annulerCombat();
    }

}
function afficherMenuCompetiteur(){
    $("#groupe2").show();
}

function commencerCombat(){
    var positionActuelle = $("#positionactuelle").val();
    if (positionActuelle === "arbitre"){
        $("#groupe1").hide();
        $("#groupe2").hide();
    }
    else if (positionActuelle === "blanc"){
        $("#groupe1").hide();
        $("#groupe2").hide();
        $("#groupe3").show();
    }
    else if (positionActuelle === "rouge"){
        $("#groupe1").hide();
        $("#groupe2").hide();
        $("#groupe3").show();
    }
}

function annulerCombat(){
    var positionActuelle = $("#positionactuelle").val();
    if (positionActuelle === "arbitre"){
        $("#groupe1").show();
        $("#groupe2").show();
        $("#roche").prop("checked", false);
        $("#papier").prop("checked", false);
        $("#sciseau").prop("checked", false);
    }
    else if (positionActuelle === "blanc"){
        $("#groupe1").show();
        $("#groupe2").show();
        $("#roche").prop("checked", false);
        $("#papier").prop("checked", false);
        $("#sciseau").prop("checked", false);
    }
    else if (positionActuelle === "rouge"){
        $("#groupe1").show();
        $("#groupe2").show();
        $("#roche").prop("checked", false);
        $("#papier").prop("checked", false);
        $("#sciseau").prop("checked", false);
    }
    $("#tableau-gagnant").hide();
    $("#tableau-egalite").hide();
    $("#groupe3").hide();
}

function viderLesSieges(type){

    if (type === "spectateur"){
        for (var i = 0; i < 12; i++){
            document.getElementById('spectateur_' + i).src = "images/chaise.jpg";
        }
    }

    if (type === "competiteur"){
        for (var i = 0; i < 12; i++){
            document.getElementById('competiteur_' + i).src = "images/chaise.jpg";
        }
    }

    if (type === "tapis"){
        document.getElementById('tapis_blanc').src = "images/white_square.jpg";
        document.getElementById('tapis_arbitre').src = "images/ying_yang.jpg";
        document.getElementById('tapis_rouge').src = "images/red_square.png";
    }

    if (type === "attaques"){
        document.getElementById('tapis_attaque_blanc').src = "images/pas_attaque.png";
        document.getElementById('tapis_attaque_rouge').src = "images/pas_attaque.png";
    }

}

function joindre(){

    booInGame = true;
    stompSpectateur.send("/app/connexion/spectateur", {}, JSON.stringify({'courriel': "" , 'avatar': "" , 'position': "", 'ajoutouretire': "PREMIERE" }));
    stompCompetiteur.send("/app/connexion/competiteur", {}, JSON.stringify({'courriel': "" , 'avatar': "" , 'position': "", 'ajoutouretire': "PREMIERE" }));
    stompTapis.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': "" , 'avatar': "" , 'position': "", 'ajoutouretire': "PREMIERE" }));
    stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': "" , 'attaque': "", 'couleur': "PREMIERE" }));


    $("#btnJoindre").hide();
    $("#btnQuitter").show();
}

function envoyerAttaque(attaque){
    var positionActuelle = $("#positionactuelle").val();
    var courriel = $("#courriel").val();

    stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': courriel , 'attaque': attaque, 'couleur': positionActuelle }));
}

function afficherAttaques(attaques){
    var courriel = $("#courriel").val();
    var obj = JSON.parse(attaques);

    for (var i = 0; i < obj.length; i++){
        console.log("Courriel: " + obj[i].courriel + "Attaque: " + obj[i].attaque + "Couleur: " + obj[i].couleur);
        if (obj.length == 3){

            if (i == 2) {
                if (obj[i].couleur === "DRAW"){
                    document.getElementById('joueur-blanc').src = obj[i].courriel;
                    document.getElementById('joueur-rouge').src = obj[i].attaque;
                    $("#tableau-egalite").show();
                }
                else{
                    document.getElementById('avatar_gagnant').src = obj[i].attaque;
                    document.getElementById('courriel_gagnant').innerHTML = obj[i].courriel;
                    $("#tableau-gagnant").show();
                }

                setTimeout(terminerGame, 4000);
            }
            else {
                if (obj[i].couleur === "blanc"){
                    if (obj[i].attaque === "roche")
                        document.getElementById('tapis_attaque_blanc').src = "images/roche.png";
                    else if (obj[i].attaque === "papier")
                        document.getElementById('tapis_attaque_blanc').src = "images/papier.png";
                    else if (obj[i].attaque === "sciseau")
                        document.getElementById('tapis_attaque_blanc').src = "images/sciseau.png";
                }
                else if (obj[i].couleur === "rouge"){
                    if (obj[i].attaque === "roche")
                        document.getElementById('tapis_attaque_rouge').src = "images/roche.png";
                    else if (obj[i].attaque === "papier")
                        document.getElementById('tapis_attaque_rouge').src = "images/papier.png";
                    else if (obj[i].attaque === "sciseau")
                        document.getElementById('tapis_attaque_rouge').src = "images/sciseau.png";
                }
            }
        }
        else if (obj[i].courriel === courriel && obj.length < 2){
            if (obj[i].couleur === "blanc"){
                if (obj[i].attaque === "roche")
                    document.getElementById('tapis_attaque_blanc').src = "images/roche.png";
                else if (obj[i].attaque === "papier")
                    document.getElementById('tapis_attaque_blanc').src = "images/papier.png";
                else if (obj[i].attaque === "sciseau")
                    document.getElementById('tapis_attaque_blanc').src = "images/sciseau.png";
            }
            else if (obj[i].couleur === "rouge"){
                if (obj[i].attaque === "roche")
                    document.getElementById('tapis_attaque_rouge').src = "images/roche.png";
                else if (obj[i].attaque === "papier")
                    document.getElementById('tapis_attaque_rouge').src = "images/papier.png";
                else if (obj[i].attaque === "sciseau")
                    document.getElementById('tapis_attaque_rouge').src = "images/sciseau.png";
            }
        }
    }
}

function terminerGame(){
    var positionActuelle = $("#positionactuelle").val();
    var courriel = $("#courriel").val();

    annulerCombat();

    if (positionActuelle === "arbitre" || positionActuelle === "blanc" || positionActuelle === "rouge"){
        $("#groupe2").hide();
        $("#competiteur").prop("checked", false);
        $("#arbitre").prop("checked", false);
        $("#blanc").prop("checked", false);
        $("#rouge").prop("checked", false);
    }

    if (positionActuelle === "arbitre" || positionActuelle === "blanc" || positionActuelle === "rouge"){
        stompTapis.send("/app/connexion/tapis", {}, JSON.stringify({'courriel': courriel , 'avatar': "" , 'position': positionActuelle, 'ajoutouretire': "RESET" }));
        stompAttaques.send("/app/connexion/attaques", {}, JSON.stringify({'courriel': "" , 'attaque': "" , 'couleur': "RESET" }));
    }

    $("#positionactuelle").val("AUCUNE");
}


$(document).ready( function(){
    connexion();

});





