import React, { useState } from 'react';
import api from '../services/api';

const AddRolUtilizator: React.FC = () => {
    const [grupId, setGrupId] = useState('');
    const [utilizatorId, setUtilizatorId] = useState('');
    const [activ, setActiv] = useState(true);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/rol-utilizatori', {
                grup: { id: parseInt(grupId) },
                utilizator: { id: parseInt(utilizatorId) },
                activ
            });
            setSuccess('Rol utilizator adăugat cu succes!');
            setError('');
            setGrupId('');
            setUtilizatorId('');
            setActiv(true);
        } catch (err) {
            setError('Eroare la adăugarea rolului utilizator.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Rol Utilizator</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID Grup:</label>
                    <input
                        type="number"
                        value={grupId}
                        onChange={(e) => setGrupId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>ID Utilizator:</label>
                    <input
                        type="number"
                        value={utilizatorId}
                        onChange={(e) => setUtilizatorId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Activ:</label>
                    <input
                        type="checkbox"
                        checked={activ}
                        onChange={(e) => setActiv(e.target.checked)}
                    />
                </div>
                <button type="submit">Adaugă Rol Utilizator</button>
            </form>
        </div>
    );
};

export default AddRolUtilizator;