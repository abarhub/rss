/**
 * Created by Alain on 20/11/2016.
 */

angular.module('toolsApp')
        .service('restTemplateService',
                function($http) {
                    return {

                        racineUrl: "/web",

                        /**
                         * ajoute une url avec un nom
                         * @param nomAAjouter
                         * @param urlAAjouter
                         * @returns {*}
                         */
                        ajouteUrl: function (nomAAjouter, urlAAjouter) {
                            return $http.post(this.racineUrl+'/add_url?name='+encodeURIComponent(nomAAjouter)+
                                    '&url=' + encodeURIComponent(urlAAjouter));
                        },

                        /**
                         * retourne la liste des url
                         * @returns {*}
                         */
                        getListeUrl: function () {
                            return $http.get(this.racineUrl+'/listeUrl');
                        },

                        /**
                         * retourne la liste des items d'une url
                         * @param id
                         * @returns {*}
                         */
                        getListeMessages: function (id) {
                            return $http.get(this.racineUrl+'/listeMessages?id='+id);
                        },

                        /**
                         * retourne la liste des catégories
                         * @returns {*}
                         */
                        getListeCategories: function () {
                            return $http.get(this.racineUrl+'/listeCategorie');
                        },

                        /**
                         * retourne la liste des url en fonction de l'id
                         * @param id
                         * @returns {*}
                         */
                        getListeUrl2: function (id) {
                            return $http.get(this.racineUrl+'/listeMessages2?type=categorie&id='+id);
                        },

                        /**
                         * retourne la liste des messages en fonction de l'id
                         * @param id
                         * @returns {*}
                         */
                        getListeMessages2: function (id) {
                            return $http.get(this.racineUrl+'/listeMessages2?type=flux&id='+id);
                        },


                    }
                }
        );