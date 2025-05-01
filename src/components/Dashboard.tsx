import React, { useEffect, useState } from 'react';
import api from '../services/api';

interface Utilizator {
    id: number;
    numeUtilizator: string;
    email: string;
}

const Dashboard: React.FC = () => {
    const [utilizatori, setUtilizatori] = useState<Utilizator[]>([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUtilizatori = async () => {
            try {
                const response = await api.get('/utilizatori');
                setUtilizatori(response.data);
            } catch (err) {
                setError('Nu ai acces sau token-ul este invalid.');
            }
        };
        fetchUtilizatori();
    }, []);

    return (
        <div>
            <h2>Dashboard</h2>
            {error && <p>{error}</p>}
            <ul>
                {utilizatori.map((utilizator) => (
                    <li key={utilizator.id}>{utilizator.numeUtilizator} - {utilizator.email}</li>
                ))}
            </ul>
        </div>
    );
};

export default Dashboard;