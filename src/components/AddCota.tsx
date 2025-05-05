import React, { useState } from 'react';
import api from '../services/api';

const AddCota: React.FC = () => {
    const [meciId, setMeciId] = useState('');
    const [descriere, setDescriere] = useState('');
    const [valoare, setValoare] = useState('');
    const [blocat, setBlocat] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/cote', {
                meci: { id: parseInt(meciId) },
                descriere,
                valoare: parseFloat(valoare),
                blocat
            });
            setSuccess('Cotă adăugată cu succes!');
            setError('');
            setMeciId('');
            setDescriere('');
            setValoare('');
            setBlocat(false);
        } catch (err) {
            setError('Eroare la adăugarea cotei.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Cotă</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID Meci:</label>
                    <input
                        type="number"
                        value={meciId}
                        onChange={(e) => setMeciId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Descriere:</label>
                    <input
                        type="text"
                        value={descriere}
                        onChange={(e) => setDescriere(e.target.value)}
                        required
                    />
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
                    <label>Blocat:</label>
                    <input
                        type="checkbox"
                        checked={blocat}
                        onChange={(e) => setBlocat(e.target.checked)}
                    />
                </div>
                <button type="submit">Adaugă Cotă</button>
            </form>
        </div>
    );
};

export default AddCota;