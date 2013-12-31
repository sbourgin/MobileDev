(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a image message decorator
        ImageMessageDecorator: WinJS.Class.derive(Models.ContentDecorator,

            //constructor
            function (data) {
                //call super class constructor to access its properties
                Models.ContentDecorator.prototype.constructor.call(this);

                //initialize properties
                if (data) {
                    this._messageComponent  = data.messageComponent ? data.messageComponent : "";
                    this._image             = data.image            ? data.image            : "";
                }
            },

            //properties of the class
            {
                _image: "",

                image: {
                    get: function () {
                        return this._image;
                    },
                },

                getSendParameters: function () {
                    var parameters = this._messageComponent.getSendParameters();

                    parameters.image = this._image;

                    return parameters;
                },

                getImageAsync: function () {

                },
            },

            //static methods
            {
            }
        )
    });
})();
