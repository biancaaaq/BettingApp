import axios from 'axios';

const API_URL = 'http://localhost:8081/api/auth';

interface AuthResponse {
    jwt: string;
}

export const register = async (username: string, password: string, email: string): Promise<string> => {
    try {
        const response = await axios.post(`${API_URL}/register`, { username, password, email });
        return response.data; // Returnează mesajul de succes (ex. "Utilizator înregistrat cu succes!")
    } catch (error: any) {
        console.error('Eroare la înregistrare:', error);
        throw error.response?.data || error.message || 'Eroare necunoscută';
    }
};

export const login = async (username: string, password: string): Promise<string> => {
    const response = await axios.post<AuthResponse>(`${API_URL}/login`, { username, password });
    const token = response.data.jwt;
    localStorage.setItem('token', token);
    const role = getRoleFromToken(token);
    localStorage.setItem('role', role || '');
    return token;
};

// export const logout = () => {
//     localStorage.removeItem('token');
//     localStorage.removeItem('role');
    
// };
export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("username");
  localStorage.removeItem("role");
};


export const getToken = (): string | null => {
    return localStorage.getItem('token');
};

export const getRole = (): string | null => {
    return localStorage.getItem('role');
};

export const getRoleFromToken = (token: string | null): string | null => {
    if (!token) return null;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const role = payload.role;
        return role.startsWith('ROLE_') ? role.substring(5) : role;
    } catch (e) {
        console.error('Eroare la extragerea rolului din token:', e);
        return null;
    }
};

export const getFullRoleFromToken = (token: string | null): string | null => {
    const role = getRoleFromToken(token);
    return role ? `ROLE_${role}` : null;
};