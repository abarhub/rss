/**
 * Created by Alain on 01/11/2015.
 */

angular.module('todoApp')
        .controller('TodoListController', ['$scope', '$http', '$interval','logService',
                function($scope, $http, $interval, logService) {
            var todoList = this;

            var stopUpdateDisplay;

            todoList.racineUrl="/web";

            todoList.liste1 = [
                {"url":"http://www.google.fr/0","listeItem":[{"title":"KKKKKK0-0","description":"GGGG0-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH0-0"},{"title":"KKKKKK0-1","description":"GGGG0-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH0-1"},{"title":"KKKKKK0-2","description":"GGGG0-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH0-2"}],"title":"BBBBBB0","description":"AAAA0","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"},
                {"url":"http://www.google.fr/1","listeItem":[{"title":"KKKKKK1-0","description":"GGGG1-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH1-0"},{"title":"KKKKKK1-1","description":"GGGG1-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH1-1"},{"title":"KKKKKK1-2","description":"GGGG1-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH1-2"}],"title":"BBBBBB1","description":"AAAA1","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"},
                {"url":"http://www.google.fr/2","listeItem":[{"title":"KKKKKK2-0","description":"GGGG2-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH2-0"},{"title":"KKKKKK2-1","description":"GGGG2-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH2-1"},{"title":"KKKKKK2-2","description":"GGGG2-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH2-2"}],"title":"BBBBBB2","description":"AAAA2","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"}
            ];

            todoList.liste_url = [
                {"url":"http://www.google.fr/0","listeItem":[{"title":"KKKKKK0-0","description":"GGGG0-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH0-0"},{"title":"KKKKKK0-1","description":"GGGG0-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH0-1"},{"title":"KKKKKK0-2","description":"GGGG0-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH0-2"}],"title":"BBBBBB0","description":"AAAA0","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"},
                {"url":"http://www.google.fr/1","listeItem":[{"title":"KKKKKK1-0","description":"GGGG1-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH1-0"},{"title":"KKKKKK1-1","description":"GGGG1-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH1-1"},{"title":"KKKKKK1-2","description":"GGGG1-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH1-2"}],"title":"BBBBBB1","description":"AAAA1","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"},
                {"url":"http://www.google.fr/2","listeItem":[{"title":"KKKKKK2-0","description":"GGGG2-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH2-0"},{"title":"KKKKKK2-1","description":"GGGG2-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH2-1"},{"title":"KKKKKK2-2","description":"GGGG2-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH2-2"}],"title":"BBBBBB2","description":"AAAA2","language":"en","lastBuildDate":"2001-01-01","pubDate":"2001-01-02"}
            ];

            todoList.liste_item_select = [
                {"title":"KKKKKK0-0","description":"GGGG0-0","link":"http://www.yahoo.fr/0","pubDate":"2001-01-03","guid":"HHHH0-0"},
                {"title":"KKKKKK0-1","description":"GGGG0-1","link":"http://www.yahoo.fr/1","pubDate":"2001-01-03","guid":"HHHH0-1"},
                {"title":"KKKKKK0-2","description":"GGGG0-2","link":"http://www.yahoo.fr/2","pubDate":"2001-01-03","guid":"HHHH0-2"}

            ];

            todoList.urlAAjouter = undefined;
            todoList.nomAAjouter = undefined;

            todoList.listeCategories=[];

            /*todoList.updateList=function() {
                logMsgInfo($http,"Test1 updateList");
                $http.get('/liste').then(function(value) {
                    logMsgInfo($http,"Ok");
                    var tmp=value.data;
                    logMsgInfo($http,"res="+tmp);
                    todoList.liste1=tmp.liste_channel;
                    logMsgInfo($http,"suite");
                    todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                    logMsgInfo($http,"Fin traitement");
                }, function(reason) {
                    logMsgErr($http,"Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }*/

            todoList.addUrl=function() {
                logService.logMsgInfo("Test1 addUrl");
                var urlAAjouter=todoList.urlAAjouter;
                var nomAAjouter=todoList.nomAAjouter;
                if (typeof urlAAjouter !== 'undefined') {
                    if(typeof nomAAjouter !== 'undefined') {
                        if (stringStartWith(urlAAjouter, "http")) {
                            if(nomAAjouter!='') {
                                logService.logMsgInfo("URL a ajouter : " + urlAAjouter);
                                todoList.urlAAjouter = "";
                                todoList.nomAAjouter = "";
                                $http.post(todoList.racineUrl+'/add_url?name='+encodeURIComponent(nomAAjouter)+
                                        '&url=' + encodeURIComponent(urlAAjouter))
                                        .then(function (value) {
                                            logService.logMsgInfo("ok : " + value);
                                            logService.logMsgInfo("appel updateListFlux ...");
                                            todoList.updateListFlux();
                                            logService.logMsgInfo("appel updateListFlux fin");
                                        }, function (reason) {
                                            logMsgErr("Error : " + reason);
                                        });
                            } else {
                                logService.logMsgErr("nom vide !");
                            }
                        }
                        else {
                            logService.logMsgErr("url invalide !");
                        }
                    } else {
                        logService.logMsgErr("nom invalide !");
                    }
                }
                logService.logMsgInfo("fin methode addUrl !");
            }



            todoList.updateListFlux=function() {
                logService.logMsgInfo("Test1 updateListFlux");
                $http.get(todoList.racineUrl+'/listeUrl').then(function(value) {
                    logService.logMsgInfo("Ok");
                    var tmp=value.data;
                    logService.logMsgInfo("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logService.logMsgInfo("suite");
                    todoList.liste_url=tmp.liste_channel;
                    //todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                    logService.logMsgInfo("Fin traitement");
                }, function(reason) {
                    logService.logMsgErr("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }


            todoList.updateMsg=function(id) {
                logService.logMsgInfo("Test2 updateMsg:"+id);
                $http.get(todoList.racineUrl+'/listeMessages?id='+id).then(function(value) {
                    logService.logMsgInfo("Ok");
                    var tmp=value.data;
                    logService.logMsgInfo("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logService.logMsgInfo("suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.listeItem;
                    logService.logMsgInfo("Fin traitement");
                }, function(reason) {
                    logService.logMsgErr("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }

            todoList.updateAffichage=function() {
                if ( angular.isDefined(stopUpdateDisplay) ){
                    // arret de l'affichage recurrent
                    logService.logMsgInfo("arret display");
                    $interval.cancel(stopUpdateDisplay);
                    stopUpdateDisplay = undefined;
                } else {
                    // demarrage de l'affichage recurrent
                    logService.logMsgInfo("demarrage display");
                    //stopUpdateDisplay = $interval(todoList.updateListFlux, 10*1000);
                    stopUpdateDisplay = $interval(function() {
                        todoList.updateListFlux();
                    }, 10*1000);
                }
            }

            todoList.updateCategorie=function() {
                logService.logMsgInfo("Test2 updateCategorie");
                $http.get(todoList.racineUrl+'/listeCategorie').then(function(value) {
                    logService.logMsgInfo("Ok");
                    var tmp=value.data;
                    logService.logMsgInfo("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logService.logMsgInfo("suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.listeCategories=tmp.categorieUiList;
                    logService.logMsgInfo("Fin traitement");
                }, function(reason) {
                    logService.logMsgErr("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }

            todoList.selectCateg=function(id) {
                logService.logMsgInfo("Test2 updateMsg:"+id);
                $http.get(todoList.racineUrl+'/listeMessages2?type=categorie&id='+id).then(function(value) {
                    logService.logMsgInfo("Ok");
                    var tmp=value.data;
                    logService.logMsgInfo("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logService.logMsgInfo("suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.listeItem;
                    logService.logMsgInfo("Fin traitement");
                }, function(reason) {
                    logService.logMsgErr("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }

            todoList.selectFlux=function(id) {
                logService.logMsgInfo("Test2 updateMsg:"+id);
                $http.get(todoList.racineUrl+'/listeMessages2?type=flux&id='+id).then(function(value) {
                    logService.logMsgInfo("Ok");
                    var tmp=value.data;
                    logService.logMsgInfo("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logService.logMsgInfo("suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.listeItem;
                    logService.logMsgInfo("Fin traitement");
                }, function(reason) {
                    logService.logMsgErr("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }


        }]);


