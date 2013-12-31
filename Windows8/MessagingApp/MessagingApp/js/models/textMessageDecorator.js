(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a text message decorator
        TextMessageDecorator: WinJS.Class.derive(Models.ContentDecorator,

            //constructor
            function (data) {
                //call super class constructor to access its properties
                Models.ContentDecorator.prototype.constructor.call(this);

                //initialize properties
                if (data) {
                    this._messageComponent  = data.messageComponent ? data.messageComponent : "";
                    this._text              = data.text             ? data.text             : "";
                }
            },

            //properties of the class
            {
                _text: "",

                text: {
                    get: function () {
                        return this._text;
                    },
                },

                getSendParameters: function () {
                    var parameters = this._messageComponent.getSendParameters();

                    parameters.text = this._text;

                    return parameters;
                },
            },

            //static methods
            {
            }
        )
    });
})();
