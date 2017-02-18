/**
 * Created by Alain on 20/11/2016.
 */


angular.module('toolsApp',[])
        .service('toolsService',
                    function() {
                        return {
                            stringStartWith: function (s1, s2) {
                                if (typeof s1 !== 'undefined') {
                                    if (typeof s2 !== 'undefined') {
                                        return s1.lastIndexOf(s2, 0) === 0;
                                    }
                                }
                                return false;
                            },


                            isDefined: function (s) {
                                if (typeof s !== 'undefined') {
                                    return true;
                                } else {
                                    return false;
                                }
                            }

                        }
                    }
                );
