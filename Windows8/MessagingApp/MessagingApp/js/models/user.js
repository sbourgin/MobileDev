(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a user object
        User: WinJS.Class.mix(WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._name                  = data.name                 ? data.name                 : "";
                    this._password              = data.password             ? data.password             : "";
                    this._description           = data.description          ? data.description          : "";
                    this._thumbnail             = data.thumbnail            ? data.thumbnail            : null;
                    this._unreadMessages        = data.unreadMessages       ? data.unreadMessages       : 0;
                }
            },

            //properties of the class
            {
                _name: "",
                _password: "",
                _description: "",
                _thumbnail: null,
                _unreadMessages: 0,
                _logged: false,
                _allReceivedMessages: 0,

                name: {
                    get: function () {
                        return this._name;
                    },
                },

                password: {
                    get: function () {
                        return this._password;
                    },
                },

                description: {
                    get: function () {
                        return this._description;
                    },
                },

                thumbnail: {
                    get: function () {
                        return this._thumbnail;
                    },
                    set: function (thumbnail) {
                        this._thumbnail = thumbnail;
                    }
                },

                unreadMessages: {
                    get: function () {
                        return this._unreadMessages;
                    },
                },

                allReceivedMessages: {
                    get: function () {
                        return this._allReceivedMessages;
                    }
                },

                logged: {
                    get: function () {
                        return this._logged;
                    }
                },

                getNormalisedName: function(){
                    return this._name.toLowerCase().replace(/\s/g, "");
                },

                logonAsync: function () {
                    var self = this;

                    var parameters = {
                        name: this._name,
                        password: this._password,
                    };

                    return new WinJS.Promise(function (onSuccess, onError) {
                        Utils.MessageAPI.getAsync("logon", parameters).then(
                            function complete(user) {
                                self._description = user.description;
                                self._logged = true;

                                //initialize all unread messages field
                                return self.getAllReceivedMessagesAsync();
                            },
                            onError
                        ).then(
                            function complete() {
                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                                self.refresh();
                            }
                        );
                    });
                },

                logoffAsync: function () {
                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("logoff", {}).then(
                            function complete() {
                                self._logged = false;

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error logging account off");
                            }
                        );
                    });
                },

                registerAsync: function () {
                    var parameters = {
                        name: this._name,
                        password: this._password,
                        description: this._description,
                    };

                    return new WinJS.Promise(function (onSuccess, onError) {
                        Utils.MessageAPI.getAsync("register", parameters).then(
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            onError
                        );
                    });
                },

                removeAsync: function () {
                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("remove", {}).then(
                            function complete(thumbnail) {
                                self.thumbnail = thumbnail;

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error removing account");
                            }
                        );
                    });
                },

                getThumbAsync: function () {
                    var parameters = {
                        name: this._name,
                    };

                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("getThumb", parameters).then(
                            function complete(thumbnail) {
                                var blob = new Blob([thumbnail], { "type": "image\/jpeg" });
                                var objectURL = window.URL.createObjectURL(blob);

                                self._thumbnail = objectURL;

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error getting thumbnail");
                            }
                        );
                    });
                },

                setThumbAsync: function () {
                    var formData = new FormData();
                    formData.append("image", this._thumbnail);

                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.postAsync("setThumb", formData).then(
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error setting thumbnail");
                            }
                        );
                    });
                },

                getUnreadMessagesAsync: function () {
                    var self = this;

                    var parameters = {
                        sent: 0,
                        name: this._name,
                        status: 0,
                        action: 0,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("list", parameters).then(
                            function complete(result) {
                                self._unreadMessages = result.count;

                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error getting unread messages");
                            }
                        );
                    });
                },

                getAllReceivedMessagesAsync: function () {
                    var self = this;

                    var parameters = {
                        sent: 0,
                        status: 2,
                        action: 0,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("list", parameters).then(
                            function complete(result) {
                                if (self._allReceivedMessages != result.count) {
                                    self._allReceivedMessages = result.count;

                                    //if new unread messages trigger a custom event
                                    self.dispatchEvent("newMessages");
                                }

                                onSuccess({
                                    model: self,
                                });
                            },
                            function error() {
                                console.log("Error getting all unread messages");
                            }
                        );
                    });
                },

                refresh: function () {
                    var self = this;

                    //if user logged, check every five seconds for new messages
                    if (this.logged) {
                        WinJS.Promise.timeout(5000).then(
                            function complete() {
                                return self.getAllReceivedMessagesAsync()
                            }
                        ).then(
                            function complete(returnObject) {
                                console.log(returnObject.model.allReceivedMessages);

                                self.refresh();
                            }
                        );
                    }
                },
            },

            //static methods
            {
            }
        ), WinJS.Utilities.eventMixin) //add the possibility for our class to trigger events
    });
})();
