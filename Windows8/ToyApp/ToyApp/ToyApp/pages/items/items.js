(function () {
    "use strict";

    var ui = WinJS.UI;

    var countriesCollection = null;
    var countriesArray = new WinJS.Binding.List();

    ui.Pages.define("/pages/items/items.html", {
        // This function is called to initialize the page.
        init: function (element, options) {
            this.itemInvoked = ui.eventHandler(this._itemInvoked.bind(this));

            //if we didn't get the data yet, make the async call
            if (countriesCollection == null) {
                countriesCollection = new CityAPI.CountriesCollection();

                countriesCollection.fetchAsync().then(
                    function complete(collection) {
                        collection.forEach(function (country) {
                            countriesArray.push({
                                key: country.key,
                                title: country.key,
                                subtitle: country.name,
                                backgroundImage: country.key + ".png",
                                description: country.name + "description"
                            })
                        });
                    }
                );
            }
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            //get the HTML countries list element when DOM is ready
            var contriesList = document.getElementById("countriesList");
            
            countriesList.winControl.itemDataSource = countriesArray.dataSource;
        },

        // This function updates the page layout in response to layout changes.
        updateLayout: function (element) {
            /// <param name="element" domElement="true" />

            // TODO: Respond to changes in layout.
        },

        _itemInvoked: function (args) {
            var groupKey = Data.groups.getAt(args.detail.itemIndex).key;
            WinJS.Navigation.navigate("/pages/split/split.html", { groupKey: groupKey });
        }
    });
})();
