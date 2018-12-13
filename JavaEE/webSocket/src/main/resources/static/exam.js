function examenReussi(evalue, evaluateur) {
    $.ajax({
        type: 'GET',
        url: "/examens/reussi/" + evaluateur + "/" + evalue,
        dataType: 'text',
        success: function() {
            window.location.href = "/examen";
        },
        error: function() {
            alert('Error loading ');
        }
    });

}

function examenEchoue(evalue, evaluateur){
    $.ajax({
        type: 'GET',
        url: "/examens/echoue/" + evaluateur + "/" + evalue,
        dataType: 'text',
        success: function() {
            window.location.href = "/examen";
        },
        error: function() {
            alert('Error loading ');
        }
    });
}

function promouvoirAAncien(courriel){
    $.ajax({
        type: 'GET',
        url: "/examen/promouvoiraancien/" + courriel,
        dataType: 'text',
        success: function() {
            window.location.href = "/examen";
        },
        error: function() {
            alert('Error loading ');
        }
    });
}

function promouvoirASensei(courriel){
    $.ajax({
        type: 'GET',
        url: "/examen/promouvoirasensei/" + courriel,
        dataType: 'text',
        success: function() {
            window.location.href = "/examen";
        },
        error: function() {
            alert('Error loading ');
        }
    });
}

function retirerSensei(courriel){
    $.ajax({
        type: 'GET',
        url: "/examen/promouvoiraancien/" + courriel,
        dataType: 'text',
        success: function() {
            window.location.href = "/examen";
        },
        error: function() {
            alert('Error loading ');
        }
    });
}