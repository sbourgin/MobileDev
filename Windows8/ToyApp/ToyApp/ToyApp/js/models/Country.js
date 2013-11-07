(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a country object
        Country: WinJS.Class.define(

            //constructor
            function (data) {
                if (data && data.key) {
                    this._key   = data.key;
                    this._name  = CityAPI.Country.countriesName[this.key];
                }
            },

            //properties of the class
            {
                _key: '',
                _name: '',
                _picture: 'images/country.png',

                key: {
                    get: function () {
                        return this._key;
                    }
                },

                name: {
                    get: function () {
                        return this._name;
                    }
                },

                picture: {
                    get: function () {
                        return this._picture;
                    }
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