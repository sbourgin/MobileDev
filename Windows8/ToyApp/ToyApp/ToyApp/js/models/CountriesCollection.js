(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a countries collection
        CountriesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._collection = data.collection ? data.collection : [];
                }
            },

            //properties of the class
            {
                _collection: [],

                collection: {
                    get: function(){
                        return this._collection;
                    }
                },

                fetchAsync: function (onSuccess) {
                    var self = this;

                    AJAX.Request.fetchAsync("countries", {},
                        function(countries) {
                            for (var i = 0, length = countries.length ; i < length ; i++) {                                
                                self._collection.push(new CityAPI.Country({
                                    key: countries[i]
                                }));
                            }
                        }
                    );
                },
            },

            //static methods
            {
            }
        ),
    });
})();