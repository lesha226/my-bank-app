package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Return 404 when update non-existent-user'
    name 'shouldReturn404WhenUpdateNonExistingUser'

    request {
        method PATCH()
        url '/api/v1/accounts/non-existent-user'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),   // для консьюмера (WireMock): любой Bearer-токен
                    producer('Bearer test-token')  // для провайдера (MockMvc-тест): ровно этот токен
            )
            contentType(applicationJson())
        }
        body([
                name: 'Test user',
                birthdate: '2001-01-01'
        ])
    }

    response {
        status 404
    }

}