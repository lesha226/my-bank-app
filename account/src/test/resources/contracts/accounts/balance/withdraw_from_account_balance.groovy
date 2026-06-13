package contracts.accounts.balance

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Withdraw from account balance'

    request {
        method POST()
        url '/api/v1/balance/withdraw'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                fromLogin: 'test-user',
                amount: 10
        ])
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                info: 'Снятие выполнено: 10 со счёта test-user'
        )
    }

}