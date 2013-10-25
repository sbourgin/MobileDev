(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a cities collection
        CitiesCollection: WinJS.Class.define(

            //properties of the class
            {
                fetchAsync: function () {
                    var self = this;

                    AJAX.Request.fetchAsync("cities", {}, function (cities) {
                        for (var i = 0 ; i < cities.length ; i++) {
                            self.collection.push(new CityAPI.City(cities[i]));
                        }
                    });
                },
            },

            //constructor
            function () {
            },

            //static methods
            {

            }
        ),
    });
})();