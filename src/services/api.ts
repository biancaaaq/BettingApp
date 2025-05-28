import axios from 'axios';
import { getToken } from './authService';

const api = axios.create({
    baseURL: 'http://localhost:8081',
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        const token = getToken();
        console.log('Token folosit în cerere:', token);
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            console.warn('Niciun token găsit în localStorage');
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 403 && error.response.data === 'Contul este autoexclus') {
            console.log('Cont auto-exclus detectat, redirecționare la /cont-autoexclus');
            window.location.href = '/cont-autoexclus';
            return Promise.reject(new Error('Contul este autoexclus'));
        }
        if (error.response && error.response.status === 401) {
            console.log('Token expirat, redirecționare la /login');
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            window.location.href = '/login';
            return Promise.reject(new Error('Token expirat, te rugăm să te loghezi din nou'));
        }
        if (error.response && error.response.status === 403) {
            console.log('Acces interzis (403), posibil rol incorect:', error.response.data);
        }
        return Promise.reject(error);
    }
);

export default api;