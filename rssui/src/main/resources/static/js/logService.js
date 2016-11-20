/**
 * Created by Alain on 19/11/2016.
 */

angular.module('todoApp')
        .service('logService',
            function($http) {

                return {
                    logMsg: function (s) {
                        console.log(s);
                        alert(s);
                    },

                    logMsgErr: function (s) {
                        this.logMsg2($http, s, 'Erreur');
                    },

                    logMsgInfo: function (s) {
                        this.logMsg2($http, s, 'Info');
                    },

                    logMsg2: function (http2, s, niveau) {
                        console.log(s);
                        //alert(s);
                        if (http2 !== 'undefined') {
                            var niveauErreur;
                            var composant;
                            var message;
                            var racineUrl = '/web';
                            niveauErreur = 'Info';
                            if (niveau == 'undefined') {
                                niveauErreur = niveau;
                            }
                            composant = 'UI';
                            message = s;
                            http2.post(racineUrl + '/traces?' +
                                    'niveauErreur=' + encodeURIComponent(niveauErreur) +
                                    '&composant=' + encodeURIComponent(composant) +
                                    '&message=' + encodeURIComponent(message))
                                    .then(function (value) {
                                        console.log("trace ok");
                                    }, function (reason) {
                                        console.log("Error : " + reason);
                                    });
                        }
                    }
                };


            });
