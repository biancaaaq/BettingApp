import React, { useState } from 'react';
import api from '../services/api';

const AddBilet: React.FC = () => {
    const [utilizatorId, setUtilizatorId] = useState('');
    const [grupId, setGrupId] = useState('');
    const [cotaTotala, setCotaTotala] = useState('');
    const [miza, setMiza] = useState('');
    const [castigPotential, setCastigPotential] = useState('');
    const [status, setStatus] = useState('ACTIVE');
    const [dataCreare, setDataCreare] = useState(new Date().toISOString().slice(0, 19));
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/bilete', {
                utilizator: { id: parseInt(utilizatorId) },
                grup: grupId ? { id: parseInt(grupId) } : null,
                cotaTotala: parseFloat(cotaTotala),
                miza: parseFloat(miza),
                castigPotential: parseFloat(castigPotential),
                status,
                dataCreare
            });
            setSuccess('Bilet adăugat cu succes!');
            setError('');
            setUtilizatorId('');
            setGrupId('');
            setCotaTotala('');
            setMiza('');
            setCastigPotential('');
            setStatus('ACTIVE');
            setDataCreare(new Date().toISOString().slice(0, 19));
        } catch (err) {
            setError('Eroare la adăugarea biletului.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Bilet</h2>
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
                    <label>ID Grup (opțional):</label>
                    <input
                        type="number"
                        value={grupId}
                        onChange={(e) => setGrupId(e.target.value)}
                    />
                </div>
                <div>
                    <label>Cotă Totală:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={cotaTotala}
                        onChange={(e) => setCotaTotala(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Miză:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={miza}
                        onChange={(e) => setMiza(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Câștig Potențial:</label>
                    <input
                        type="number"
                        step="0.01"
                        value={castigPotential}
                        onChange={(e) => setCastigPotential(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Status:</label>
                    <select value={status} onChange={(e) => setStatus(e.target.value)}>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="WON">WON</option>
                        <option value="LOST">LOST</option>
                    </select>
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
                <button type="submit">Adaugă Bilet</button>
            </form>
        </div>
    );
};

export default AddBilet;