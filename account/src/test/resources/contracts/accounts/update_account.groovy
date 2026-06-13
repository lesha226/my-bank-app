package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'update account by test-user'

    request {
        method PATCH()
        url '/api/v1/account/user'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                name: 'fullName',
                birthdate: '2001-01-01'
        ])
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                info: "message"
        ])
    }

}
