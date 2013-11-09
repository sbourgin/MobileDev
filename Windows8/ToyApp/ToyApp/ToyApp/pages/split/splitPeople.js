(function () {
    "use strict";

    var binding = WinJS.Binding;
    var nav = WinJS.Navigation;
    var ui = WinJS.UI;
    var utils = WinJS.Utilities;

    ui.Pages.define("/pages/split/splitPeople.html", {

        _itemSelectionIndex: -1,
        _wasSingleColumn: false,

        _citySelected: null,
        _peopleCollection: null,
        _peopleArray: null,
        _peopleList: null,

        _personSelected: null,

        // This function is called to initialize the page.
        init: function (element, options) {
            // Store information about the group and selection that this page will
            // display.

            this._citySelected = options.city;
            this._personSelected = options.personSelected ? options.personSelected : null;
            this._itemSelectionIndex = (options && "selectedIndex" in options) ? options.selectedIndex : -1;

            this._peopleArray = new WinJS.Binding.List();
            this._peopleCollection = new CityAPI.PeopleCollection({
                city: this._citySelected.id,
                items: this._peopleArray,
            });

            this.selectionChanged = ui.eventHandler(this._selectionChanged.bind(this));
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            this._peopleList = element.querySelector("#peopleList");

            //bind listView HTML element to our list
            this._peopleList.winControl.itemDataSource = this._peopleArray.dataSource;

            var self = this;
            this._peopleCollection.fetchAsync().then(
                function complete(collection) {
                    self._fetchCompleted(element);
                }
            );
        },

        _fetchCompleted: function (element) {
            //use a templating function to dynamically load elements
            this._peopleList.winControl.itemTemplate = Template.ListViewIncrementalTemplate.incrementalTemplate(this._peopleList.winControl.itemTemplate, this._peopleList, this._peopleCollection);

            element.querySelector("header[role=banner] .pagetitle").textContent = this._citySelected.city;

            this._updateVisibility(element);
            if (this._isSingleColumn()) {
                this._wasSingleColumn = true;
                if (this._itemSelectionIndex >= 0) {
                    //fetch corresponding city
                    var person = new CityAPI.Person({
                        id: this._personSelected.id,
                    });

                    var self = this;
                    person.fetchAsync().then(
                        function complete(person) {
                            // For single-column detail view, load the article.
                            binding.processAll(element.querySelector(".articlesection"), person);
                        }
                    );
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
            //if(this._peopleArray) this._peopleArray.dispose();
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
                        location: "/pages/split/splitPeople.html",
                        state: { city: this._citySelected }
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
                if (nav.canGoBack && nav.history.backStack[nav.history.backStack.length - 1].location === "/pages/split/splitPeople.html") {
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
                            nav.navigate("/pages/split/splitPeople.html", { city: this._citySelected, selectedIndex: this._itemSelectionIndex, personSelected: this._peopleArray.getAt(this._peopleList.winControl.currentItem.index) });
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
    });
})();
