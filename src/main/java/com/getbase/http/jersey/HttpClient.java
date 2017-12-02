package com.getbase.http.jersey;

import com.getbase.Configuration;
import com.getbase.exceptions.ConnectionException;
import com.getbase.http.Request;
import com.getbase.http.Response;
import com.getbase.utils.Joiner;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class HttpClient extends com.getbase.http.HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private javax.ws.rs.client.Client client;

    public HttpClient(Configuration config) {
        this(config, new DefaultBuilder());
    }

    public HttpClient(Configuration config, Builder builder) {
        super(config);

        this.client = builder.build(this.config);
    }

    @Override
    public Response rawRequest(Request request) {
        if (!request.getMethod().isBodySupported() && request.getBody() != null) {
            throw new IllegalArgumentException("Provided HTTP method is not allowed to send body");
        }

        // set target url
        WebTarget webTarget = client.target(request.getUrl());

        // append query parameters
        for (Map.Entry<String, Object> q : request.getQueryParameters().entrySet()) {
            webTarget = webTarget.queryParam(q.getKey(), q.getValue());
        }

        // create jersey request
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        // assign headers
        for (Map.Entry<String, String> h : request.getHeaders().entrySet()) {
            invocationBuilder.header(h.getKey(), h.getValue());
        }

        // perform request
        Invocation invocation;
        javax.ws.rs.core.Response jerseyResponse = null;

        if (request.getBody() != null && !request.getBody().isEmpty() && request.getMethod().isBodySupported()) {
            invocation = invocationBuilder.build(request.getMethod().name(), Entity.json(request.getBody()));
        } else {
            invocation = invocationBuilder.build(request.getMethod().name());
        }

        try {
            final long start = currentTimeMillis();
            try {
                jerseyResponse = invocation.invoke();

                if (log.isDebugEnabled()) {
                    log.debug("Received HTTP {} for {} {} after {} milliseconds", jerseyResponse.getStatus(),
                            request.getMethod(), request.getUrl(), currentTimeMillis() - start);
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Received exception {} for {} {} after {} milliseconds", e,  request.getMethod(),
                            request.getUrl(), currentTimeMillis() - start);
                }
                throw new ConnectionException(e);
            }

            // flatten response headers
            Map<String, String> responseHeaders = new HashMap<String, String>();
            for (Map.Entry<String, List<String>> h : jerseyResponse.getStringHeaders().entrySet()) {
                responseHeaders.put(h.getKey(), Joiner.join(";", h.getValue()));
            }

            // prepare basecrm.http response
            Response response = new Response(jerseyResponse.getStatus(),
                    jerseyResponse.readEntity(String.class),
                    responseHeaders);

            return response;
        } finally {
            if (jerseyResponse != null) {
                jerseyResponse.close();
            }
        }
    }

    protected static class DefaultBuilder implements Builder {

        public Client build(final Configuration config) {
            // setup client
            ClientConfig clientConfig = new ClientConfig();

            if (config.isVerbose()) {
                clientConfig.register(new LoggingFilter(logger("jersey"), false));
            }

            clientConfig.property(ClientProperties.CONNECT_TIMEOUT, config.getTimeout() * 1000);
            clientConfig.property(ClientProperties.READ_TIMEOUT, config.getTimeout() * 1000);

            javax.ws.rs.client.Client client;

            if (config.isVerifySSL()) {
                client = ClientBuilder.newBuilder().withConfig(clientConfig).hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return config.isVerifySSL();
                    }
                }).build();
            } else {
                client = ClientBuilder.newClient(clientConfig);
            }

            return client;
        }

        private java.util.logging.Logger logger(String name) {
            return new java.util.logging.Logger(name, null) {
                @Override
                public void info(String msg) {
                    log.info(msg);
                }
            };
        }
    }
}
