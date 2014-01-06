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
                            }
                        );
                    });
                },

                readAllMessagesFrom: function () {
                    for (var i = 0 ; i < this.items.length ; i++) {
                        var message = this.items.getAt(i);

                        if (message.getAllProperties().from == this._receiver) {
                            message.readAsync();
                        }
                    }
                },

                getNbNewMessagesFromAsync: function(){
                    var self = this;

                    var parameters = {
                        sent: 0,
                        name: this._receiver,
                        status: 0,
                        action: 0,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("list", parameters).then(
                            function complete(result) {

                                onSuccess({
                                    collection: self,
                                    count: result.count,
                                });
                            }
                        );
                    });
                },

                refreshAsync: function () {
                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        WinJS.Promise.timeout(5000).then(
                            function (complete) {
                                if (! self._stopRefresh) {
                                    return self.getNbNewMessagesFromAsync();
                                }
                            }
                        ).then(
                            function complete(returnObject) {
                                if (returnObject) {
                                    console.log("refresh for " + self_receiver + "! count: " + returnObject.count);

                                    if (returnObject.count > 0) {
                                        onSuccess({
                                            collection: self,
                                        });
                                    }


                                    self.refreshAsync();
                                }
                            }
                        );
                    });
                },
            },

            //static methods
            {
            }
        ),
    });
})();