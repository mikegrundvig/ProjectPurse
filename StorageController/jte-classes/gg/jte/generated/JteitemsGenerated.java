package gg.jte.generated;
import java.util.List;
import com.michaelgrundvig.storage.item.ItemDTO;
import com.michaelgrundvig.storage.tag.TagDTO;
import com.michaelgrundvig.storage.tag.TagService;
public final class JteitemsGenerated {
	public static final String JTE_NAME = "items.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,15,15,15,15,22,22,24,24,24,25,25,25,27,27,193,193,194,194,194,196,196,196,197,197,197,198,198,198,199,199,200,200,201,201,201,201,201,201,202,202,204,204,205,205,206,206,206,206,206,206,207,207,210,210,210,211,211,211,212,212,212,214,214,215,215,215,216,216,219,219,220,220,220,221,221,227,227,237,237,238,238,280,280,282,282,282,283,283,283,285,285,292};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String pageTitle, List<ItemDTO> items, List<TagDTO> tags, TagService tagService) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(pageTitle);
		jteOutput.writeContent("</title>\r\n    <link href=\"/bootstrap.min.css\" rel=\"stylesheet\" >\r\n    <script src=\"/bootstrap.bundle.min.js\" ></script>\r\n    <script src=\"/autocomplete.js\"></script>\r\n    <script type=\"application/javascript\">\r\n\r\n        const allTags =  [\r\n            ");
		for (TagDTO entry : tags) {
			jteOutput.writeContent("\r\n            {\r\n                id: ");
			jteOutput.setContext("script", null);
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent(",\r\n                name: \"");
			jteOutput.setContext("script", null);
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("\"\r\n            },\r\n            ");
		}
		jteOutput.writeContent("\r\n        ];\r\n\r\n        function findTagById(id) {\r\n            for (let i = 0; i < allTags.length; i++) {\r\n                if(allTags[i].id === id) {\r\n                    return allTags[i];\r\n                }\r\n            }\r\n        }\r\n\r\n        function findTagByName(name) {\r\n            for (let i = 0; i < allTags.length; i++) {\r\n                if(allTags[i].name === name) {\r\n                    return allTags[i];\r\n                }\r\n            }\r\n        }\r\n\r\n        function removeBin(binId) {\r\n            removeGeneric(binId, \"selectedBinsList\", \"selectedBins\");\r\n        }\r\n\r\n        function removeTag(tagId) {\r\n            removeGeneric(tagId, \"selectedTagsList\", \"selectedTags\");\r\n        }\r\n\r\n        function removeGeneric(id, listName, formField) {\r\n            clearList(listName);\r\n            const selectedTags = document.getElementById(formField);\r\n            for (let i = 0; i < selectedTags.children.length; i++) {\r\n                let currentTag = selectedTags.children[i];\r\n                if(currentTag.value === id) {\r\n                    currentTag.remove();\r\n                    break;\r\n                }\r\n            }\r\n            renderList(listName, formField);\r\n        }\r\n\r\n        function addTag() {\r\n            const field = document.getElementById(\"tag\");\r\n            const tag = findTagByName(field.value);\r\n            field.value = \"\";\r\n            const selectedTags = document.getElementById(\"selectedTags\");\r\n            selectedTags.add(new Option(tag.name, tag.id, true, true));\r\n            clearList(\"selectedTagsList\");\r\n            renderList(\"selectedTagsList\", \"selectedTags\", true);\r\n        }\r\n\r\n        function addBin() {\r\n            const field = document.getElementById(\"bin\");\r\n            const binId = field.value;\r\n            field.value = \"\";\r\n            const selectedBins = document.getElementById(\"selectedBins\");\r\n            selectedBins.add(new Option(binId, binId, true, true));\r\n            clearList(\"selectedBinsList\");\r\n            renderList(\"selectedBinsList\", \"selectedBins\", false);\r\n        }\r\n\r\n        function clearList(listName) {\r\n            const list = document.getElementById(listName);\r\n            while(list.children.length >0) {\r\n                list.children[0].remove();\r\n            }\r\n        }\r\n\r\n        function renderList(listName, formField, isTag) {\r\n            const list = document.getElementById(listName);\r\n            const selectedTags = document.getElementById(formField);\r\n\r\n            for (let i = 0; i < selectedTags.children.length; i++) {\r\n                let li = document.createElement('li');\r\n                li.className = \"list-group-item d-flex justify-content-between align-items-center\";\r\n                let span = document.createElement('span');\r\n                span.className = \"badge bg-primary rounded-pill\";\r\n                let anchor = document.createElement(\"a\");\r\n                const value = selectedTags.children[i].value;\r\n                if(isTag) {\r\n                    anchor.onclick = function() {\r\n                        removeTag(value);\r\n                    };\r\n                } else {\r\n                    anchor.onclick = function() {\r\n                        removeBin(value);\r\n                    };\r\n                }\r\n                anchor.appendChild(document.createTextNode(\"X\"));\r\n                span.appendChild(anchor);\r\n                li.appendChild(document.createTextNode(selectedTags.children[i].text));\r\n                li.appendChild(span);\r\n                list.appendChild(li);\r\n            }\r\n        }\r\n\r\n        function handleEdit(inputForm) {\r\n            let submitForm = document.getElementById(\"staticForm\");\r\n            submitForm.elements[\"action\"].value = 'e';\r\n            submitForm.elements[\"itemId\"].value = inputForm.elements[\"itemId\"].value;\r\n            submitForm.elements[\"name\"].value = inputForm.elements[\"name\"].value;\r\n            submitForm.elements[\"description\"].value = inputForm.elements[\"description\"].value;\r\n            submitForm.elements[\"createButton\"].innerText = \"Edit Item\"\r\n\r\n            // Clear out the existing lists\r\n            clearList(\"selectedBinsList\");\r\n            clearList(\"selectedTagsList\");\r\n            let outputBins = document.getElementById(\"selectedBins\");\r\n            let outputTags = document.getElementById(\"selectedTags\");\r\n            let inputBins = inputForm.elements[\"selectedBins\"];\r\n            let inputTags = inputForm.elements[\"selectedTags\"]\r\n\r\n            // Populate the fixed lists\r\n            for(let i = 0; i < inputBins.options.length; i++) {\r\n                let currentOption = inputBins.options[i];\r\n                outputBins.options.add(new Option(currentOption.text, currentOption.value, true, true));\r\n            }\r\n            for(let i = 0; i < inputTags.options.length; i++) {\r\n                let currentOption = inputTags.options[i];\r\n                outputTags.options.add(new Option(currentOption.text, currentOption.value, true, true));\r\n            }\r\n\r\n            // Render them both\r\n            renderList(\"selectedTagsList\", \"selectedTags\", true);\r\n            renderList(\"selectedBinsList\", \"selectedBins\", false);\r\n        }\r\n\r\n        function handleDelete(currentForm) {\r\n            currentForm.elements[\"action\"].value = 'd';\r\n            currentForm.submit();\r\n        }\r\n    </script>\r\n</head>\r\n<body>\r\n\r\n<div class=\"container\">\r\n    <div class=\"row\">\r\n        <div class=\"col-1\">\r\nnavbar\r\n            <ul class=\"nav flex-column\">\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Items</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/tags\">Tags</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/bins\">Bins</a>\r\n                </li>\r\n            </ul>\r\n\r\n        </div>\r\n        <div class=\"col-9\">\r\n\r\n            <table class=\"table table-striped\">\r\n                <thead>\r\n                <tr>\r\n                    <th scope=\"col\">#</th>\r\n                    <th scope=\"col\">Name</th>\r\n                    <th scope=\"col\">Description</th>\r\n                    <th scope=\"col\">Tags</th>\r\n                    <th scope=\"col\">Bins</th>\r\n                    <th scope=\"col\">Edit</th>\r\n                    <th scope=\"col\">Delete</th>\r\n                </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
		for (ItemDTO entry : items) {
			jteOutput.writeContent("\r\n                    <form name=\"form_");
			jteOutput.setContext("form", "name");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" action=\"/items\" method=\"post\">\r\n                        <input type=\"hidden\" name=\"action\" value=\"\" />\r\n                        <input type=\"hidden\" name=\"itemId\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" />\r\n                        <input type=\"hidden\" name=\"name\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("\" />\r\n                        <input type=\"hidden\" name=\"description\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.description);
			jteOutput.writeContent("\" />\r\n                        <select name=\"selectedBins\" ");
			jteOutput.writeContent("multiple");
			jteOutput.writeContent(" style=\"display: none\">\r\n                            ");
			for (int binId : entry.bins) {
				jteOutput.writeContent("\r\n                                <option value=\"");
				jteOutput.setContext("option", "value");
				jteOutput.writeUserContent(binId);
				jteOutput.writeContent("\">");
				jteOutput.setContext("option", null);
				jteOutput.writeUserContent(binId);
				jteOutput.writeContent("</option>\r\n                            ");
			}
			jteOutput.writeContent("\r\n                        </select>\r\n                        <select name=\"selectedTags\" ");
			jteOutput.writeContent("multiple");
			jteOutput.writeContent(" style=\"display: none\">\r\n                            ");
			for (int tagId : entry.tags) {
				jteOutput.writeContent("\r\n                                <option value=\"");
				jteOutput.setContext("option", "value");
				jteOutput.writeUserContent(tagId);
				jteOutput.writeContent("\">");
				jteOutput.setContext("option", null);
				jteOutput.writeUserContent(tagService.byId(tagId).getName());
				jteOutput.writeContent("</option>\r\n                            ");
			}
			jteOutput.writeContent("\r\n                        </select>\r\n                        <tr>\r\n                            <th scope=\"row\">");
			jteOutput.setContext("th", null);
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("</th>\r\n                            <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("</td>\r\n                            <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.description);
			jteOutput.writeContent("</td>\r\n                            <td>\r\n                                ");
			for (int tagId : entry.tags) {
				jteOutput.writeContent("\r\n                                    ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(tagService.byId(tagId).getName());
				jteOutput.writeContent("\r\n                                ");
			}
			jteOutput.writeContent("\r\n                            </td>\r\n                            <td>\r\n                                ");
			for (int binId : entry.bins) {
				jteOutput.writeContent("\r\n                                    ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(binId);
				jteOutput.writeContent("\r\n                                ");
			}
			jteOutput.writeContent("\r\n                            </td>\r\n                            <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleEdit(this.form);\" value=\"Edit\"></td>\r\n                            <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleDelete(this.form);\" value=\"Delete\"></td>\r\n                        </tr>\r\n                    </form>\r\n                ");
		}
		jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n\r\n\r\n        </div>\r\n            <div class=\"col-2\">\r\n                <form id=\"staticForm\" action=\"/items\" method=\"post\">\r\n                    <input type=\"hidden\" name=\"action\" value=\"c\" />\r\n                    <input type=\"hidden\" name=\"itemId\" />\r\n                    <select id=\"selectedTags\" name=\"selectedTags\" ");
		jteOutput.writeContent("multiple");
		jteOutput.writeContent(" style=\"display: none\"></select>\r\n                    <select id=\"selectedBins\" name=\"selectedBins\" ");
		jteOutput.writeContent("multiple");
		jteOutput.writeContent(" style=\"display: none\"></select>\r\n                    <div>\r\n                        <label for=\"name\" class=\"form-label\">Name</label>\r\n                        <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" placeholder=\"Name\">\r\n                    </div>\r\n                    <div>\r\n                        <label for=\"description\" class=\"form-label\">Description</label>\r\n                        <input type=\"text\" class=\"form-control\" id=\"description\" name=\"description\" placeholder=\"Description\">\r\n                    </div>\r\n                    <div>\r\n                        <label for=\"bin\" class=\"form-label\">Bin</label>\r\n                        <input type=\"text\" class=\"form-control\" id=\"bin\" name=\"bin\" placeholder=\"bin...\" autocomplete=\"off\">\r\n                        <input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"addBin();\" value=\"Add\">\r\n                        <ul class=\"list-group\" id=\"selectedBinsList\"></ul>\r\n                    </div>\r\n                    <div>\r\n                        <label for=\"tag\" class=\"form-label\">Tag</label>\r\n                        <input type=\"text\" class=\"form-control\" id=\"tag\" name=\"tag\" placeholder=\"tag...\" autocomplete=\"off\">\r\n                        <input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"addTag();\" value=\"Add\">\r\n                        <ul class=\"list-group\" id=\"selectedTagsList\"></ul>\r\n                    </div>\r\n                    <div>\r\n                        <button type=\"submit\" id=\"createButton\" class=\"btn btn-primary mb-3\">Create Item</button>\r\n                    </div>\r\n                </form>\r\n            </div>\r\n    </div>\r\n</div>\r\n\r\n<script type=\"application/javascript\">\r\n\r\n    const field = document.getElementById('tag');\r\n    const tagAc = new Autocomplete(field, {\r\n        data: [{label: \"I'm a label\", value: 42}],\r\n        maximumItems: 5,\r\n        threshold: 1,\r\n        onSelectItem: ({label, value}) => {\r\n            console.log(\"user selected:\", label, value);\r\n        }\r\n    });\r\n\r\n    tagAc.setData([\r\n        ");
		for (TagDTO entry : tags) {
			jteOutput.writeContent("\r\n        {\r\n            \"label\": \"");
			jteOutput.setContext("script", null);
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("\",\r\n            \"value\": \"");
			jteOutput.setContext("script", null);
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\"\r\n        },\r\n        ");
		}
		jteOutput.writeContent("\r\n    ]);\r\n\r\n\r\n</script>\r\n\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String pageTitle = (String)params.get("pageTitle");
		List<ItemDTO> items = (List<ItemDTO>)params.get("items");
		List<TagDTO> tags = (List<TagDTO>)params.get("tags");
		TagService tagService = (TagService)params.get("tagService");
		render(jteOutput, jteHtmlInterceptor, pageTitle, items, tags, tagService);
	}
}
