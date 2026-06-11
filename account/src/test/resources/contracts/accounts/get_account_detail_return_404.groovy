package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Return 404 when nonUser'

    request {
        method GET()
        url '/api/v1/accounts/nonUser'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
        }
    }

    response {
        status NOT_FOUND()
    }

}
