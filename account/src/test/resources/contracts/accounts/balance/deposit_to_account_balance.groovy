package contracts.accounts.balance

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Deposit to the account balance'

    request {
        method POST()
        url '/api/v1/balance/deposit'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                toLogin: 'test-user',
                amount: 10
        ])
    }

    response {
        status OK()

        headers {
            contentType(applicationJson())
        }
        body(
                info: 'Внесение выполнено: 10 на счёт test-user'
        )
    }

}