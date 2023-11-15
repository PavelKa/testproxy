package example.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class TestProxySpec extends Specification {
    @Shared
    @AutoCleanup
    @Inject
    @Client("/")
    HttpClient client

    void "test proxy  304"() {
        given:
        def response = client.toBlocking().exchange(HttpRequest.GET("proxy/status/304"))
        expect:
        response.status == HttpStatus.NOT_MODIFIED

    }

    void "test proxy  200"() {
        when:
        def response = client.toBlocking().exchange(HttpRequest.GET("proxy/status/200"))
        then:
        response.status == HttpStatus.OK

    }

    void "test proxy x x 200"() {
        when:
        println id
        def response = client.toBlocking().exchange(HttpRequest.GET("proxy/status/200"))
        then:
        response.status == HttpStatus.OK
        where:
        id <<[1,2,3]


    }

}