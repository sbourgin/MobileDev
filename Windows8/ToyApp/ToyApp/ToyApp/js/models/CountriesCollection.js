(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a countries collection
        CountriesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._items = data.items ? data.items : [];
                }
            },

            //properties of the class
            {
                _items: [],

                items: {
                    get: function(){
                        return this._items;
                    }
                },

                fetchAsync: function (onSuccess) {
                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        AJAX.Request.fetchAsync("countries", {}).then(
                            function complete(countries) {
                                for (var i = 0, length = countries.length ; i < length ; i++) {
                                    self._items.push(new CityAPI.Country({
                                        key: countries[i]
                                    }));
                                }

                                onSuccess(self);
                            }
                        );
                    });
                },
            },

            //static methods
            {
            }
        ),
    });
})();