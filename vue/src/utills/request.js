import axios from "axios";

const request = axios.create({
    baseURL : 'http://localhost:9090/',
    timeout : 5000
});

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token

    return config;
    }, error => {
    return Promise.reject();
})

request.interceptors.response.use(
    response => {
        let res = response.data;

        if (response.config.responseType == "blob"){
            return res;
        }

        if (typeof res === "string"){
            res = res ? JSON.parse(res) : res;
        }

        return res;
    },
    error => {
        console.log('error = ' + error);
        return Promise.reject(error);
    }
);

export default request