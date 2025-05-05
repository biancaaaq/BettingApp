import React, { useState } from 'react';
import api from '../services/api';

const AddDetaliuBilet: React.FC = () => {
    const [biletId, setBiletId] = useState('');
    const [cotaId, setCotaId] = useState('');
    const [predictie, setPredictie] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/detalii-bilete', {
                bilet: { id: parseInt(biletId) },
                cota: { id: parseInt(cotaId) },
                predictie
            });
            setSuccess('Detaliu bilet adăugat cu succes!');
            setError('');
            setBiletId('');
            setCotaId('');
            setPredictie('');
        } catch (err) {
            setError('Eroare la adăugarea detaliului bilet.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Detaliu Bilet</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID Bilet:</label>
                    <input
                        type="number"
                        value={biletId}
                        onChange={(e) => setBiletId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>ID Cotă:</label>
                    <input
                        type="number"
                        value={cotaId}
                        onChange={(e) => setCotaId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Predicție:</label>
                    <input
                        type="text"
                        value={predictie}
                        onChange={(e) => setPredictie(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Adaugă Detaliu Bilet</button>
            </form>
        </div>
    );
};

export default AddDetaliuBilet;