(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a city object
        City: WinJS.Class.define(

            //constructor
            function (data) {
                this.id = data.id;
                this.country = data.country;
                this.region = data.region;
                this.city = data.city;
                this.latitude = data.latitude;
                this.longitude = data.longitude;
                this.comment = data.comment;
            },

            //properties of the class
            {
                id: '', 
                country: '', 
                region: '', 
                city: '', 
                latitude: '', 
                longitude: '', 
                comment: '',
            },

            

            //static methods
            {
            }
        )
    });
})();