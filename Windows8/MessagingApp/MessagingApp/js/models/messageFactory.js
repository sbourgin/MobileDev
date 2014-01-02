(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to define message factory
        MessageFactory: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
            },

            //static methods
            {
                createMessage: function (data) {
                    var message = new Models.EmptyMessage(data);

                    if (data.hasOwnProperty("text")) {
                        message = new Models.TextMessageDecorator({
                            messageComponent: message,
                            text: data.text,
                        });
                    }

                    if (data.hasOwnProperty("imageName")) {
                        var parameters = {
                            messageComponent: message,
                            imageName: data.imageName,
                        };

                        if (data.hasOwnProperty("imageFile")) {
                            parameters["imageFile"] = data.imageFile;
                        }

                        message = new Models.ImageMessageDecorator(parameters);
                    }

                    return message;
                },
            }
        )
    });
})();