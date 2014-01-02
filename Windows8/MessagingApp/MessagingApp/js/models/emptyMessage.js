(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a empty message object
        EmptyMessage: WinJS.Class.derive(Models.Message,

            //constructor
            function (data) {
                //call super class constructor to access its properties
                Models.Message.prototype.constructor.call(this);

                //initialize properties
                if (data) {
                    this._id        = data.id       ? data.id       : "";
                    this._status    = data.status   ? data.status   : -1;
                    this._time      = data.time     ? data.time     : -1;
                    this._from      = data.from     ? data.from     : "";
                    this._to        = data.to       ? data.to       : "";
                    this._subject   = data.subject  ? data.subject  : "";
                }
            },

            //properties of the class
            {
            },

            //static methods
            {
            }
        )
    });
})();