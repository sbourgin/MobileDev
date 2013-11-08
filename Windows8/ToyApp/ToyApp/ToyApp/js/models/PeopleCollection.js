(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a people collection
        PeopleCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._city  = data.city     ? data.city     : '';
                    this._items = data.items    ? data.items    : [];
                }
            },

            //properties of the class
            {
                _maxItems: 100,

                _city: '',

                _items: [],

                maxItems: {
                    get: function () {
                        return this._maxItems;
                    }
                },

                cityy: {
                    get: function () {
                        return this._city;
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
                        city: this._city,
                        reverse: reverse,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        AJAX.Request.fetchAsync("people", parameters).then(
                            function complete(people) {
                                var length = people.length;

                                if (reverse) {
                                    for (var i = length - 1 ; i >= 0 ; i--) {
                                        self._items.unshift(new CityAPI.Person(people[i]));
                                    }

                                    if (self._items.length > self._maxItems) {
                                        self._items.splice((-1 * length), length);
                                    }
                                }
                                else {
                                    for (var i = 0 ; i < length ; i++) {
                                        self._items.push(new CityAPI.Person(people[i]));
                                    }

                                    if (self._items.length > self._maxItems) {
                                        self._items.splice(0, length);
                                    }
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