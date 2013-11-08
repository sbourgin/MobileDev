(function () {
    "use strict";

    var binding = WinJS.Binding;
    var nav = WinJS.Navigation;
    var ui = WinJS.UI;
    var utils = WinJS.Utilities;

    ui.Pages.define("/pages/split/splitCity.html", {

        _itemSelectionIndex: -1,
        _wasSingleColumn: false,

        _countrySelected: null,
        _citiesCollection: null,
        _citiesArray: null,
        _citiesList: null,

        // This function is called to initialize the page.
        init: function (element, options) {
            // Store information about the group and selection that this page will
            // display.

            this._countrySelected = options.country;
            this._itemSelectionIndex = (options && "selectedIndex" in options) ? options.selectedIndex : -1;

            this._citiesArray = new binding.List();
            this._citiesCollection = new CityAPI.CitiesCollection({
                country: this._countrySelected.key,
                items: this._citiesArray,
            });

            this.selectionChanged = ui.eventHandler(this._selectionChanged.bind(this));
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            //handle click on people button to redirect to the new page
            element.querySelector(".people-button").addEventListener("click", this._navigateToPeoplePage.bind(this));

            this._citiesList = element.querySelector("#citiesList");

            //bind listView HTML element to our list
            this._citiesList.winControl.itemDataSource = this._citiesArray.dataSource;

            var self = this;
            this._citiesCollection.fetchAsync().then(
                function complete(collection) {
                    self._citiesList.winControl.selection.set(0);
                }
            );

            //use a templating function to dynamically load elements
            this._citiesList.winControl.itemTemplate = Template.ListViewIncrementalTemplate.incrementalTemplate(this._citiesList.winControl.itemTemplate, this._citiesList, this._citiesCollection);

            element.querySelector("header[role=banner] .pagetitle").textContent = this._countrySelected.key;

            this._updateVisibility(element);
            if (this._isSingleColumn()) {
                this._wasSingleColumn = true;
                if (this._itemSelectionIndex >= 0) {
                    // For single-column detail view, load the article.
                    binding.processAll(element.querySelector(".articlesection"), this._citiesArray.getAt(this._itemSelectionIndex));
                }
            } else {
                // If this page has a selectionIndex, make that selection
                // appear in the ListView.
                var listView = element.querySelector(".itemlist").winControl;
                listView.selection.set(Math.max(this._itemSelectionIndex, 0));
            }
        },

        unload: function () {
            //this._items.dispose();
            //if(this._citiesArray) this._citiesArray.dispose();
        },

        updateLayout: function (element) {
            var isSingleColumn = this._isSingleColumn();
            if (this._wasSingleColumn === isSingleColumn) {
                return;
            }

            var listView = element.querySelector(".itemlist").winControl;
            var firstVisible = listView.indexOfFirstVisible;
            this._updateVisibility(element);

            var handler = function (e) {
                listView.removeEventListener("contentanimating", handler, false);
                e.preventDefault();
            }

            if (isSingleColumn) {
                listView.selection.clear();
                if (this._itemSelectionIndex >= 0) {
                    // If the app has snapped into a single-column detail view,
                    // add the single-column list view to the backstack.
                    nav.history.current.state = {
                        country: this._countrySelected,
                        selectedIndex: this._itemSelectionIndex
                    };
                    nav.history.backStack.push({
                        location: "/pages/split/split.html",
                        state: { country: this._countrySelected }
                    });
                    element.querySelector(".articlesection").focus();
                } else {
                    listView.addEventListener("contentanimating", handler, false);
                    if (firstVisible >= 0 && listView.itemDataSource.list.length > 0) {
                        listView.indexOfFirstVisible = firstVisible;
                    }
                    listView.forceLayout();
                }
            } else {
                // If the app has unsnapped into the two-column view, remove any
                // splitPage instances that got added to the backstack.
                if (nav.canGoBack && nav.history.backStack[nav.history.backStack.length - 1].location === "/pages/split/splitCity.html") {
                    nav.history.backStack.pop();
                }

                listView.addEventListener("contentanimating", handler, false);
                if (firstVisible >= 0 && listView.itemDataSource.list.length > 0) {
                    listView.indexOfFirstVisible = firstVisible;
                }
                listView.forceLayout();
                listView.selection.set(this._itemSelectionIndex >= 0 ? this._itemSelectionIndex : Math.max(firstVisible, 0));
            }

            this._wasSingleColumn = isSingleColumn;
        },

        // This function checks if the list and details columns should be displayed
        // on separate pages instead of side-by-side.
        _isSingleColumn: function () {
            return document.documentElement.offsetWidth <= 767;
        },

        _selectionChanged: function (args) {
            var listView = args.currentTarget.winControl;
            var details;
            // By default, the selection is restriced to a single item.
            listView.selection.getItems().done(function updateDetails(items) {
                if (items.length > 0) {
                    this._itemSelectionIndex = items[0].index;
                    if (this._isSingleColumn()) {
                        // If snapped or portrait, navigate to a new page containing the
                        // selected item's details.
                        setImmediate(function () {
                            nav.navigate("/pages/split/splitCity.html", { country: this._countrySelected, selectedIndex: this._itemSelectionIndex });
                        }.bind(this));
                    } else {
                        // If fullscreen or filled, update the details column with new data.
                        details = document.querySelector(".articlesection");
                        binding.processAll(details, items[0].data);
                        details.scrollTop = 0;
                    }
                }
            }.bind(this));
        },

        // This function toggles visibility of the two columns based on the current
        // view state and item selection.
        _updateVisibility: function (element) {
            var splitPage = element.querySelector(".splitpage");
            if (this._isSingleColumn()) {
                if (this._itemSelectionIndex >= 0) {
                    utils.addClass(splitPage, "itemdetail");
                    element.querySelector(".articlesection").focus();
                } else {
                    utils.addClass(splitPage, "groupdetail");
                    element.querySelector(".itemlist").focus();
                }
            } else {
                utils.removeClass(splitPage, "groupdetail");
                utils.removeClass(splitPage, "itemdetail");
                element.querySelector(".itemlist").focus();
            }
        },

        _navigateToPeoplePage: function (mouseEvent) {
            var currentItem = this._citiesList.winControl.currentItem;

            var currentCity = this._citiesCollection.items.getItem(currentItem.index).data;

            nav.navigate("/pages/split/splitPeople.html", { city: currentCity });
        },
    });
})();
