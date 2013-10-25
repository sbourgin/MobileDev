(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a people object
        Person: WinJS.Class.define(

            //constructor
            function (data) {
                this.id = data.id;
                this.surname = data.surname;
                this.forename = data.forename;
                this.dob = data.dob;
                this.city = data.city;
                this.comment = data.comment;
            },

            //properties of the class
            {
                id: '',
                surname: '',
                forename: '',
                dob: '',
                city: '',
                comment: '',
            },

            //static methods
            {
            }
        )
    });
})();