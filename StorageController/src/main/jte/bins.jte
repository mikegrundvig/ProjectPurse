@import java.util.List
@import com.michaelgrundvig.storage.bin.BinDTO

@param String pageTitle
@param List<BinDTO> bins

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${pageTitle}</title>
    <link href="/bootstrap.min.css" rel="stylesheet" >
    <script src="/bootstrap.bundle.min.js" ></script>
    <script type="application/javascript">
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
                    <a class="nav-link" href="/items">Items</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tags">Tags</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Bins</a>
                </li>
            </ul>

        </div>

        <div class="col-9">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Type</th>
                    <th scope="col">Position</th>
                    <th scope="col">Image</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                @for(BinDTO entry : bins)
                <form name="form_${entry.id}" action="/bins" method="post">
                    <input type="hidden" name="action" value="" />
                    <input type="hidden" name="binId" value="${entry.id}" />
                    <tr>
                        <th scope="row">${entry.id}</th>
                        <td>${entry.type}</td>
                        <td>Drawer: ${entry.position.drawer}<br />
                            Row: ${entry.position.row}<br />
                            Column: ${entry.position.column}<br /></td>
                        <td><input type="button" class="btn btn-primary mb-3" onclick="handleEdit(this.form);" value="Edit"></td>
                        <td><input type="button" class="btn btn-primary mb-3" onclick="handleDelete(this.form);" value="Delete"></td>
                    </tr>
                </form>
                @endfor
                </tbody>
            </table>
        </div>

        <div class="col-2">
            <form id="staticForm" action="/bins" method="post">
                <input type="hidden" name="action" value="c" />
                <div>
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="binId" name="binId" placeholder="Id">
                </div>
                <div>
                    <label for="type" class="form-label">Type</label>
                    <select class="form-select" id="type" name="type" aria-label="Default select example">
                        <option selected value="OneByOne">1x1</option>
                        <option value="OneByTwo">1x2</option>
                        <option value="OneByThree">1x3</option>
                        <option value="OneByFour">1x4</option>
                        <option value="TwoByTwo">2x2</option>
                        <option value="TwoByFour">2x4</option>
                    </select>
                </div>
                <div>
                    <button type="submit" class="btn btn-primary mb-3">Create Bin</button>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>