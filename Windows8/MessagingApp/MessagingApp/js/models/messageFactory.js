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

                    if (data.hasOwnProperty("image")) {
                        message = new Models.ImageMessageDecorator({
                            messageComponent: message,
                            image: data.image,
                        });
                    }

                    return message;
                },
            }
        )
    });
})();