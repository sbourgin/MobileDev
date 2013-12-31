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

                id: {
                    get: function () {
                        return this._messageComponent._id;
                    },
                },

                status: {
                    get: function () {
                        return this._messageComponent._status;
                    },
                },

                time: {
                    get: function () {
                        return this._messageComponent._time;
                    },
                },

                from: {
                    get: function () {
                        return this._messageComponent._from;
                    },
                },

                to: {
                    get: function () {
                        return this._messageComponent._to;
                    },
                },

                subject: {
                    get: function () {
                        return this._messageComponent._subject;
                    },
                },
            },

            //static methods
            {
            }
        )
    });
})();
