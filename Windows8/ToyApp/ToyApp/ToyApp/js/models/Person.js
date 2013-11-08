(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a people object
        Person: WinJS.Class.define(

            //constructor
            function (data) {
                this._id = data.id;
                this._surname = data.surname;
                this._forename = data.forename;
                this._dob = data.dob;
                this._city = data.city;
                this._comment = data.comment;
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
            },

            //static methods
            {
            }
        )
    });
})();