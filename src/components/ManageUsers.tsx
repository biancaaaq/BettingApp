import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../design/ManageUsers.css';

interface Utilizator {
    id: number;
    numeUtilizator: string;
    email: string;
    rol: string;
    activ: boolean;
    dataCreare: string;
}

const ManageUsers: React.FC = () => {
    const [utilizatori, setUtilizatori] = useState<Utilizator[]>([]);
    const [balante, setBalante] = useState<{ [key: number]: number }>({});
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUtilizatori = async () => {
            try {
                const response = await api.get('/utilizatori');
                setUtilizatori(response.data);

                const balanteTemp: { [key: number]: number } = {};
                for (const utilizator of response.data) {
                    try {
                        const balantaResponse = await api.get(`/tranzactii/balanta?utilizatorId=${utilizator.id}`);
                        balanteTemp[utilizator.id] = balantaResponse.data;
                    } catch (err: any) {
                        console.error(`Eroare la obținerea balanței pentru utilizator ${utilizator.id}:`, err);
                        balanteTemp[utilizator.id] = 0;
                    }
                }
                setBalante(balanteTemp);
            } catch (err: any) {
                const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
                console.error('Eroare la încărcarea utilizatorilor:', errorMessage);
                setError(`Eroare la încărcarea utilizatorilor: ${errorMessage}`);
            }
        };
        fetchUtilizatori();
    }, []);

    const handleReactiveaza = async (id: number) => {
        try {
            console.log(`Încerc să reactivez utilizatorul cu ID ${id}`);
            const response = await api.put(`/utilizatori/activare/${id}`);
            console.log('Răspuns reactivare:', response.data);
            setSuccess(response.data);
            setUtilizatori(
                utilizatori.map((utilizator) =>
                    utilizator.id === id ? { ...utilizator, activ: true } : utilizator
                )
            );
            setTimeout(() => setSuccess(''), 2000);
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la reactivare:', errorMessage);
            setError(`Eroare la reactivare: ${errorMessage}`);
        }
    };

    const handleInapoi = () => {
        navigate('/admin');
    };

    return (
        <div className="manage-users-container">
            <h2>Gestionare Utilizatori</h2>
            {error && <p className="error">{error}</p>}
            {success && <p className="success">{success}</p>}
            {utilizatori.length > 0 ? (
                <table className="users-table">
                    <thead>
                        <tr>
                            <th>Nume Utilizator</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Activ</th>
                            <th>Data Creare</th>
                            <th>Balanță (RON)</th>
                            <th>Acțiuni</th>
                        </tr>
                    </thead>
                    <tbody>
                        {utilizatori.map((utilizator) => (
                            <tr key={utilizator.id}>
                                <td>{utilizator.numeUtilizator}</td>
                                <td>{utilizator.email}</td>
                                <td>{utilizator.rol}</td>
                                <td>{utilizator.activ ? 'Da' : 'Nu'}</td>
                                <td>{new Date(utilizator.dataCreare).toLocaleString()}</td>
                                <td>{(balante[utilizator.id] || 0).toFixed(2)}</td>
                                <td>
                                    {!utilizator.activ && (
                                        <button onClick={() => handleReactiveaza(utilizator.id)}>
                                            Reactivează
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>Nu există utilizatori.</p>
            )}
            <div className="action-buttons">
                <button onClick={handleInapoi}>Înapoi</button>
            </div>
        </div>
    );
};

export default ManageUsers;