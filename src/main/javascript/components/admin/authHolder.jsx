export default class AuthHolder {
    constructor() {
        this.isAuthenticated = false;
    }

    authenticate(username, password) {
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        return fetch('/api/admin/login', {
            method: 'POST',
            body: formData,
            credentials: 'include',
        }).then((response) => {
            if (response.status !== 204) {
                return false;
            }
            this.isAuthenticated = true;
            return true;
        });
    }

    unauthenticate() {
        this.isAuthenticated = false;
    }
}
