import React, { useState } from 'react';
import api from '../services/api';

const AddTranzactie: React.FC = () => {
    const [utilizatorId, setUtilizatorId] = useState('');
    const [tip, setTip] = useState('DEPOSIT');
    const [valoare, setValoare] = useState('');
    const [dataTranzactie, setDataTranzactie] = useState(new Date().toISOString().slice(0, 19));
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/tranzactii', {
                utilizator: { id: parseInt(utilizatorId) },
                tip,
                valoare: parseFloat(valoare),
                dataTranzactie
            });
            setSuccess('Tranzacție adăugată cu succes!');
            setError('');
            setUtilizatorId('');
            setTip('DEPOSIT');
            setValoare('');
            setDataTranzactie(new Date().toISOString().slice(0, 19));
        } catch (err) {
            setError('Eroare la adăugarea tranzacției.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Tranzacție</h2>
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
                    <label>Tip:</label>
                    <select value={tip} onChange={(e) => setTip(e.target.value)}>
                        <option value="DEPOSIT">DEPOSIT</option>
                        <option value="WITHDRAW">WITHDRAW</option>
                    </select>
                </div>
                <div>
                    <label>Valoare:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={valoare}
                        onChange={(e) => setValoare(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data Tranzacție:</label>
                    <input
                        type="datetime-local"
                        value={dataTranzactie}
                        onChange={(e) => setDataTranzactie(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Adaugă Tranzacție</button>
            </form>
        </div>
    );
};

export default AddTranzactie; 