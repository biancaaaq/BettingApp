import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login, register } from '../services/authService';
import '../design/Login.css';

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [isRegistering, setIsRegistering] = useState(false); // Comută între logare și înregistrare
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            await login(username, password);
            setError('');
            navigate('/home');
        } catch (err: any) {
            setError(err || 'Eroare la autentificare');
        }
    };

    const handleRegister = async () => {
        try {
            const response = await register(username, password, email);
            setSuccess(response);
            setError('');
            setIsRegistering(false); // Revine la formularul de logare după înregistrare
            setTimeout(() => setSuccess(''), 3000);
        } catch (err: any) {
            setError(err || 'Eroare la înregistrare');
            setSuccess('');
        }
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (isRegistering) {
            handleRegister();
        } else {
            handleLogin();
        }
    };

    return (
        <div className="login-container">
            <h2>{isRegistering ? 'Înregistrare' : 'Logare'}</h2>
            {error && <p className="error">{error}</p>}
            {success && <p className="success">{success}</p>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Parolă:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {isRegistering && (
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                )}
                <button type="submit">{isRegistering ? 'Înregistrează-te' : 'Loghează-te'}</button>
            </form>
            <button
                className="toggle-button"
                onClick={() => setIsRegistering(!isRegistering)}
            >
                {isRegistering ? 'Ai deja un cont? Loghează-te' : 'Nu ai cont? Înregistrează-te'}
            </button>
        </div>
    );
};

export default Login;