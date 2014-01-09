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

        _messagesCollection: null,
        _messagesArray: null,
        _messagesList: null,

        _imageName: null,
        _imageFile: null,

        // This function is called to initialize the page.
        init: function (element, options) {
            this._userLogged = options.user;

            this._itemSelectionIndex = (options && "selectedIndex" in options) ? options.selectedIndex : -1

            this._usersArray = new binding.List();
            this._usersCollection = new Models.UsersCollection({
                items: this._usersArray,
            });

            //listen on custom event in order to refresh UI when new messages received
            this._userLogged.addEventListener("newMessages", this._refresh.bind(this));

            this.selectionChanged = ui.eventHandler(this._selectionChanged.bind(this));
        },

        // This function is called whenever a user navigates to this page.
        ready: function (element, options) {
            //get user thumbnail
            this._userLogged.getThumbAsync().then(
                function complete(returnObject) {
                    document.getElementById("thumbnailImage").src = returnObject.model.thumbnail;
                }
            );

            this._usersList = element.querySelector("#usersList");

            //bind listView HTML element to our list
            this._usersList.winControl.itemDataSource = this._usersArray.dataSource;
            this._usersList.winControl.itemTemplate = Utils.Template.userTemplate();

            var self = this;
            this._usersCollection.fetchAsync().then(
                function complete(returnObject) {
                    //remove current user from list
                    returnObject.collection.removeUser(self._userLogged);

                    self._fetchCompleted(element);
                }
            );

            element.querySelector("#settingsButton").addEventListener("click", this._showSettings);
            element.querySelector("#removeAccountButton").addEventListener("click", this._removeAccount.bind(this));
            element.querySelector("#logoffButton").addEventListener("click", this._logOff.bind(this));
            element.querySelector("#photoButton").addEventListener("click", this._uploadPhoto.bind(this));
            element.querySelector("#sendButton").addEventListener("click", this._sendMessage.bind(this));
            element.querySelector("#thumbnailButton").addEventListener("click", this._changeThumbnail.bind(this));
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
                            nav.navigate("/pages/split/split.html", { user: this._userLogged, selectedIndex: this._itemSelectionIndex });

                            this._fetchMessages();
                        }.bind(this));
                    } else {
                        // If fullscreen or filled, update the details column with new data.
                        details = document.querySelector(".articlesection");
                        binding.processAll(details, items[0].data);
                        details.scrollTop = 0;

                        this._fetchMessages();
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

                    document.getElementById("backButton").style.visibility = "visible";
                    document.getElementById("settingsButton").style.visibility = "hidden";

                } else {
                    utils.addClass(splitPage, "groupdetail");
                    element.querySelector(".itemlist").focus();

                    document.getElementById("backButton").style.visibility = "hidden";
                    document.getElementById("settingsButton").style.visibility = "visible";
                }
            } else {
                utils.removeClass(splitPage, "groupdetail");
                utils.removeClass(splitPage, "itemdetail");
                element.querySelector(".itemlist").focus();

                document.getElementById("backButton").style.visibility = "hidden";
                document.getElementById("settingsButton").style.visibility = "visible";
            }
        },

        _showSettings: function () {
            WinJS.UI.SettingsFlyout.showSettings("settings");
        },

        _removeAccount: function () {
            this._userLogged.removeAsync().then(
                function complete(returnObject) {
                    nav.navigate("/pages/login/login.html");
                }
            );            
        },

        _logOff: function () {
            this._userLogged.logoffAsync().then(
                function complete(returnObject) {
                    nav.navigate("/pages/login/login.html");
                }
            );
        },

        _uploadPhoto: function () {
            // Verify that we are currently not snapped, or that we can unsnap to open the picker
            var currentState = Windows.UI.ViewManagement.ApplicationView.value;
            if (currentState === Windows.UI.ViewManagement.ApplicationViewState.snapped &&
                !Windows.UI.ViewManagement.ApplicationView.tryUnsnap()) {
                // Fail silently if we can't unsnap
                return;
            }

            var selector = new Windows.Storage.Pickers.FileOpenPicker();
            selector.suggestedStartLocation = Windows.Storage.Pickers.PickerLocationId.picturesLibrary;
            selector.fileTypeFilter.replaceAll([".jpg", ".jpeg"]);
            selector.viewMode = Windows.Storage.Pickers.PickerViewMode.thumbnail;

            var self = this;

            selector.pickSingleFileAsync().then(
                function complete(file) {
                    if (file && file.contentType == "image/jpeg") {
                        self._imageName = file.name;
                        self._imageFile = MSApp.createFileFromStorageFile(file);

                        document.getElementById("imageIndicator").innerText = file.name;
                    }
                },

                function error(errorMessage) {
                    console.log("Error: " + errorMessage);
                }
            );
        },

        _sendMessage: function(){
            var messageHTML = document.getElementById("messageText");
            var userSelected = this._usersCollection.items.getAt(this._itemSelectionIndex);

            if (messageHTML.value || (this._imageName && this._imageFile)) {
                var parameters = {
                    from: this._userLogged.name,
                    to: userSelected.name,
                    subject: "",
                };

                if (messageHTML.value) {
                    parameters['text'] = messageHTML.value;
                }

                if (this._imageName && this._imageFile) {
                    parameters["imageName"] = this._imageName;
                    parameters["imageFile"] = this._imageFile;
                }

                var message = Models.MessageFactory.createMessage(parameters);

                var self = this;

                message.sendAsync().then(
                    function complete(returnObject) {
                        messageHTML.value = '';
                        document.getElementById("imageIndicator").innerText = '';

                        self._imageName = null;
                        self._imageFile = null;

                        self._fetchMessages();
                    }
                );
            }
        },

        _changeThumbnail: function(){
            var selector = new Windows.Storage.Pickers.FileOpenPicker();
            selector.suggestedStartLocation = Windows.Storage.Pickers.PickerLocationId.picturesLibrary;
            selector.fileTypeFilter.replaceAll([".jpg", ".jpeg"]);
            selector.viewMode = Windows.Storage.Pickers.PickerViewMode.thumbnail;

            var self = this;

            selector.pickSingleFileAsync().then(
                function complete(file) {
                    if (file && file.contentType == "image/jpeg") {
                        var thumbnail = MSApp.createFileFromStorageFile(file);

                        self._userLogged.thumbnail = thumbnail;
                        self._userLogged.setThumbAsync().then(
                            function complete(returnObject) {
                                var objectURL = window.URL.createObjectURL(returnObject.model.thumbnail);

                                document.getElementById("thumbnailImage").src = objectURL;
                            }
                        );
                    }
                },

                function error(errorMessage) {
                    console.log("Error: " + errorMessage);
                }
            );
        },

        _fetchMessages: function () {
            var userSelected = this._usersCollection.items.getAt(this._itemSelectionIndex);

            this._messagesArray = new binding.List();

            this._messagesCollection = new Models.MessagesCollection({
                items: this._messagesArray,
                receiver: userSelected.getNormalisedName(),
            });

            this._messagesList = document.getElementById("messagesList");
            this._messagesList.winControl.itemDataSource = this._messagesArray.dataSource;

            var self = this;

            this._messagesCollection.fetchAsync().then(
                function complete(returnObject) {
                    //apply template 
                    self._messagesList.winControl.itemTemplate = Utils.Template.messageTemplate(
                        self._userLogged.getNormalisedName(),
                        self._messageRemove.bind(self),
                        self._messagesList,
                        self._messagesCollection.items.length
                    );

                    //make all messages read
                    returnObject.collection.readAllMessages();
                    
                    //remove notification
                    if (userSelected.unreadMessages) {
                        var subtitle = self._usersList.querySelectorAll(".item-subtitle")[self._itemSelectionIndex];

                        if (subtitle) {
                            subtitle.innerText = userSelected.description;
                            WinJS.Utilities.removeClass(subtitle, "unreadMessages");
                        }
                    }
                }
            );
        },

        _messageRemove: function (item) {
            var index = this._messagesArray.indexOfKey(item.key);

            this._messagesArray.splice(index, 1);

            this._messagesList.winControl.forceLayout();
        },

        _refresh: function () {
            console.log("refresh");

            var usersList = document.getElementById("usersList");

            if (usersList) {
                usersList.winControl.forceLayout();
            }

            var self = this;

            if (this._messagesCollection) {
                this._messagesCollection.getNbMessagesAsync().then(
                    function complete(returnObject) {
                        if (returnObject.nbMessages != self._messagesCollection.items.length) {
                            self._fetchMessages();
                        }
                    }
                )
            }
        },
    });
})();
