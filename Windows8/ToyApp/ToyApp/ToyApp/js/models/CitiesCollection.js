(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a cities collection
        CitiesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._country = data.country ? data.country : '';
                    this._collection = data.collection ? data.collection : [];
                }
            },

            //properties of the class
            {
                _id: 0,
                _country: '',

                _collection: [],

                country: {
                    get: function(){
                        return this._country;
                    }
                },

                collection: {
                    get: function () {
                        return this._collection;
                    },
                },

                fetchAsync: function (onSuccess) {
                    var self = this;

                    var parameters = {
                        id: this._id,
                        cn: this._country,
                    };

                    AJAX.Request.fetchAsync("cities", parameters,
                        function (cities) {
                            for (var i = 0, length = cities.length ; i < length ; i++) {
                                self._collection.push(new CityAPI.City(cities[i]));
                            }
                        }
                    );

                    this._id++;
                },
            },

            //static methods
            {
            }
        ),
    });
})();