const m = require("mithril");
const Item = require("../models/Item");

module.exports = {
    oninit: Item.loadList,
    view: function() {
        return m(".item-list", Item.list.map(function(item) {
            return m(m.route.Link, {
                class: "item-list-item",
                href: "/item/edit/" + item.id,
            }, item.name)
        }))
    }
}
