/**
 * Created by Alain on 25/09/2016.
 */



angular.module('usersApp', ['ngSanitize'])
        .controller('UsersController', ['$scope', '$http', '$interval',
            function($scope, $http, $interval) {
                var usersThis = this;

                usersThis.listeUsers=[
                    {"nom":"Dupuis","prenom":"pierre","id":"1"},
                    {"nom":"Dupond","prenom":"jacques","id":"2"}
                ];

                usersThis.searchUserText="";

                usersThis.search=function() {
                  //alert("Coucou");

                    var nom=usersThis.searchUserText;

                    usersThis.searchUserText="";

                    $http.get('/searchUsers?nom='+nom).then(function(value) {
                        logMsgInfo($http,"Ok");
                        //alert("Debut");
                        var tmp=value.data;
                        logMsgInfo($http,"res="+tmp);
                        //alert("suite");
                        //todoList.liste1=tmp.liste_channel;
                        logMsgInfo($http,"suite");
                        //alert("res");
                        //todoList.liste_url=tmp.liste_channel;
                        usersThis.listeUsers=tmp.listUserUI;//ListUsersUI
                        /*usersThis.listeUsers={};
                        if(isDefined(tmp.listUsersUI)){
                            if(tmp.listUsersUI.length>0){
                                tmp.listUsersUI.forEach(function (element, index, array) {
                                    var nom='';
                                    var prenom='';
                                    var id='';
                                    if(isDefined(element.nom)){
                                        nom=element.nom;
                                    }
                                    if(isDefined(element.prenom)){
                                        prenom=element.prenom;
                                    }
                                    if(isDefined(element.id)){
                                        id=element.id;
                                    }
                                    usersThis.listeUsers.push({"nom":nom,"prenom":prenom,"id":id});
                                });
                            }
                        }*/
                        //todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                        logMsgInfo($http,"Fin traitement");
                        //alert("Fin traitement");
                    }, function(reason) {
                        logMsgErr($http,"Error : "+reason);
                        //alert("Error : "+reason);
                    }, function(value) {
                        // notifyCallback
                    });
                };

                // test
            }
       ]);
