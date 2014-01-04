(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a messages collection
        MessagesCollection: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._items = data.items ? data.items : [];
                }
            },

            //properties of the class
            {
                _items: [],

                items: {
                    get: function () {
                        return this._items;
                    }
                },

                fetchAsync: function (name) {
                    var self = this;

                    var parameters = {
                        name: name,
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

                readAllMessagesFrom: function (user) {
                    for (var i = 0 ; i < this.items.length ; i++) {
                        var message = this.items.getAt(i);

                        if (message.getAllProperties().from == user) {
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