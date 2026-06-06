package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

import java.time.LocalDate

Contract.make {
    description 'update account by test-user'
    name 'shouldReturnUpdatedAccountForTestUser'

    request {
        method PATCH()
        url '/api/v1/accounts/test-user'
        headers {
            contentType(applicationJson())
        }
        body([
                name: 'Test user',
                birthdate: '2001-01-01'
        ])
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                login: 'test-user',
                name: 'Test user',
                birthdate: '2001-01-01',
                balanceAmount: 123,
                contacts: [
                        [
                                login: 'test-user1',
                                name: 'Test user1'
                        ],
                        [
                                login: 'test-user2',
                                name: 'Test user2'
                        ]
                ]
        ])
    }

}
