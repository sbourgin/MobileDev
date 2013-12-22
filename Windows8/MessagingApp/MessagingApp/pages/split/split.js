(function () {
    "use strict";

    var binding = WinJS.Binding;
    var nav = WinJS.Navigation;
    var ui = WinJS.UI;
    var utils = WinJS.Utilities;

    ui.Pages.define("/pages/split/split.html", {

        _itemSelectionIndex: -1,
        _wasSingleColumn: false,

        _userLogged: null,
        _usersCollection: null,
        _usersArray: null,
        _usersList: null,

        // This function is called to initialize the page.
        init: function (element, options) {
            this._userLogged = options.user;

            this._itemSelectionIndex = (options && "selectedIndex" in options) ? options.selectedIndex : -1;

            this._usersArray = new binding.List();
            this._usersCollection = new Models.UsersCollection({
                items: this._usersArray,
            });

            this.selectionChanged = ui.eventHandler(this._selectionChanged.bind(this));
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            this._usersList = element.querySelector("#usersList");

            //bind listView HTML element to our list
            this._usersList.winControl.itemDataSource = this._usersArray.dataSource;

            var self = this;
            this._usersCollection.fetchAsync().then(
                function complete(returnObject) {
                    self._fetchCompleted(element);
                }
            );

            element.querySelector("#settings_button").addEventListener("click", this._showSettings);
            element.querySelector("#remove_account_button").addEventListener("click", this._removeAccount.bind(this));
            element.querySelector("#logoff_button").addEventListener("click", this._logOff.bind(this));
        },

        _fetchCompleted: function (element) {            
            element.querySelector("header[role=banner] .pagetitle").textContent = this._userLogged.name;

            this._updateVisibility(element);
            if (this._isSingleColumn()) {
                this._wasSingleColumn = true;
                if (this._itemSelectionIndex >= 0) {
                    var user = this._usersCollection.items.getAt(this._itemSelectionIndex);

                    // For single-column detail view, load the article.
                    binding.processAll(element.querySelector(".articlesection"), user);
                }
            } else {
                // If this page has a selectionIndex, make that selection
                // appear in the ListView.
                var listView = element.querySelector(".itemlist").winControl;
                listView.selection.set(Math.max(this._itemSelectionIndex, 0));
            }
        },

        unload: function () {
            if(this._citiesArray) this._citiesArray.dispose();
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
                        user: this._userLogged,
                        selectedIndex: this._itemSelectionIndex
                    };
                    nav.history.backStack.push({
                        location: "/pages/split/split.html",
                        state: { user: this._userLogged, }
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
                if (nav.canGoBack && nav.history.backStack[nav.history.backStack.length - 1].location === "/pages/split/split.html") {
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
                            nav.navigate("/pages/split/split.html", { user: this._userLogged, selectedIndex: this._itemSelectionIndex});
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

                    document.getElementById("back_button").style.visibility = "visible";
                    document.getElementById("settings_button").style.visibility = "hidden";

                } else {
                    utils.addClass(splitPage, "groupdetail");
                    element.querySelector(".itemlist").focus();

                    document.getElementById("back_button").style.visibility = "hidden";
                    document.getElementById("settings_button").style.visibility = "visible";
                }
            } else {
                utils.removeClass(splitPage, "groupdetail");
                utils.removeClass(splitPage, "itemdetail");
                element.querySelector(".itemlist").focus();

                document.getElementById("back_button").style.visibility = "hidden";
                document.getElementById("settings_button").style.visibility = "visible";
            }
        },

        _showSettings: function () {
            WinJS.UI.SettingsFlyout.showSettings("settings");
        },

        _removeAccount: function () {
            this._userLogged.removeAsync();

            nav.navigate("/pages/login/login.html");
        },

        _logOff: function () {
            this._userLogged.logoffAsync();

            nav.navigate("/pages/login/login.html");
        },
    });
})();
