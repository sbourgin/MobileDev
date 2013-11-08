(function () {
    "use strict";

    var ui = WinJS.UI;

    ui.Pages.define("/pages/items/items.html", {

        _countriesCollection: null,
        _countriesArray: null,

        // This function is called to initialize the page.
        init: function (element, options) {
            this._countriesArray = new WinJS.Binding.List();
            this._countriesCollection = new CityAPI.CountriesCollection({
                items: this._countriesArray,
            });

            this.itemInvoked = ui.eventHandler(this._itemInvoked.bind(this));
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            //bind listView HTML element to our list
            element.querySelector("#countriesList").winControl.itemDataSource = this._countriesArray.dataSource;

            this._countriesCollection.fetchAsync();            
        },

        // This function updates the page layout in response to layout changes.
        updateLayout: function (element) {
            /// <param name="element" domElement="true" />

            // TODO: Respond to changes in layout.
        },

        _itemInvoked: function (args) {
            var country = this._countriesCollection.items.getAt(args.detail.itemIndex);

            WinJS.Navigation.navigate("/pages/split/splitCity.html", { country: country });
        }
    });
})();
