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
            }
       ]);
