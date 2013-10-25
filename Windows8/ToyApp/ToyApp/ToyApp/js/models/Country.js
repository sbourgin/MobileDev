(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('CityAPI', {

        //creation of the class to manage a country object
        Country: WinJS.Class.define(

            //constructor
            function (data) {
                this.name = data.name;
            },

            //properties of the class
            {
                name: '',
            },

            //static methods
            {
            }
        ),
    });
})();