package contracts.cash

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Put cash to account'
    name "action_put"

    request {
        method POST()
        url '/api/v1/cash/test-user/action'
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
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                info: 'message'
        )
    }

}
