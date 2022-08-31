const m = require("mithril");

module.exports = {
    view: function(vnode) {
        return m("main.layout", [
            m("nav.menu", [
                m(m.route.Link, {href: "/list"}, "Items"),
                m(m.route.Link, {href: "/list"}, "Tags"),
                m(m.route.Link, {href: "/list"}, "Bins"),
                m(m.route.Link, {href: "/list"}, "Drawers"),
            ]),
            m("section", vnode.children)
        ])
    }
}