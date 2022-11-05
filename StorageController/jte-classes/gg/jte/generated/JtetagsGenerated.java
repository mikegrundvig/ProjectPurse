package gg.jte.generated;
import java.util.List;
import com.michaelgrundvig.storage.tag.TagDTO;
public final class JtetagsGenerated {
	public static final String JTE_NAME = "tags.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,11,11,11,11,14,81,81,82,82,82,84,84,84,86,86,86,88,88,88,88,88,88,89,89,89,91,91,91,95,95,103,104,105,106,107,108,109,110,111,112,114,115,116,117,118,119,120,121,122,126};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String pageTitle, List<TagDTO> tags) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(pageTitle);
		jteOutput.writeContent("</title>\r\n    <link href=\"/bootstrap.min.css\" rel=\"stylesheet\" >\r\n    <script src=\"/bootstrap.bundle.min.js\" ></script>\r\n");
		jteOutput.writeContent("\r\n\r\n    <script type=\"application/javascript\">\r\n        function handleEdit(element, entryId) {\r\n            const actionField = element.form.elements[\"action\"];\r\n            const action = actionField.value;\r\n            if(action === \"\") {\r\n                element.value = \"Submit\";\r\n                actionField.value = \"e\";\r\n                element.form.elements[\"tagName\"].style.display=\"block\";\r\n                document.getElementById(\"id_\" + entryId).style.display=\"none\";\r\n            } else {\r\n                element.form.submit();\r\n            }\r\n        }\r\n\r\n        function handleDelete(currentForm) {\r\n            currentForm.elements[\"action\"].value = 'd';\r\n            currentForm.submit();\r\n        }\r\n    </script>\r\n\r\n</head>\r\n<body>\r\n\r\n<div class=\"container\">\r\n    <div class=\"row\">\r\n        <div class=\"col-1\">\r\n\r\n            <ul class=\"nav flex-column\">\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/items\">Items</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Tags</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/bins\">Bins</a>\r\n                </li>\r\n            </ul>\r\n\r\n        </div>\r\n        <div class=\"col-11\">\r\n\r\n\r\n<div class=\"container pt-4 pb-4\">\r\n    <div class=\"h-100 d-flex flex-column bd-highlight\">\r\n        <form action=\"/tags\" method=\"post\">\r\n            <input type=\"hidden\" name=\"action\" value=\"c\">\r\n            <div class=\"input-group\">\r\n                <input type=\"text\" class=\"form-control\" id=\"tag\" name=\"tag\" placeholder=\"Start typing...\" autocomplete=\"off\">\r\n                <button type=\"submit\" class=\"btn btn-primary mb-3\">Create Tag</button>\r\n            </div>\r\n        </form>\r\n    </div>\r\n</div>\r\n\r\n<table class=\"table table-striped\">\r\n    <thead>\r\n    <tr>\r\n        <th scope=\"col\">#</th>\r\n        <th scope=\"col\">Name</th>\r\n        <th scope=\"col\">Edit</th>\r\n        <th scope=\"col\">Delete</th>\r\n    </tr>\r\n    </thead>\r\n    <tbody>\r\n    ");
		for (TagDTO entry : tags) {
			jteOutput.writeContent("\r\n        <form name=\"form_");
			jteOutput.setContext("form", "name");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" action=\"/tags\" method=\"post\">\r\n            <input type=\"hidden\" name=\"action\" value=\"\" />\r\n            <input type=\"hidden\" name=\"id\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" />\r\n            <tr>\r\n                <th scope=\"row\">");
			jteOutput.setContext("th", null);
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("</th>\r\n                <td>\r\n                    <div id=\"id_");
			jteOutput.setContext("div", "id");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\">");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("</div>\r\n                    <input type=\"text\" name=\"tagName\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.name);
			jteOutput.writeContent("\" style=\"display: none\" />\r\n                </td>\r\n                <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleEdit(this, ");
			jteOutput.setContext("input", "onclick");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent(");\" value=\"Edit\"></td>\r\n                <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleDelete(this.form);\" value=\"Delete\"></td>\r\n            </tr>\r\n        </form>\r\n    ");
		}
		jteOutput.writeContent("\r\n    </tbody>\r\n</table>\r\n\r\n        </div>\r\n    </div>\r\n</div>\r\n\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n");
		jteOutput.writeContent("\r\n\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String pageTitle = (String)params.get("pageTitle");
		List<TagDTO> tags = (List<TagDTO>)params.get("tags");
		render(jteOutput, jteHtmlInterceptor, pageTitle, tags);
	}
}
