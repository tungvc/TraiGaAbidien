<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 09/09/2016
  Time: 11:30 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Gen Link</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        String.prototype.hashCode = function() {
            var hash = 0;

            try {

                if (this.length == 0) return hash;

                for (i = 0; i < this.length; i++) {
                    char = this.charCodeAt(i);
                    hash = ((hash << 5) - hash) + char;
                    hash = hash & hash; // Convert to 32bit integer
                }
                return hash;

            } catch (e) {
                throw new Error('hashCode: ' + e);
            }
        };
        function genLink() {
            var title = encodeURIComponent(document.getElementById("title").value);
            var desc = encodeURIComponent(document.getElementById("desc").value);
            var siteUrl = encodeURIComponent(document.getElementById("siteUrl").value);
            var image = encodeURIComponent(document.getElementById("image").value);
            var redirectUrlRaw = document.getElementById("redirectUrl").value;
            var redirectUrl = encodeURIComponent(redirectUrlRaw);
            var link = 'http://drafts2022.net/genlink?title=' + title + '&desc=' + desc + '&siteUrl=' + siteUrl + '&image=' + image + '&redirectUrl=' + redirectUrl + '&token=' + redirectUrlRaw.hashCode();
            document.getElementById("link").innerHTML = link;
            document.getElementById("link").href = link;
            console.log(link);
        }
    </script>
</head>
<body>

<div class="container">
    <form>
        <div class="form-group">
            <label for="usr">Title:</label>
            <input type="text" class="form-control" id="title">
        </div>
        <div class="form-group">
            <label for="pwd">Description:</label>
            <input type="text" class="form-control" id="desc">
        </div>
        <div class="form-group">
            <label for="pwd">Website Url:</label>
            <input type="text" class="form-control" id="siteUrl">
        </div>
        <div class="form-group">
            <label for="pwd">Image:</label>
            <input type="text" class="form-control" id="image">
        </div>
        <div class="form-group">
            <label for="pwd">Redirect Url:</label>
            <input type="text" class="form-control" id="redirectUrl">
        </div>
        <button type="button" class="btn btn-success" onclick="genLink()">Gen link</button>
        <div class="form-group"></div>
        <div class="form-group"></div>
        <div class="form-group"></div>
        <div class="form-group">
            <a for="pwd" id="link" style="word-break: break-all;"></a>
        </div>
    </form>
</div>

</body>
</html>

