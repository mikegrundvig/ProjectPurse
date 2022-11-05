@import java.util.List
@import com.michaelgrundvig.storage.item.ItemDTO
@import com.michaelgrundvig.storage.tag.TagDTO
@import com.michaelgrundvig.storage.tag.TagService

@param String pageTitle
@param List<ItemDTO> items
@param List<TagDTO> tags
@param TagService tagService

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${pageTitle}</title>
    <link href="/bootstrap.min.css" rel="stylesheet" >
    <script src="/bootstrap.bundle.min.js" ></script>
    <script src="/autocomplete.js"></script>
    <script type="application/javascript">

        const allTags =  [
            @for(TagDTO entry : tags)
            {
                id: ${entry.id},
                name: "${entry.name}"
            },
            @endfor
        ];

        function findTagById(id) {
            for (let i = 0; i < allTags.length; i++) {
                if(allTags[i].id === id) {
                    return allTags[i];
                }
            }
        }

        function findTagByName(name) {
            for (let i = 0; i < allTags.length; i++) {
                if(allTags[i].name === name) {
                    return allTags[i];
                }
            }
        }

        function removeBin(binId) {
            removeGeneric(binId, "selectedBinsList", "selectedBins");
        }

        function removeTag(tagId) {
            removeGeneric(tagId, "selectedTagsList", "selectedTags");
        }

        function removeGeneric(id, listName, formField) {
            clearList(listName);
            const selectedTags = document.getElementById(formField);
            for (let i = 0; i < selectedTags.children.length; i++) {
                let currentTag = selectedTags.children[i];
                if(currentTag.value === id) {
                    currentTag.remove();
                    break;
                }
            }
            renderList(listName, formField);
        }

        function addTag() {
            const field = document.getElementById("tag");
            const tag = findTagByName(field.value);
            field.value = "";
            const selectedTags = document.getElementById("selectedTags");
            selectedTags.add(new Option(tag.name, tag.id, true, true));
            clearList("selectedTagsList");
            renderList("selectedTagsList", "selectedTags", true);
        }

        function addBin() {
            const field = document.getElementById("bin");
            const binId = field.value;
            field.value = "";
            const selectedBins = document.getElementById("selectedBins");
            selectedBins.add(new Option(binId, binId, true, true));
            clearList("selectedBinsList");
            renderList("selectedBinsList", "selectedBins", false);
        }

        function clearList(listName) {
            const list = document.getElementById(listName);
            while(list.children.length >0) {
                list.children[0].remove();
            }
        }

        function renderList(listName, formField, isTag) {
            const list = document.getElementById(listName);
            const selectedTags = document.getElementById(formField);

            for (let i = 0; i < selectedTags.children.length; i++) {
                let li = document.createElement('li');
                li.className = "list-group-item d-flex justify-content-between align-items-center";
                let span = document.createElement('span');
                span.className = "badge bg-primary rounded-pill";
                let anchor = document.createElement("a");
                const value = selectedTags.children[i].value;
                if(isTag) {
                    anchor.onclick = function() {
                        removeTag(value);
                    };
                } else {
                    anchor.onclick = function() {
                        removeBin(value);
                    };
                }
                anchor.appendChild(document.createTextNode("X"));
                span.appendChild(anchor);
                li.appendChild(document.createTextNode(selectedTags.children[i].text));
                li.appendChild(span);
                list.appendChild(li);
            }
        }

        function handleEdit(inputForm) {
            let submitForm = document.getElementById("staticForm");
            submitForm.elements["action"].value = 'e';
            submitForm.elements["itemId"].value = inputForm.elements["itemId"].value;
            submitForm.elements["name"].value = inputForm.elements["name"].value;
            submitForm.elements["description"].value = inputForm.elements["description"].value;
            submitForm.elements["createButton"].innerText = "Edit Item"

            // Clear out the existing lists
            clearList("selectedBinsList");
            clearList("selectedTagsList");
            let outputBins = document.getElementById("selectedBins");
            let outputTags = document.getElementById("selectedTags");
            let inputBins = inputForm.elements["selectedBins"];
            let inputTags = inputForm.elements["selectedTags"]

            // Populate the fixed lists
            for(let i = 0; i < inputBins.options.length; i++) {
                let currentOption = inputBins.options[i];
                outputBins.options.add(new Option(currentOption.text, currentOption.value, true, true));
            }
            for(let i = 0; i < inputTags.options.length; i++) {
                let currentOption = inputTags.options[i];
                outputTags.options.add(new Option(currentOption.text, currentOption.value, true, true));
            }

            // Render them both
            renderList("selectedTagsList", "selectedTags", true);
            renderList("selectedBinsList", "selectedBins", false);
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
navbar
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Items</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tags">Tags</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/bins">Bins</a>
                </li>
            </ul>

        </div>
        <div class="col-9">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Tags</th>
                    <th scope="col">Bins</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                @for(ItemDTO entry : items)
                    <form name="form_${entry.id}" action="/items" method="post">
                        <input type="hidden" name="action" value="" />
                        <input type="hidden" name="itemId" value="${entry.id}" />
                        <input type="hidden" name="name" value="${entry.name}" />
                        <input type="hidden" name="description" value="${entry.description}" />
                        <select name="selectedBins" multiple="multiple" style="display: none">
                            @for(int binId : entry.bins)
                                <option value="${binId}">${binId}</option>
                            @endfor
                        </select>
                        <select name="selectedTags" multiple="multiple" style="display: none">
                            @for(int tagId : entry.tags)
                                <option value="${tagId}">${tagService.byId(tagId).getName()}</option>
                            @endfor
                        </select>
                        <tr>
                            <th scope="row">${entry.id}</th>
                            <td>${entry.name}</td>
                            <td>${entry.description}</td>
                            <td>
                                @for(int tagId : entry.tags)
                                    ${tagService.byId(tagId).getName()}
                                @endfor
                            </td>
                            <td>
                                @for(int binId : entry.bins)
                                    ${binId}
                                @endfor
                            </td>
                            <td><input type="button" class="btn btn-primary mb-3" onclick="handleEdit(this.form);" value="Edit"></td>
                            <td><input type="button" class="btn btn-primary mb-3" onclick="handleDelete(this.form);" value="Delete"></td>
                        </tr>
                    </form>
                @endfor
                </tbody>
            </table>


        </div>
            <div class="col-2">
                <form id="staticForm" action="/items" method="post">
                    <input type="hidden" name="action" value="c" />
                    <input type="hidden" name="itemId" />
                    <select id="selectedTags" name="selectedTags" multiple="multiple" style="display: none"></select>
                    <select id="selectedBins" name="selectedBins" multiple="multiple" style="display: none"></select>
                    <div>
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                    </div>
                    <div>
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                    </div>
                    <div>
                        <label for="bin" class="form-label">Bin</label>
                        <input type="text" class="form-control" id="bin" name="bin" placeholder="bin..." autocomplete="off">
                        <input type="button" class="btn btn-primary mb-3" onclick="addBin();" value="Add">
                        <ul class="list-group" id="selectedBinsList"></ul>
                    </div>
                    <div>
                        <label for="tag" class="form-label">Tag</label>
                        <input type="text" class="form-control" id="tag" name="tag" placeholder="tag..." autocomplete="off">
                        <input type="button" class="btn btn-primary mb-3" onclick="addTag();" value="Add">
                        <ul class="list-group" id="selectedTagsList"></ul>
                    </div>
                    <div>
                        <button type="submit" id="createButton" class="btn btn-primary mb-3">Create Item</button>
                    </div>
                </form>
            </div>
    </div>
</div>

<script type="application/javascript">

    const field = document.getElementById('tag');
    const tagAc = new Autocomplete(field, {
        data: [{label: "I'm a label", value: 42}],
        maximumItems: 5,
        threshold: 1,
        onSelectItem: ({label, value}) => {
            console.log("user selected:", label, value);
        }
    });

    tagAc.setData([
        @for(TagDTO entry : tags)
        {
            "label": "${entry.name}",
            "value": "${entry.id}"
        },
        @endfor
    ]);


</script>

</body>
</html>