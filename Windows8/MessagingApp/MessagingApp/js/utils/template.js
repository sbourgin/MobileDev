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

                userTemplate: function () {
                    return function (itemPromise) {

                        return itemPromise.then(function (item) {
                            var div = document.createElement("div");
                            div.className = "userstemplate";

                            var itemDiv = document.createElement("div");
                            itemDiv.className = "item";

                            div.appendChild(itemDiv);

                            var img = document.createElement("img");
                            img.className = "item-image";
                            img.src = "images/thumbnail.png";

                            item.data.getThumbAsync().then(
                                function complete(returnObject) {
                                    img.src = returnObject.model.thumbnail;
                                }
                            );

                            itemDiv.appendChild(img);

                            var itemInfo = document.createElement("div");
                            itemInfo.className = "item-info";

                            itemDiv.appendChild(itemInfo);

                            var h3 = document.createElement("h3");
                            h3.className = "item-title win-type-ellipsis";
                            h3.textContent = item.data.name;

                            itemInfo.appendChild(h3);

                            var h6 = document.createElement("h6");
                            h6.className = "item-subtitle win-type-ellipsis";
                            h6.textContent = item.data.description;

                            item.data.getUnreadMessagesAsync().then(
                                function complete(){
                                    if (item.data.unreadMessages) {
                                        h6.className += " unreadMessages";
                                        h6.textContent = "Unread messages: " + item.data.unreadMessages;
                                    }
                                }
                            );

                            itemInfo.appendChild(h6);

                            return div;
                        });
                    };
                },

                messageTemplate: function (userLogged, onMessageRemove, listview, nbItems) {
                    return function (itemPromise) {

                        return itemPromise.then(function (item) {
                            var properties = item.data.getAllProperties();

                            var div = document.createElement("div");
                            div.className = "messagestemplate";

                            var divBuble = document.createElement("div");

                            //unix timestamp are expressed in seconds, JavaScript works in miliseconds
                            var header = document.createElement("div");
                            header.className = "messageTime";
                            header.innerText = Utils.Template.formatAMPM(properties.time * 1000);

                            divBuble.appendChild(header);

                            if (properties.imageName) {
                                var imgMin = document.createElement("img");
                                imgMin.className = "imageMessageMin";

                                var imgBig = document.getElementById("imageMessageBig");

                                item.data.getImageAsync().then(
                                    function complete(returnObject) {
                                        imgMin.src = returnObject.model.imageFile;

                                        imgMin.addEventListener("click", function () {
                                            imgBig.src = returnObject.model.imageFile;
                                            document.getElementById("imageFlyout").winControl.show(this);
                                        });

                                        msSetImmediate(function () {
                                            listview.winControl.ensureVisible(nbItems - 1);
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

                                if (properties.status == 1) {
                                    var remove = document.createElement("a");
                                    remove.className = "removeMessage";
                                    remove.innerText = 'x';

                                    remove.addEventListener("click", function () {
                                        item.data.removeAsync().then(
                                            function complete() {
                                                onMessageRemove(item);
                                            }
                                        );
                                    });

                                    divBuble.appendChild(remove);
                                }                                
                            }
                            else {
                                WinJS.Utilities.addClass(divBuble, "bubleLeft");
                            }

                            div.appendChild(divBuble);

                            if (item.index == nbItems - 1) {
                                msSetImmediate(function () {
                                    listview.winControl.ensureVisible(nbItems - 1);
                                });
                            }

                            return div;
                        });
                    };
                },
            }
        )
    });
})();