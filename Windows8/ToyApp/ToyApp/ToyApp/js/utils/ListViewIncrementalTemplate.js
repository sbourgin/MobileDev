(function () {
    'use strict';

    WinJS.Namespace.define('Template', {

        //creation of the class to manage a request object
        ListViewIncrementalTemplate: WinJS.Class.define(

            //constructor
            function () {
            },

            //properties of the class
            {
            },

            //static methods
            {
                incrementalTemplate: function (template, listView, collection) {
                    return function (itemPromise) {

                        return itemPromise.then(function (item) {
                            var firstItem = collection.items.getItem(0);
                            var lastItem = collection.items.getItem(collection.items.length - 1);

                            //if the first item is being displayed, 
                            //and if his id is different from 0 because that means that we already deleted items in our list
                            if (item.key === firstItem.key && firstItem.data.id != 1) {
                                var fetching;

                                //avoid interleavings
                                if (!fetching) {
                                    fetching = true;
                                   
                                    collection.fetchAsync(firstItem.data.id, 1).then(
                                        function complete(collection) {
                                            fetching = false;
                                        }
                                    );
                                }
                            }
                            //if the last item is being displayed, fetch new data
                            else if (item.key === lastItem.key) {
                                var fetching;

                                //avoid interleavings
                                if (!fetching) {
                                    fetching = true;

                                    collection.fetchAsync(lastItem.data.id, 0).then(
                                        function complete(collection) {
                                            fetching = false;
                                        }
                                    );
                                }
                            }

                            return template(itemPromise);
                        });
                    };
                },
            }
        )
    });
})();