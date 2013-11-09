(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a people object
        Person: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._id        = data.id       ? data.id       : '';
                    this._surname   = data.surname  ? data.surname  : '';
                    this._forename  = data.forename ? data.forename : '';
                    this._dob       = data.dob      ? data.dob      : '';
                    this._city      = data.city     ? data.city     : '';
                    this._comment   = data.comment  ? data.comment  : '';
                }
            },

            //properties of the class
            {
                _id: '',
                _surname: '',
                _forename: '',
                _dob: '',
                _city: '',
                _comment: '',
                _picture: 'images/person.png',

                id: {
                    get: function () {
                        return this._id;
                    },
                },

                surname: {
                    get: function () {
                        return this._surname;
                    },
                },

                forename: {
                    get: function () {
                        return this._forename;
                    },
                },

                dob: {
                    get: function () {
                        return this._dob;
                    },
                },

                city: {
                    get: function () {
                        return this._city;
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
                        AJAX.Request.fetchAsync("person", parameters).then(
                            function complete(person) {
                                self._surname   = person.surname;
                                self._forename  = person.forename;
                                self._dob       = person.dob;
                                self._city      = person.city;
                                self._comment   = person.comment;

                                onSuccess({
                                    person: self
                                });
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