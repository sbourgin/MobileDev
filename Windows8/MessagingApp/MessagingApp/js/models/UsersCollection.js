(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a users collection
        UsersCollection: WinJS.Class.define(

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

                fetchAsync: function (onSuccess) {
                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("users", {}).then(
                            function complete(users) {
                                for (var i = 0, length = users.length ; i < length ; i++) {
                                    self._items.push(new Models.User(users[i]));
                                }

                                onSuccess({
                                    collection: self,
                                });
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