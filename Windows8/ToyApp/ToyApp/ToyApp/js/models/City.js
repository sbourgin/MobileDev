(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a city object
        City: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._id         = data.id           ? data.id           : '';
                    this._country    = data.country      ? data.country      : '';
                    this._region     = data.region       ? data.region       : '';
                    this._city       = data.city         ? data.city         : '';
                    this._latitude   = data.latitude     ? data.latitude     : '';
                    this._longitude  = data.longitude    ? data.longitude    : '';
                    this._comment    = data.comment      ? data.comment      : '';
                }
            },

            //properties of the class
            {
                _id: '', 
                _country: '', 
                _region: '', 
                _city: '', 
                _latitude: '', 
                _longitude: '', 
                _comment: '',
                _picture: 'images/city.png',

                id: {
                    get: function () {
                        return this._id;
                    },
                },

                country: {
                    get: function () {
                        return this._country;
                    },
                },

                region: {
                    get: function () {
                        return this._region;
                    },
                },

                city: {
                    get: function () {
                        return this._city;
                    },
                },

                latitude: {
                    get: function () {
                        return this._latitude;
                    },
                },

                longitude: {
                    get: function () {
                        return this._longitude;
                    },
                },

                comment: {
                    get: function () {
                        return this._comment;
                    },
                },

                picture: {
                    get: function () {
                        return this._picture;
                    },
                },

                fetchAsync: function () {
                    var self = this;

                    var parameters = {
                        id: this._id,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        AJAX.Request.fetchAsync("city", parameters).then(
                            function complete(city) {
                                self._country   = city.country;
                                self._region    = city.region;
                                self._city      = city.city;
                                self._latitude  = city.latitude;
                                self._longitude = city.longitude;
                                self._comment   = city.comment;

                                onSuccess(self);
                            }
                        );
                    });
                },
            },

            //static methods
            {
            }
        )
    });
})();