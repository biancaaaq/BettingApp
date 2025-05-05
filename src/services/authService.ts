import axios from 'axios';

const API_URL = 'http://localhost:8081/api/auth';

interface AuthResponse {
    jwt: string;
}

export const register = async (username: string, password: string, email: string) => {
    return await axios.post(`${API_URL}/register`, { username, password, email });
};

export const login = async (username: string, password: string): Promise<string> => {
    const response = await axios.post<AuthResponse>(`${API_URL}/login`, { username, password });
    const token = response.data.jwt;
    localStorage.setItem('token', token);
    return token;
};

export const logout = () => {
    localStorage.removeItem('token');
};

export const getToken = (): string | null => {
    return localStorage.getItem('token');
};

export const getRole = (): string | null => {
    const token = getToken();
    if (!token) return null;
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
};