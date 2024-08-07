/*******************************************************************************
 * Copyright (C)2012-2024, German Federal Armed Forces - All rights reserved.
 *
 * MUKdo II
 * Wibbelhofstraße 3
 * 26384 Wilhelmshaven
 * Germany
 *
 * This source code is part of the MEDAS/SEDAP Project.
 * Person of contact (POC): Volker Voß, MUKdo II A, Wilhelmshaven
 *
 * Unauthorized use, modification, redistributing, copying, selling and
 * printing of this file in source and binary form including accompanying
 * materials is STRICTLY prohibited.
 *
 * This source code and it's parts is classified as OFFEN / NATO UNCLASSIFIED!
 *******************************************************************************/
package de.bundeswehr.sedap.express.mockup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.Instant;
import java.util.function.Function;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class WSVAIS2HTTP {

    public static void main(String[] args) throws IOException {

	HttpServerProvider provider = HttpServerProvider.provider();
	HttpServer server = provider.createHttpServer(new InetSocketAddress(9999), 64);
	HttpContext context = server.createContext("/AIS");
	context.getFilters().add(new TracingFilter());
	context.setHandler(WSVAIS2HTTP.respondWith(req -> HttpResponse.ok("Hello " + Instant.now()).text()));
	server.start();
    }

    static HttpHandler respondWith(HttpFunc hf) {
	return exchange -> {

	    HttpRequest req = HttpRequest.of(exchange);
	    HttpResponse res = hf.apply(req);

	    exchange.getResponseHeaders().putAll(res.headers());

	    try {
		exchange.sendResponseHeaders(res.status(), Long.MAX_VALUE);
		try (var os = exchange.getResponseBody()) {

		    while (true) {
			os.write((Instant.now() + "!AIVDM,2,1,6,A,53c9Sp02>JjE0PTp0004l5`tpD8u8L0000000016?p>:<4a80AS@DRCP,0*4F)").getBytes());
			Thread.sleep(250);
		    }
		} catch (IOException | InterruptedException e) {

		}
	    } catch (IOException e) {

	    }
	};
    }

    record HttpRequest(String method, URI requestUri, Headers headers, HttpExchange exchange) {

	static HttpRequest of(HttpExchange exchange) {
	    return new HttpRequest(
		    exchange.getRequestMethod(),
		    exchange.getRequestURI(),
		    exchange.getRequestHeaders(),
		    exchange);
	}
    }

    interface HttpFunc extends Function<HttpRequest, HttpResponse> {
    }

    static class TracingFilter extends Filter {

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
	    var req = HttpRequest.of(exchange);
	    System.out.println(req.toString());
	    chain.doFilter(exchange);
	}

	@Override
	public String description() {
	    return "Trace";
	}
    }

    record HttpResponse(int status, Headers headers, String body) {

	static HttpResponse ok() {
	    return HttpResponse.ok("");
	}

	static HttpResponse ok(String body) {
	    return new HttpResponse(200, new Headers(), body);
	}

	HttpResponse header(String name, String value) {
	    var res = new HttpResponse(status(), headers(), body());
	    res.headers().add(name, value);
	    return res;
	}

	HttpResponse json() {
	    return header("Content-type", "application/json");
	}

	HttpResponse text() {
	    return header("Content-type", "text/plain");
	}
    }
}