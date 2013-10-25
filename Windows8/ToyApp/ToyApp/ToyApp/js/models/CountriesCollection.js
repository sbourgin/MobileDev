(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a countries collection
        CountriesCollection: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
                collection: [],

                fetchAsync: function () {
                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {

                        AJAX.Request.fetchAsync("countries", {}).then(
                            function complete(countries) {
                                for (var i = 0 ; i < countries.length ; i++) {
                                    self.collection.push({
                                        key: countries[i],
                                        name: CityAPI.CountriesCollection.countriesName[countries[i]]
                                    });
                                }

                                onSuccess(self.collection);
                            }
                        );
                    });
                },
            },

            //static methods
            {
                countriesName: {
                    'US': 'United States',
                    'GB': 'Great Britain',
                    'IE': 'Ireland',
                    'NZ': 'New Zealand',
                },
            }
        ),
    });
})();