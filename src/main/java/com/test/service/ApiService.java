package com.test.service;

import com.test.model.ApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class ApiService {

    public ApiResponse callApi(
            String url,
            String method,
            String headers,
            String jsonBody,
            boolean ignoreSsl)
            throws Exception {

        HttpClient client =
                createHttpClient(ignoreSsl);

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(60));

        addHeaders(builder, headers);

        switch (method) {

            case "GET":

                builder.GET();
                break;

            case "POST":

                builder.POST(
                        HttpRequest.BodyPublishers.ofString(
                                jsonBody == null
                                        ? ""
                                        : jsonBody));
                break;

            case "PUT":

                builder.PUT(
                        HttpRequest.BodyPublishers.ofString(
                                jsonBody == null
                                        ? ""
                                        : jsonBody));
                break;

            case "DELETE":

                builder.method(
                        "DELETE",
                        HttpRequest.BodyPublishers.ofString(
                                jsonBody == null
                                        ? ""
                                        : jsonBody));
                break;

            case "PATCH":

                builder.method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(
                                jsonBody == null
                                        ? ""
                                        : jsonBody));
                break;
        }

        HttpRequest request =
                builder.build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        try {
            body = com.test.util.JsonUtil.prettyPrint(body);
        } catch (Exception e) {
            
        }

        return new ApiResponse(
                response.statusCode(),
                body);
    }

    private HttpClient createHttpClient(
            boolean ignoreSsl)
            throws Exception {

        if (!ignoreSsl) {

            return HttpClient.newBuilder()
                    .connectTimeout(
                            Duration.ofSeconds(30))
                    .build();
        }

        TrustManager[] trustAllCerts =
                new TrustManager[] {

                        new X509TrustManager() {

                            @Override
                            public void checkClientTrusted(
                                    X509Certificate[] chain,
                                    String authType) {
                            }

                            @Override
                            public void checkServerTrusted(
                                    X509Certificate[] chain,
                                    String authType) {
                            }

                            @Override
                            public X509Certificate[]
                            getAcceptedIssuers() {

                                return new X509Certificate[0];
                            }
                        }
                };

        SSLContext sslContext =
                SSLContext.getInstance("TLS");

        sslContext.init(
                null,
                trustAllCerts,
                new SecureRandom());

        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .connectTimeout(
                        Duration.ofSeconds(30))
                .build();
    }

    private void addHeaders(
            HttpRequest.Builder builder,
            String headers) {

        if (headers == null
                || headers.trim().isEmpty()) {

            return;
        }

        String[] lines =
                headers.split("\\r?\\n");

        for (String line : lines) {

            int index =
                    line.indexOf(":");

            if (index <= 0) {

                continue;
            }

            String headerName =
                    line.substring(
                            0,
                            index)
                            .trim();

            String headerValue =
                    line.substring(
                            index + 1)
                            .trim();

            builder.header(
                    headerName,
                    headerValue);
        }
    }
}