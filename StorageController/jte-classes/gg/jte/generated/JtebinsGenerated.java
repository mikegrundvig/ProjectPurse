package gg.jte.generated;
import java.util.List;
import com.michaelgrundvig.storage.bin.BinDTO;
public final class JtebinsGenerated {
	public static final String JTE_NAME = "bins.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,11,11,11,11,55,55,56,56,56,58,58,58,60,60,60,61,61,61,62,62,62,63,63,63,64,64,64,69,69,102};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String pageTitle, List<BinDTO> bins) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(pageTitle);
		jteOutput.writeContent("</title>\r\n    <link href=\"/bootstrap.min.css\" rel=\"stylesheet\" >\r\n    <script src=\"/bootstrap.bundle.min.js\" ></script>\r\n    <script type=\"application/javascript\">\r\n        function handleDelete(currentForm) {\r\n            currentForm.elements[\"action\"].value = 'd';\r\n            currentForm.submit();\r\n        }\r\n    </script>\r\n</head>\r\n<body>\r\n\r\n<div class=\"container\">\r\n    <div class=\"row\">\r\n\r\n        <div class=\"col-1\">\r\n            navbar\r\n            <ul class=\"nav flex-column\">\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/items\">Items</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/tags\">Tags</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Bins</a>\r\n                </li>\r\n            </ul>\r\n\r\n        </div>\r\n\r\n        <div class=\"col-9\">\r\n            <table class=\"table table-striped\">\r\n                <thead>\r\n                <tr>\r\n                    <th scope=\"col\">#</th>\r\n                    <th scope=\"col\">Type</th>\r\n                    <th scope=\"col\">Position</th>\r\n                    <th scope=\"col\">Image</th>\r\n                    <th scope=\"col\">Edit</th>\r\n                    <th scope=\"col\">Delete</th>\r\n                </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
		for (BinDTO entry : bins) {
			jteOutput.writeContent("\r\n                <form name=\"form_");
			jteOutput.setContext("form", "name");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" action=\"/bins\" method=\"post\">\r\n                    <input type=\"hidden\" name=\"action\" value=\"\" />\r\n                    <input type=\"hidden\" name=\"binId\" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("\" />\r\n                    <tr>\r\n                        <th scope=\"row\">");
			jteOutput.setContext("th", null);
			jteOutput.writeUserContent(entry.id);
			jteOutput.writeContent("</th>\r\n                        <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.type);
			jteOutput.writeContent("</td>\r\n                        <td>Drawer: ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.position.drawer);
			jteOutput.writeContent("<br />\r\n                            Row: ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.position.row);
			jteOutput.writeContent("<br />\r\n                            Column: ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(entry.position.column);
			jteOutput.writeContent("<br /></td>\r\n                        <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleEdit(this.form);\" value=\"Edit\"></td>\r\n                        <td><input type=\"button\" class=\"btn btn-primary mb-3\" onclick=\"handleDelete(this.form);\" value=\"Delete\"></td>\r\n                    </tr>\r\n                </form>\r\n                ");
		}
		jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n\r\n        <div class=\"col-2\">\r\n            <form id=\"staticForm\" action=\"/bins\" method=\"post\">\r\n                <input type=\"hidden\" name=\"action\" value=\"c\" />\r\n                <div>\r\n                    <label for=\"name\" class=\"form-label\">Name</label>\r\n                    <input type=\"text\" class=\"form-control\" id=\"binId\" name=\"binId\" placeholder=\"Id\">\r\n                </div>\r\n                <div>\r\n                    <label for=\"type\" class=\"form-label\">Type</label>\r\n                    <select class=\"form-select\" id=\"type\" name=\"type\" aria-label=\"Default select example\">\r\n                        <option selected value=\"OneByOne\">1x1</option>\r\n                        <option value=\"OneByTwo\">1x2</option>\r\n                        <option value=\"OneByThree\">1x3</option>\r\n                        <option value=\"OneByFour\">1x4</option>\r\n                        <option value=\"TwoByTwo\">2x2</option>\r\n                        <option value=\"TwoByFour\">2x4</option>\r\n                    </select>\r\n                </div>\r\n                <div>\r\n                    <button type=\"submit\" class=\"btn btn-primary mb-3\">Create Bin</button>\r\n                </div>\r\n            </form>\r\n        </div>\r\n    </div>\r\n</div>\r\n\r\n\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String pageTitle = (String)params.get("pageTitle");
		List<BinDTO> bins = (List<BinDTO>)params.get("bins");
		render(jteOutput, jteHtmlInterceptor, pageTitle, bins);
	}
}
