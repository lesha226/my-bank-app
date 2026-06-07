package contracts.cash

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Return 404 when put 123 for non-existent-user'
    name 'shouldReturn404WhenPut123ForNonExistingUser'

    request {
        method POST()
        url '/api/v1/cash/non-existent-user/action'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                value: 123,
                action: 'PUT'
        ])
    }

    response {
        status 404
    }

}