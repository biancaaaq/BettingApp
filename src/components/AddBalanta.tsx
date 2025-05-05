import React, { useState } from 'react';
import api from '../services/api';

const AddBalanta: React.FC = () => {
    const [utilizatorId, setUtilizatorId] = useState('');
    const [suma, setSuma] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/balanta', {
                utilizator: { id: parseInt(utilizatorId) },
                suma: parseFloat(suma)
            });
            setSuccess('Balanță adăugată cu succes!');
            setError('');
            setUtilizatorId('');
            setSuma('');
        } catch (err) {
            setError('Eroare la adăugarea balanței.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Balanță</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
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
                    <label>Sumă:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={suma}
                        onChange={(e) => setSuma(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Adaugă Balanță</button>
            </form>
        </div>
    );
};

export default AddBalanta;