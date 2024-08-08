/**
 * Note: This license has also been called the “Simplified BSD License” and the “FreeBSD License”.
 *
 * Copyright 2024 MESE POC: Volker Voß, Federal Armed Forces of Germany
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSEnARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BEn LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package de.bundeswehr.sedap.express.mockup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.function.Function;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class SEDAPExpressMockUpRESTServer {

    public static void main(String[] args) throws IOException {

	HttpServerProvider provider = HttpServerProvider.provider();
	HttpServer server = provider.createHttpServer(new InetSocketAddress(9999), 64);
	HttpContext context = server.createContext("/hello");
	context.getFilters().add(new TracingFilter());
	context.setHandler(SEDAPExpressMockUpRESTServer.respondWith(req -> HttpResponse.ok("Hello " + Instant.now()).text()));
	server.start();
    }

    static HttpHandler respondWith(HttpFunc hf) {
	return exchange -> {

	    var req = HttpRequest.of(exchange);
	    var res = hf.apply(req);

	    var bytes = res.body().getBytes(StandardCharsets.UTF_8);
	    exchange.getResponseHeaders().putAll(res.headers());

	    try {
		exchange.sendResponseHeaders(res.status(), bytes.length);
		try (var os = exchange.getResponseBody()) {
		    os.write(bytes);
		}
	    } catch (IOException e) {
		e.printStackTrace();
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