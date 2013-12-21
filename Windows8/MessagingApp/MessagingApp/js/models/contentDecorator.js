(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a empty message object
        ContentDecorator: WinJS.Class.derive(Models.Message,

            //constructor
            function () {
            },

            //properties of the class
            {
                _messageComponent: null,
            },

            //static methods
            {
            }
        )
    });
})();