(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to manage a city object
        User: WinJS.Class.define(

            //constructor
            function (data) {
                if (data) {
                    this._name          = data.name         ? data.name         : "";
                    this._password      = data.password     ? data.password     : "";
                    this._description   = data.description  ? data.description  : "";
                    this._thumbnail     = data.thumbnail    ? data.thumbnail    : "images/thumbnail.png";
                }
            },

            //properties of the class
            {
                _name: "",
                _password: "",
                _description: "",
                _thumbnail: "",

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

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            },
                            onError
                        );
                    });
                },

                logoffAsync: function () {
                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("logoff", {}).then(
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
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
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            }
                        );
                    });
                },
            },

            //static methods
            {
            }
        )
    });
})();