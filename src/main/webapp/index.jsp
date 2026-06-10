<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <title>REST API Caller</title>
</head>

<body>

<h1>REST API Caller</h1>

<form action="apiCaller" method="post">

    <table>

        <tr>
            <td>URL</td>
            <td>
                <input
                        type="text"
                        name="url"
                        size="80"
                        placeholder="http://10.177.44.51:4001/api/account"
                        required>
            </td>
        </tr>

        <tr>
            <td>Method</td>
            <td>
                <select name="method">

                    <option value="POST" selected>POST</option>

                    <option value="GET">GET</option>

                    <option value="PUT">PUT</option>

                    <option value="DELETE">DELETE</option>

                    <option value="PATCH">PATCH</option>

                </select>
            </td>
        </tr>

        <tr>
            <td>Options</td>

            <td>

                <input
                        type="checkbox"
                        name="ignoreSsl"
                        value="true"
                        checked>

                Ignore SSL Validation (-k)

                <br><br>

                <input
                        type="checkbox"
                        name="verbose"
                        value="true">

                Verbose Logging (-v)

            </td>
        </tr>

        <tr>
            <td>Headers</td>

            <td>

                <textarea
                        name="headers"
                        rows="6"
                        cols="80"
                        placeholder="Content-Type: application/json
Authorization: Bearer abc123"></textarea>

            </td>
        </tr>

        <tr>
            <td>JSON Body</td>

            <td>

                <textarea
                        name="jsonBody"
                        rows="15"
                        cols="80"
                        placeholder='{
  "customerId":"123"
}'></textarea>

            </td>
        </tr>

        <tr>

            <td colspan="2">

                <input
                        type="submit"
                        value="Call API">

            </td>

        </tr>

    </table>

</form>

</body>

</html>