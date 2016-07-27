/**
 * Created by Alain on 01/11/2015.
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
    /*var niveauErreur;
    var composant;
    var message;
    niveauErreur='Erreur';
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
            });*/
}

function logMsgErr($http,s){
    logMsg2($http,s,'Erreur');
}

function logMsgInfo($http,s){
    logMsg2($http,s,'Info');
}

function logMsg2($http,s,niveau){
    console.log(s);
    alert(s);
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

angular.module('todoApp', ['ngSanitize'])
        .controller('TodoListController', function($http) {
            var todoList = this;
            /*todoList.todos = [
                {text:'learn angular', done:true},
                {text:'build an angular app', done:false}];*/

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

            todoList.updateList=function() {
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
            }

            todoList.addUrl=function() {
                //alert("Test1");
                logMsgInfo($http,"Test1 addUrl");
                var urlAAjouter=todoList.urlAAjouter;
                var nomAAjouter=todoList.nomAAjouter;
                if (typeof urlAAjouter !== 'undefined') {
                    if(typeof nomAAjouter !== 'undefined') {
                        if (stringStartWith(urlAAjouter, "http")) {
                            if(nomAAjouter!='') {
                                logMsgInfo($http,"URL a ajouter : " + urlAAjouter);
                                todoList.urlAAjouter = "";
                                todoList.nomAAjouter = "";
                                //$http.post('/api3/add_url',  { 'name' : 'test1', 'url' : urlAAjouter });
                                $http.post('/add_url?name='+encodeURIComponent(nomAAjouter)+
                                        '&url=' + encodeURIComponent(urlAAjouter))
                                        .then(function (value) {
                                            logMsgInfo($http,"ok : " + value);
                                            logMsgInfo($http,"appel updateListFlux ...");
                                            todoList.updateListFlux();
                                            logMsgInfo($http,"appel updateListFlux fin");
                                        }, function (reason) {
                                            logMsgErr($http,"Error : " + reason);
                                        });
                            } else {
                                logMsgErr($http,"nom vide !");
                            }
                        }
                        else {
                            logMsgErr($http,"url invalide !");
                        }
                    } else {
                        logMsgErr($http,"nom invalide !");
                    }
                }
                logMsgInfo($http,"fin methode addUrl !");
            }



            todoList.updateListFlux=function() {
                logMsgInfo($http,"Test1 updateListFlux");
                $http.get('/listeUrl').then(function(value) {
                    logMsgInfo($http,"Ok");
                    var tmp=value.data;
                    logMsgInfo($http,"res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logMsgInfo($http,"suite");
                    todoList.liste_url=tmp.liste_channel;
                    //todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                    logMsgInfo($http,"Fin traitement");
                }, function(reason) {
                    logMsgErr($http,"Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }


            todoList.updateMsg=function(id) {
                logMsgInfo($http,"Test2 updateMsg:"+id);
                $http.get('/listeMessages?id='+id).then(function(value) {
                    logMsgInfo($http,"Ok");
                    var tmp=value.data;
                    logMsgInfo($http,"res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    logMsgInfo($http,"suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.listeItem;
                    logMsgInfo($http,"Fin traitement");
                }, function(reason) {
                    logMsgErr($http,"Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }
        });


