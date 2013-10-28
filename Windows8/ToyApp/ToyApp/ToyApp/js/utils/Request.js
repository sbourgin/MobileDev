(function () {
    'use strict';

    WinJS.Namespace.define('AJAX', {

        //creation of the class to manage a request object
        Request: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
                //'private' properties, read only access 
                _baseUrl: 'http://honey.computing.dcu.ie/city/',
                _successStatus: 'okay',
                _errorStatus: 'error',

                baseUrl: {
                    get: function () {
                        return this._baseUrl;
                    }
                },

                successStatus: {
                    get: function () {
                        return this._successStatus;
                    }
                },

                errorStatus: {
                    get: function () {
                        return this._errorStatus;
                    }
                },
            },

            //static methods
            {
                fetchAsync: function (endUrl, parameters, onSuccess) {
                    var ajaxRequest = new AJAX.Request();

                    var options = {};
                    var queryParameters = [];

                    //constructing query parameters
                    for (var key in parameters) {
                        queryParameters.push(encodeURIComponent(key) + "=" + encodeURIComponent(parameters[key]));
                    }
                    queryParameters = queryParameters.join("&");

                    //setting options
                    options.url = ajaxRequest.baseUrl + endUrl + '?' + queryParameters;
                    options.responseType = 'json';

                    WinJS.xhr(options).done(
                        function complete(request) {
                            if (request.status === 200) {
                                var response = JSON.parse(request.response);

                                if (response.status == ajaxRequest.errorStatus) {
                                    console.log('Request Error: ' + response.message);
                                }
                                else if (response.status == ajaxRequest.successStatus) {
                                    console.log('Success: ' + response.result);

                                    onSuccess(response.result);
                                }
                            }
                        },

                        function error(request) {
                            console.log('HTTP Error: ' + request.statusText);
                        }
                    );
                },
            }
        )
    });
})();