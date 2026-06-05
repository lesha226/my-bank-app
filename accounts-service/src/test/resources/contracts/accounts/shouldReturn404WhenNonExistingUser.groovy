package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Return 404 when non-existent-user'
    name 'shouldReturn404WhenNonExistingUser'

    request {
        method GET()
        url '/api/v1/accounts/non-existent-user'
    }

    response {
        status 404
    }

}
