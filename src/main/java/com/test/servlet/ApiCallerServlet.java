package com.test.servlet;
import com.test.model.ApiResponse;
import com.test.service.ApiService;
import java.io.IOException;
import java.net.ConnectException;

import java.net.http.HttpTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import java.net.URI;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/apiCaller")
public class ApiCallerServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String url =
                request.getParameter("url");

        String method =
                request.getParameter("method");

        String headers =
                request.getParameter("headers");

        String jsonBody =
                request.getParameter("jsonBody");

        boolean ignoreSsl =
                request.getParameter("ignoreSsl") != null;

        boolean verbose =
                request.getParameter("verbose") != null;

        String errorMessage = validateInput(url, method);

        if (errorMessage != null) {

            request.setAttribute(
                    "errorMessage",
                    errorMessage);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/result.jsp");

            dispatcher.forward(request, response);

            return;
        }
         request.setAttribute("url", url);
        request.setAttribute("method", method);
        request.setAttribute("headers", headers);
        request.setAttribute("jsonBody", jsonBody);
        request.setAttribute("ignoreSsl", ignoreSsl);
        request.setAttribute("verbose", verbose);

        long startTime =
                System.currentTimeMillis();

        try {

            ApiService apiService =
                    new ApiService();

            ApiResponse apiResponse =
                    apiService.callApi(
                            url,
                            method,
                            headers,
                            jsonBody,
                            ignoreSsl);

            long endTime =
                    System.currentTimeMillis();
            
            long durationMs = endTime - startTime;

            double durationSeconds = durationMs / 1000.0;

            request.setAttribute(
                    "executionTime",
                    String.format("%.2f", durationSeconds));
          

            request.setAttribute(
                    "statusCode",
                    apiResponse.getStatusCode());

            request.setAttribute(
                    "responseBody",
                    apiResponse.getResponseBody());

        }
        catch (HttpTimeoutException ex) {

            request.setAttribute(
                    "errorMessage",
                    "Request timeout occurred.");
        }
        catch (ConnectException ex) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to connect to target server.");
        }
        catch (SSLHandshakeException ex) {

            request.setAttribute(
                    "errorMessage",
                    "SSL handshake failed.");
        }
        catch (Exception ex) {

            request.setAttribute(
                    "errorMessage",
                    ex.getMessage());
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/result.jsp");

        dispatcher.forward(request, response);
    }

    private String validateInput(
            String url,
            String method) {

        if (url == null || url.trim().isEmpty()) {

            return "URL is required.";
        }

        if (!(url.startsWith("http://")
                || url.startsWith("https://"))) {

            return "URL must start with http:// or https://";
        }

        try {

            new URI(url);

        } catch (Exception ex) {

            return "Invalid URL format.";
        }

        if (method == null
                || method.trim().isEmpty()) {

            return "HTTP Method is required.";
        }

        switch (method) {

            case "GET":
            case "POST":
            case "PUT":
            case "DELETE":
            case "PATCH":
                break;

            default:
                return "Unsupported HTTP Method.";
        }

        return null;
    }
}