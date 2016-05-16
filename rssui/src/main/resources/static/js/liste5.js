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

angular.module('todoApp', ['ngSanitize'])
        .controller('TodoListController', function($http) {
            var todoList = this;
            todoList.todos = [
                {text:'learn angular', done:true},
                {text:'build an angular app', done:false}];

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

            todoList.addTodo = function() {
                todoList.todos.push({text:todoList.todoText, done:false});
                todoList.todoText = '';
            };

            todoList.remaining = function() {
                var count = 0;
                angular.forEach(todoList.todos, function(todo) {
                    count += todo.done ? 0 : 1;
                });
                return count;
            };

            todoList.archive = function() {
                var oldTodos = todoList.todos;
                todoList.todos = [];
                angular.forEach(oldTodos, function(todo) {
                    if (!todo.done) todoList.todos.push(todo);
                });
            };

            todoList.updateList=function() {
                alert("Test1");
                $http.get('/liste').then(function(value) {
                    alert("Ok");
                    var tmp=value.data;
                    alert("res="+tmp);
                    todoList.liste1=tmp.liste_channel;
                    alert("suite");
                    todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                    alert("Fin traitement");
                }, function(reason) {
                    alert("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }

            todoList.addUrl=function() {
                alert("Test1");
                var urlAAjouter=todoList.urlAAjouter;
                if (typeof urlAAjouter !== 'undefined') {
                    if(stringStartWith(urlAAjouter,"http"))
                    {
                        alert("URL a ajouter : " + urlAAjouter);
                        todoList.urlAAjouter = "";
                        //$http.post('/api3/add_url',  { 'name' : 'test1', 'url' : urlAAjouter });
                        $http.post('/add_url?name=test1&url=' + encodeURIComponent(urlAAjouter), false, function (reason) {
                            alert("Error : " + reason);
                        }, function (value) {
                            alert("ok : " + value);
                        });
                    }
                    else
                    {
                        alert("url invalide !");
                    }
                    /*$http.get('/liste').then(function(value) {
                     alert("Ok");
                     var tmp=value.data;
                     alert("res="+tmp);
                     todoList.liste1=tmp.liste_channel;
                     alert("suite");
                     todoList.liste_url=tmp.liste_channel;
                     todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                     alert("Fin traitement");
                     }, function(reason) {
                     alert("Error : "+reason);
                     }, function(value) {
                     // notifyCallback
                     });*/
                }
            }



            todoList.updateListFlux=function() {
                alert("Test1");
                $http.get('/listeUrl').then(function(value) {
                    alert("Ok");
                    var tmp=value.data;
                    alert("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    alert("suite");
                    todoList.liste_url=tmp.liste_channel;
                    //todoList.liste_item_select=tmp.liste_channel[0].listeItem;
                    alert("Fin traitement");
                }, function(reason) {
                    alert("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }


            todoList.updateMsg=function(id) {
                alert("Test2:"+id);
                $http.get('/listeMessages?id='+id).then(function(value) {
                    alert("Ok");
                    var tmp=value.data;
                    alert("res="+tmp);
                    //todoList.liste1=tmp.liste_channel;
                    alert("suite");
                    //todoList.liste_url=tmp.liste_channel;
                    todoList.liste_item_select=tmp.listeItem;
                    alert("Fin traitement");
                }, function(reason) {
                    alert("Error : "+reason);
                }, function(value) {
                    // notifyCallback
                });
            }
        });


