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
                formatAMPM: function (time) {
                    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                    var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

                    var d = new Date(time);

                    var minutes = d.getMinutes().toString().length == 1 ? "0" + d.getMinutes() : d.getMinutes();
                    var hours = d.getHours().toString().length == 1 ? "0" + d.getHours() : d.getHours();
                    var ampm = d.getHours() >= 12 ? "pm" : "am";
                    
                    return days[d.getDay()] + " " + months[d.getMonth()] + " " + d.getDate() + " " + d.getFullYear() + " " + hours + ":" + minutes + ampm;
                },

                messageTemplate: function (userLogged) {
                    return function (itemPromise) {

                        return itemPromise.then(function (item) {
                            var properties = item.data.getAllProperties();

                            var div = document.createElement("div");
                            WinJS.Utilities.addClass(div, "messagestemplate");

                            var divBuble = document.createElement("div");

                            //unix timestamp are expressed in seconds, JavaScript works in miliseconds
                            var time = document.createElement("div");
                            time.className = "messageTime";
                            time.innerText = Utils.Template.formatAMPM(properties.time * 1000);

                            divBuble.appendChild(time);

                            if (properties.imageName) {
                                var imgMin = document.createElement("img");
                                imgMin.className = "imageMessageMin";

                                var imgBig = document.getElementById("imageMessageBig");

                                item.data.getImageAsync().then(
                                    function complete(returnObject) {
                                        var blob = new Blob([returnObject.imageFile], { "type": "image\/jpeg" });
                                        var objectURL = window.URL.createObjectURL(blob);
                                        imgMin.src = objectURL;

                                        imgBig.src = objectURL;

                                        imgMin.addEventListener("click", function () {
                                            document.getElementById("imageFlyout").winControl.show(this);
                                        });
                                    }
                                );

                                divBuble.appendChild(imgMin);
                            }

                            if (properties.text) {
                                var span = document.createElement("div");
                                span.innerText = properties.text;
                                divBuble.appendChild(span);
                            }

                            if (properties.from == userLogged) {
                                WinJS.Utilities.addClass(divBuble, "bubleRight");
                            }
                            else {
                                WinJS.Utilities.addClass(divBuble, "bubleLeft");
                            }

                            div.appendChild(divBuble);

                            return div;
                        });
                    };
                },
            }
        )
    });
})();