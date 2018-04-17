function assertSuccessResponseAndParse(response) {
    if (response.status === 200) {
        return response.json();
    }
    throw new Error(`API responded with: ${response.status} and content: ${response.content}`);
}

function assertNoContentResponse(response) {
    if (response.status !== 204) {
        throw new Error(`API responded with: ${response.status} and content: ${response.content}`);
    }
}

/**
 * Responsible for making API calls, parsing responses, and some basic error handling.
 *
 * All API calls should go through this class.
 */
export default class Api {
    constructor(authHolder, onUnauthenticated) {
        this.authHolder = authHolder;
        this.onUnauthenticated = onUnauthenticated;
    }

    get(path) {
        return fetch(path, { credentials: 'include' })
            .then(response => this.commonErrorHandling(response))
            .then(assertSuccessResponseAndParse);
    }

    post(path, body) {
        return fetch(path, {
            credentials: 'include',
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                accept: 'application/json',
                'content-type': 'application/json',
            },
        })
            .then(response => this.commonErrorHandling(response))
            .then(assertSuccessResponseAndParse);
    }

    del(path) {
        return fetch(path, {
            credentials: 'include',
            method: 'DELETE',
        })
            .then(response => this.commonErrorHandling(response))
            .then(assertNoContentResponse);
    }

    commonErrorHandling(response) {
        if (response.status === 401) {
            this.authHolder.unauthenticate();
            this.onUnauthenticated();

            // This is a bit of a hack - this error will never be caught as onUnauthenticated
            // triggers a state update in the app.
            throw new Error('User unauthenticated');
        } else if (response.status === 409) {
            throw new Error('Cannot delete this item as it is being used somewhere else.  You ' +
                'might get this error if, for example, you try and delete a film which already ' +
                'has some showings.');
        } else if (response.status === 404) {
            throw new Error('This item no longer exists!  You should try reloading the page.');
        }

        return response;
    }
}
