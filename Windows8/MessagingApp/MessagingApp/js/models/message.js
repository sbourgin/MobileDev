(function () {
    'use strict';

    //creation of namespace    
    WinJS.Namespace.define('Models', {

        //creation of the class to define message component
        Message: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
                _id: "",
                _status: -1,
                _time: -1,
                _from: "",
                _to: "",
                _subject: "",

                id: {
                    get: function () {
                        return this._id;
                    },
                },

                status: {
                    get: function () {
                        return this._status;
                    },
                },

                time: {
                    get: function () {
                        return this._time;
                    },
                },

                from: {
                    get: function () {
                        return this._from;
                    },
                },

                to: {
                    get: function () {
                        return this._to;
                    },
                },

                subject: {
                    get: function () {
                        return this._subject;
                    },
                },

                fetchAsync: function () {
                    console.log("TODO");
                },

                sendAsync: function (onSuccess) {
                    var self = this;

                    var formData = new FormData();
                    //formData.append("image", MSApp.createFileFromStorageFile(file));
                    formData.append("message", JSON.stringify(self.getSendParameters()));

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.postAsync("send", formData).then(
                            function complete(message) {

                                //execute callback
                                onSuccess({
                                    message: self,
                                });
                            }
                        );
                    });
                },

                readAsync: function () {

                },

                removeAsync: function () {

                },

                //abstract method that should be implemented in subclasses
                getSendParameters: function(){
                    return {
                        from:       this._from,
                        to:         this._to,
                        subject:    this._subject,
                    };
                },
            },

            //static methods
            {
            }
        )
    });
})();