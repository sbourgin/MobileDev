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

                sendAsync: function () {
                    var parameters = this.getSendParameters();
                    var properties = this.getAllProperties();

                    var formData = new FormData();
                    formData.append("message", JSON.stringify(this.getSendParameters()));

                    if (properties.imageFile) {
                        formData.append("image", properties.imageFile);
                    }

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.postAsync("send", formData).then(
                            function complete(message) {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            }
                        );
                    });
                },

                readAsync: function () {
                    var properties = this.getAllProperties();

                    var parameters = {
                        id: properties.id,
                    };

                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("read", parameters).then(
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            }
                        );
                    });
                },

                removeAsync: function () {
                    var properties = this.getAllProperties();

                    var parameters = {
                        id: properties.id,
                    };

                    var self = this;

                    return new WinJS.Promise(function (onSuccess) {
                        Utils.MessageAPI.getAsync("rem", parameters).then(
                            function complete() {

                                //execute callback
                                onSuccess({
                                    model: self,
                                });
                            }
                        );
                    });
                },

                getImageAsync: function () {
                    var properties = this.getAllProperties();

                    if (properties.imageName) {
                        var parameters = {
                            id: properties.id
                        };

                        var self = this;

                        return new WinJS.Promise(function (onSuccess) {
                            Utils.MessageAPI.getAsync("img", parameters).then(
                                function complete(image) {
                                    var blob = new Blob([image], { "type": "image\/jpeg" });
                                    var objectURL = window.URL.createObjectURL(blob);

                                    self._imageFile = objectURL;

                                    //execute callback
                                    onSuccess({
                                        model: self,
                                    });
                                }
                            );
                        });
                    }
                },

                //abstract method that should be implemented in subclasses
                getSendParameters: function(){
                    return {
                        from:       this._from,
                        to:         this._to,
                        subject:    this._subject,
                    };
                },

                getAllProperties: function () {
                    return {
                        id:         this._id,
                        status:     this._status,
                        time:       this._time,
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