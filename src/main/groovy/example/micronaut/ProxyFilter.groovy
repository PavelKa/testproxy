package example.micronaut

import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.client.ProxyHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.uri.UriBuilder
import org.reactivestreams.Publisher

@Filter("/proxy/**")
class ProxyFilter implements HttpServerFilter { // (1)
    private final ProxyHttpClient client

    ProxyFilter( ProxyHttpClient client
    ) {
        this.client = client

    }


    @Override
    Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                               ServerFilterChain chain) {
        Publishers.map(client.proxy( // (3)
                request.mutate() // (4)
                        .uri { UriBuilder b -> // (5)
                            b.with {
                                scheme("https")
                                host('httpbin.org')
                                port(443)
                                replacePath(request.path.substring("/proxy".length()))
                            }
                        }
                        .header("X-My-Request-Header", "XXX")

        ), { it.header("X-My-Response-Header", "YYY") })


    }
}