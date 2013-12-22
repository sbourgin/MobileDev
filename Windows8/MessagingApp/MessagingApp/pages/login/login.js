(function () {
    "use strict";

    var ui = WinJS.UI;
    var nav = WinJS.Navigation;

    ui.Pages.define("/pages/login/login.html", {

        _mandatoryFields: null,
        _submitButton: null,

        // This function is called to initialize the page.
        init: function (element, options) {
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            this._submitButton = element.querySelector("#login_button");
            element.querySelector("#register_link").addEventListener("click", this._navigateToRegisterPage.bind(this));

            this._mandatoryFields = element.querySelectorAll(".mandatory_fields");
              
            for (var i = 0 ; i < this._mandatoryFields.length ; i++) {
                this._mandatoryFields[i].addEventListener("keyup", this._validateForm.bind(this));
            }

            element.querySelector("#form_login").addEventListener("submit", this._submitLogin.bind(this));
        },

        _validateForm: function (event) {
            var valid = true;

            for (var i = 0 ; i < this._mandatoryFields.length ; i++) {
                if (this._mandatoryFields[i].value.length == 0) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                this._submitButton.disabled = false;
            }
            else {
                this._submitButton.disabled = true;
            }
        },

        _submitLogin: function (event) {
            event.preventDefault();

            var user = new Models.User({
                name: this._mandatoryFields[0].value,
                password: this._mandatoryFields[1].value,
            });

            user.logonAsync().then(
                function complete(returnObject) {
                    nav.navigate("/pages/split/split.html", {user: user});
                },
                function error(errorMessage) {
                    document.getElementById("error_message").innerText = errorMessage;
                }
            );
        },

        _navigateToRegisterPage: function (event) {
            nav.navigate("/pages/register/register.html");
        },
    });
})();
