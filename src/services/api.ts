import axios from 'axios';
import { getToken } from './authService';

const api = axios.create({
    baseURL: 'http://localhost:8081',
});

api.interceptors.request.use(
    (config) => {
        const token = getToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;