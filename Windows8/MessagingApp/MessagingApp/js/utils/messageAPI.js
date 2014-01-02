(function () {
    'use strict';

    WinJS.Namespace.define('Utils', {

        //creation of the class to manage interactions with the API
        MessageAPI: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
            },

            //static methods
            {
                //'private' properties, read only access 
                _baseUrl: 'http://honey.computing.dcu.ie/mmsg/',
                _successStatus: 'okay',
                _errorStatus: 'error',

                //associate each request with the right field provided in the response
                _responseField: {
                    "logon": "user",
                    "users": "users",
                    "conversation": "result",
                },

                //private methods to do get request
                getAsync: function (endUrl, parameters) {
                    var options = {};
                    var queryParameters = [];

                    //constructing query parameters
                    for (var key in parameters) {
                        queryParameters.push(encodeURIComponent(key) + "=" + encodeURIComponent(parameters[key]));
                    }
                    queryParameters = queryParameters.join("&");

                    //setting options
                    options.url = Utils.MessageAPI._baseUrl + endUrl + "?" + queryParameters;

                    if (endUrl == "img" || endUrl == "getThumb") {
                        options.responseType = "arraybuffer";
                    }
                    else {
                        options.responseType = "json";
                    }

                    return new WinJS.Promise(function (onSuccess, onError) {
                        WinJS.xhr(options).then(
                            function complete(request) {
                                if (request.status === 200) {
                                    try{
                                        var response = JSON.parse(request.response);
                                    }
                                    catch (e) {
                                        if (endUrl == "img" || endUrl == "getThumb") {
                                            onSuccess(request.response);
                                            return;
                                        }

                                        console.log("error parsing JSON: " + e)
                                    }

                                    if (response.status == Utils.MessageAPI._errorStatus) {
                                        //console.log('Request Error: ' + response.message);

                                        onError(response.message);
                                    }
                                    else if (response.status == Utils.MessageAPI._successStatus) {
                                        //console.log('Success: ' + response);

                                        onSuccess(response[Utils.MessageAPI._responseField[endUrl]]);
                                    }
                                }
                            },

                            function error(request) {
                                console.log('HTTP Error: ' + request.statusText);                                
                            }
                        );
                    });
                },

                postAsync: function (endUrl, formData) {
                    var options = {
                        url: Utils.MessageAPI._baseUrl + endUrl,
                        type: "POST",
                        data: formData,
                    };

                    return new WinJS.Promise(function (onSuccess) {
                        WinJS.xhr(options).then(
                            function complete(request) {
                                if (request.status === 200) {
                                    var response = JSON.parse(request.response);

                                    if (response.status == Utils.MessageAPI._errorStatus) {
                                        console.log('Request Error: ' + response.message);
                                    }
                                    else if (response.status == Utils.MessageAPI._successStatus) {
                                        //console.log('Success: ' + response);

                                        onSuccess();
                                    }
                                }
                            },

                            function error(request) {
                                console.log('HTTP Error: ' + request.statusText);
                            }
                        );
                    });
                },
            }
        )
    });
})();