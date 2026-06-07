package contracts.transfer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Return 404 when transfer from non-existent-user'
    name 'shouldReturn404'

    request {
        method POST()
        url '/api/v1/transfer/non-existent-user'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                value: 123,
                recipient: 'test-user2'
        ])
    }

    response {
        status 404
    }

}