package contracts.transfer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Transfer from test-user to other account'

    request {
        method POST()
        url '/api/v1/transfer/test-user'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }

        body([
                toLogin: 'test-user2',
                amount: 10
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
