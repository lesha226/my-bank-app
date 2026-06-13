package contracts.accounts.balance

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Transfer between account balances'

    request {
        method POST()
        url '/api/v1/balance/transfer'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                fromLogin: 'test-user',
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
                info: 'Перевод выполнен: 10 со счёта test-user на счёт test-user2'
        )
    }

}