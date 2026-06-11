package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Get account by user'

    request {
        method GET()
        url '/api/v1/account/user/detail'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                login: 'user',
                name: 'name',
                birthdate: '2001-01-01',
                balanceAmount: 100,
                contacts: [
                        [
                                login: 'user2',
                                name: 'fullName2'
                        ],
                        [
                                login: 'user3',
                                name: 'fullName3'
                        ]
                ]
        )
    }

}
