import React, { useState } from 'react';
import api from '../services/api';

const AddUtilizator: React.FC = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [rol, setRol] = useState('USER');
    const [activ, setActiv] = useState(true);
    const [dataCreare, setDataCreare] = useState(new Date().toISOString().slice(0, 19));
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/utilizatori', {
                numeUtilizator: username,
                email,
                parola: password,
                rol,
                activ,
                dataCreare
            });
            setSuccess('Utilizator adăugat cu succes!');
            setError('');
            setUsername('');
            setEmail('');
            setPassword('');
            setRol('USER');
            setActiv(true);
            setDataCreare(new Date().toISOString().slice(0, 19));
        } catch (err) {
            setError('Eroare la adăugarea utilizatorului.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Utilizator</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Parolă:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Rol:</label>
                    <select value={rol} onChange={(e) => setRol(e.target.value)}>
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div>
                    <label>Activ:</label>
                    <input
                        type="checkbox"
                        checked={activ}
                        onChange={(e) => setActiv(e.target.checked)}
                    />
                </div>
                <div>
                    <label>Data Creare:</label>
                    <input
                        type="datetime-local"
                        value={dataCreare}
                        onChange={(e) => setDataCreare(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Adaugă Utilizator</button>
            </form>
        </div>
    );
};

export default AddUtilizator;