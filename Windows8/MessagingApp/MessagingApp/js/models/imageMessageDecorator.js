(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a image message decorator
        ImageMessageDecorator: WinJS.Class.derive(Models.ContentDecorator,

            //constructor
            function (data) {
                //initialize properties
                if (data) {
                    this._messageComponent  = data.messageComponent ? data.messageComponent : "";
                    this._imageName         = data.imageName        ? data.imageName        : "";
                    this._imageFile         = data.imageFile        ? data.imageFile        : null;
                }
            },

            //properties of the class
            {
                _imageName: "",
                _imageFile: null,

                imageName: {
                    get: function () {
                        return this._imageName;
                    },
                },

                imageFile: {
                    get: function () {
                        return this._imageFile;
                    },
                },

                getSendParameters: function () {
                    var parameters = this._messageComponent.getSendParameters();

                    parameters.imageName = this._imageName;

                    return parameters;
                },

                getAllProperties: function () {
                    var parameters = this._messageComponent.getAllProperties();

                    parameters.imageName = this._imageName;
                    parameters.imageFile = this._imageFile;

                    return parameters;
                },
            },

            //static methods
            {
            }
        )
    });
})();
