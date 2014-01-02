(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage content decorator
        ContentDecorator: WinJS.Class.derive(Models.Message,

            //constructor
            function () {
            },

            //properties of the class
            {
                _messageComponent: null,

                messageComponent: {
                    get: function () {
                        return this._messageComponent;
                    },
                },
            },

            //static methods
            {
            }
        )
    });
})();
