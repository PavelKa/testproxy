package example.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class TestDirectSpec extends Specification {
    @Shared
    @AutoCleanup
    HttpClient client
    void setup() {
        client = HttpClient.create("https://httpbin.org".toURL())
        println "client crearted $client"
    }

    void "test direct  304"() {
        when:
        def response = client.toBlocking().exchange(HttpRequest.GET('/status/304'))
        then:
        HttpStatus.NOT_MODIFIED == response.getStatus()
        where:
        id <<[1,2,3,4,5]
    }

    void "test proxy 200"() {
        when:
        def response = client.toBlocking().exchange(HttpRequest.GET('/status/200'))
        then:
        HttpStatus.OK == response.getStatus()

    }


}