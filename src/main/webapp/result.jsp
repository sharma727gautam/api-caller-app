<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <title>API Call Result</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            color: #333333;
        }

        h2 {
            background-color: #f2f2f2;
            padding: 10px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        td {
            border: 1px solid #cccccc;
            padding: 8px;
            vertical-align: top;
        }

        pre {
            white-space: pre-wrap;
            word-wrap: break-word;
            margin: 0;
        }

        .error {
            color: red;
            font-weight: bold;
        }

        .success {
            color: green;
            font-weight: bold;
        }

        .button {
            display: inline-block;
            padding: 10px;
            margin-top: 15px;
            text-decoration: none;
            border: 1px solid #333333;
        }

    </style>

</head>

<body>

<%
String errorMessage =
        (String) request.getAttribute("errorMessage");

Boolean verbose =
        (Boolean) request.getAttribute("verbose");
%>

<h1>API Call Result</h1>

<% if (errorMessage != null) { %>

    <h2>Error Information</h2>

    <p class="error">
        <%= errorMessage %>
    </p>

    <a class="button" href="index.jsp">
        Back To API Caller
    </a>

<% } else { %>

    <% if (Boolean.TRUE.equals(verbose)) { %>

        <h2>Request Information</h2>

        <table>

            <tr>
                <td>URL</td>
                <td>${url}</td>
            </tr>

            <tr>
                <td>Method</td>
                <td>${method}</td>
            </tr>

            <tr>
                <td>Ignore SSL (-k)</td>
                <td>${ignoreSsl}</td>
            </tr>

            <tr>
                <td>Verbose (-v)</td>
                <td>${verbose}</td>
            </tr>

            <tr>
                <td>Headers</td>
                <td>
                    <pre>${headers}</pre>
                </td>
            </tr>

            <tr>
                <td>JSON Body</td>
                <td>
                    <pre>${jsonBody}</pre>
                </td>
            </tr>

        </table>

        <br>

    <% } %>

    <h2>Response Information</h2>

    <table>

        <tr>
            <td>HTTP Status</td>
            <td class="success">
                ${statusCode}
            </td>
        </tr>

        <tr>
            <td>Execution Time (seconds)</td>
            <td>
                ${executionTime}
            </td>
        </tr>

        <tr>
            <td>Response Body</td>
            <td>
                <pre>${responseBody}</pre>
            </td>
        </tr>

    </table>

    <a class="button" href="index.jsp">
        Back To API Caller
    </a>

<% } %>

</body>
</html>