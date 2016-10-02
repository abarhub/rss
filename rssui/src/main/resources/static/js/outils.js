/**
 * Created by Alain on 02/10/2016.
 */

function stringStartWith(s1,s2) {
    if (typeof s1 !== 'undefined') {
        if (typeof s2 !== 'undefined') {
            return s1.lastIndexOf(s2, 0) === 0;
        }
    }
    return false;
}

function logMsg(s){
    console.log(s);
    alert(s);
}

function logMsgErr($http,s){
    logMsg2($http,s,'Erreur');
}

function logMsgInfo($http,s){
    logMsg2($http,s,'Info');
}

function logMsg2($http,s,niveau){
    console.log(s);
    //alert(s);
    if($http !== 'undefined'){
        var niveauErreur;
        var composant;
        var message;
        niveauErreur='Info';
        if(niveau == 'undefined'){
            niveauErreur=niveau;
        }
        composant='UI';
        message=s;
        $http.post('/traces?'+
                'niveauErreur='+encodeURIComponent(niveauErreur)+
                '&composant=' + encodeURIComponent(composant)+
                '&message=' + encodeURIComponent(message))
                .then(function (value) {
                    console.log("trace ok");
                }, function (reason) {
                    console.log("Error : " + reason);
                });
    }
}

function isDefined(s) {
    if (typeof s !== 'undefined'){
        return true;
    } else {
        return false;
    }
}
