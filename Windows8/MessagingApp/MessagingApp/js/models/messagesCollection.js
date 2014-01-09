(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a messages collection
        MessagesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._items     = data.items    ? data.items    : [];
                    this._receiver  = data.receiver ? data.receiver : "";
                }
            },

            //properties of the class
            {
                _items: [],
                _receiver: "",

                items: {
                    get: function () {
                        return this._items;
                    }
                },

                receiver: {
                    get: function () {
                        return this._receiver;
                    }
                },

                fetchAsync: function () {
                    var self = this;

                    var parameters = {
                        name: this._receiver,
                        action: 1,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("conversation", parameters).then(
                            function complete(messages) {
                                for (var i = 0, length = messages.length ; i < length ; i++) {
                                    self._items.push(Models.MessageFactory.createMessage(messages[i]));
                                }

                                onSuccess({
                                    collection: self,
                                });
                            },
                            function error() {
                                console.log("Error fetching messages collection");
                            }
                        );
                    });
                },

                getNbMessagesAsync: function(){
                    var self = this;

                    var parameters = {
                        name: this._receiver,
                        action: 0,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("conversation", parameters).then(
                            function complete(result) {

                                onSuccess({
                                    collection: self,
                                    nbMessages: result.count,
                                });
                            },
                            function error() {
                                console.log("Error counting messages collection");
                            }
                        );
                    });
                },

                readAllMessages: function () {
                    for (var i = 0 ; i < this.items.length ; i++) {
                        var message = this.items.getAt(i);

                        if (message.getAllProperties().from == this._receiver) {
                            message.readAsync();
                        }
                    }
                },
            },

            //static methods
            {
            }
        ),
    });
})();