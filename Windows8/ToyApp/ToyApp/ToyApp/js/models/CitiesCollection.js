(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a cities collection
        CitiesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._country   = data.country  ? data.country  : '';
                    this._items     = data.items    ? data.items    : [];
                }
            },

            //properties of the class
            {
                _maxItems: 100,

                _country: '',

                _items: [],

                maxItems: {
                    get: function(){
                        return this._maxItems;
                    }
                },

                country: {
                    get: function(){
                        return this._country;
                    }
                },

                items: {
                    get: function () {
                        return this._items;
                    },
                },

                fetchAsync: function (id, reverse) {
                    var self = this;

                    var parameters = {
                        id: id,
                        cn: this._country,
                        reverse: reverse,
                    };

                    return new WinJS.Promise(function(onSuccess){
                        AJAX.Request.fetchAsync("cities", parameters).then(
                            function complete(cities) {
                                var length = cities.length;
                                var nbElementRemoved = 0;

                                if (reverse) {
                                    for (var i = length - 1 ; i >= 0 ; i--) {
                                        self._items.unshift(new CityAPI.City(cities[i]));
                                    }

                                    if (self._items.length > self._maxItems) {
                                        self._items.splice((-1 * length), length);

                                        nbElementRemoved = length;
                                    }
                                }
                                else {
                                    for (var i = 0 ; i < length ; i++) {
                                        self._items.push(new CityAPI.City(cities[i]));
                                    }

                                    if (self._items.length > self._maxItems) {
                                        self._items.splice(0, length);

                                        nbElementRemoved = length;

                                    }
                                }
                                
                                onSuccess({
                                    collection: self,
                                    nbElementAdded: length,
                                    nbElementRemoved: nbElementRemoved,
                                });
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