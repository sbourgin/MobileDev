(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a people collection
        PeopleCollection: WinJS.Class.define(

            //properties of the class
            {
                collection: [],

                fetchAsync: function () {
                    var self = this;

                    AJAX.Request.fetchAsync("people", {}, function (people) {
                        for (var i = 0 ; i < people.length ; i++) {
                            self.collection.push(new CityAPI.Person(people[i]));
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