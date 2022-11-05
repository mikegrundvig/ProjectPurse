@import java.util.List
@import com.michaelgrundvig.storage.tag.TagDTO

@param String pageTitle
@param List<TagDTO> tags

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${pageTitle}</title>
    <link href="/bootstrap.min.css" rel="stylesheet" >
    <script src="/bootstrap.bundle.min.js" ></script>
<%--    <script src="/autocomplete.js"></script>--%>

    <script type="application/javascript">
        function handleEdit(element, entryId) {
            const actionField = element.form.elements["action"];
            const action = actionField.value;
            if(action === "") {
                element.value = "Submit";
                actionField.value = "e";
                element.form.elements["tagName"].style.display="block";
                document.getElementById("id_" + entryId).style.display="none";
            } else {
                element.form.submit();
            }
        }

        function handleDelete(currentForm) {
            currentForm.elements["action"].value = 'd';
            currentForm.submit();
        }
    </script>

</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-1">

            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="/items">Items</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Tags</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/bins">Bins</a>
                </li>
            </ul>

        </div>
        <div class="col-11">


<div class="container pt-4 pb-4">
    <div class="h-100 d-flex flex-column bd-highlight">
        <form action="/tags" method="post">
            <input type="hidden" name="action" value="c">
            <div class="input-group">
                <input type="text" class="form-control" id="tag" name="tag" placeholder="Start typing..." autocomplete="off">
                <button type="submit" class="btn btn-primary mb-3">Create Tag</button>
            </div>
        </form>
    </div>
</div>

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Edit</th>
        <th scope="col">Delete</th>
    </tr>
    </thead>
    <tbody>
    @for(TagDTO entry : tags)
        <form name="form_${entry.id}" action="/tags" method="post">
            <input type="hidden" name="action" value="" />
            <input type="hidden" name="id" value="${entry.id}" />
            <tr>
                <th scope="row">${entry.id}</th>
                <td>
                    <div id="id_${entry.id}">${entry.name}</div>
                    <input type="text" name="tagName" value="${entry.name}" style="display: none" />
                </td>
                <td><input type="button" class="btn btn-primary mb-3" onclick="handleEdit(this, ${entry.id});" value="Edit"></td>
                <td><input type="button" class="btn btn-primary mb-3" onclick="handleDelete(this.form);" value="Delete"></td>
            </tr>
        </form>
    @endfor
    </tbody>
</table>

        </div>
    </div>
</div>

<%--<script>--%>
<%--    const field = document.getElementById('tag');--%>
<%--    const ac = new Autocomplete(field, {--%>
<%--        data: [{label: "I'm a label", value: 42}],--%>
<%--        maximumItems: 5,--%>
<%--        threshold: 1,--%>
<%--        onSelectItem: ({label, value}) => {--%>
<%--            console.log("user selected:", label, value);--%>
<%--        }--%>
<%--    });--%>

<%--    ac.setData([--%>
<%--        --%>
<%--        {--%>
<%--            "label": "",--%>
<%--            "value": ""--%>
<%--        },--%>
<%--        --%>
<%--    ]);--%>
<%--</script>--%>

</body>
</html>
