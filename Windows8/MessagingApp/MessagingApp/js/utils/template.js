(function () {
    'use strict';

    WinJS.Namespace.define('Utils', {

        //creation of the class to manage templates used for ListView
        Template: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
            },

            //static methods
            {
                messageTemplate: function (userLogged) {
                    return function (itemPromise) {

                        return itemPromise.then(function (item) {
                            var div = document.createElement("div");
                            WinJS.Utilities.addClass(div, "messagestemplate");

                            var divBuble = document.createElement("div");
                            divBuble.innerText = item.data.text;

                            if (item.data.from == userLogged) {
                                WinJS.Utilities.addClass(divBuble, "bubleRight");
                            }
                            else {
                                WinJS.Utilities.addClass(divBuble, "bubleLeft");
                            }

                            div.appendChild(divBuble);

                            return div;
                        })
                    }
                },
            }
        )
    });
})();