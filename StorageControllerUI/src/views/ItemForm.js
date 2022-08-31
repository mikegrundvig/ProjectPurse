const m = require("mithril");
const Item = require("../models/Item");

module.exports = {
    oninit: function(vnode) {Item.load(vnode.attrs.id)},
    view: function() {
        return m("form", {
            onsubmit: function(e) {
                e.preventDefault()
                Item.save()
            }
        }, [
            m("label.label", "Name"),
            m("input.input[type=text][placeholder=Name]",
                {
                    oninput: function (e) { Item.current.name = e.target.value },
                    value: Item.current.name
                }
            ),
            //m("label.label", "Last name"),
            //m("input.input[placeholder=Last name]"),
            m("button.button[type=submit]", "Save"),
        ])
    }
}