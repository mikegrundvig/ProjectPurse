import m from "mithril";

const ItemList = require("./views/ItemList");
const ItemForm = require("./views/ItemForm");
const Layout = require("./views/Layout");

m.route(document.body, "/item/list", {
    "/item/list": {
        render: function() {
            return m(Layout, m(ItemList))
        }
    },
    "/item/edit/:id": {
        render: function(vnode) {
            return m(Layout, m(ItemForm, vnode.attrs))
        }
    },
})