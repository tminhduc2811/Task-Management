import axios from 'axios';
import { authenticationService } from './Services/AuthenticationService';

const instance = axios.create({
    baseURL: 'http://localhost:8080/api'
});
// axios.defaults.headers.common['Authorization'] = 'AUTH TOKEN';

instance.defaults.headers.common['Content-Type'] = 'application/json';
instance.defaults.headers.common['Accept'] = 'application/json'
instance.interceptors.request.use(request => {
    console.log(request);
    if (authenticationService.authInfoValue) {
        request.headers.common['Authorization'] = 'Bearer ' + authenticationService.authInfoValue.token;
        console.log('here ', authenticationService.authInfoValue)
    }
    return request;
}, error => {
    console.log(error);
    return Promise.reject(error);
});


// instance.interceptors.request...

export default instance;