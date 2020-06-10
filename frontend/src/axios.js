import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080/api'
});
// axios.defaults.headers.common['Authorization'] = 'AUTH TOKEN';

axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.defaults.headers.common['Accept'] = 'application/json'
// axios.interceptors.request.use(request => {
//     console.log(request);
//     // Edit request config
//     return request;
// }, error => {
//     console.log(error);
//     return Promise.reject(error);
// });


// instance.interceptors.request...

export default instance;