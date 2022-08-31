const m = require("mithril");

const Item = {
    list: [],
    current: {},
    loadList: function() {
        return m.request({
            method: "GET",
            url: "/api/item",
            withCredentials: false,
        })
            .then(function(result) {
                Item.list = result
            })
    },

    load: function(id) {
        return m.request({
            method: "GET",
            url: "/api/item/" + id,
            withCredentials: false,
        })
            .then(function(result) {
                Item.current = result
            })
    },

    save: function() {
        return m.request({
            method: "PUT",
            url: "/api/item/" + Item.current.id,
            body: Item.current,
            withCredentials: false,
        })
    }

};

module.exports = Item