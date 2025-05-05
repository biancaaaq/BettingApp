import React, { useState } from 'react';
import api from '../services/api';

const AddCerereAutoexcludere: React.FC = () => {
    const [utilizatorId, setUtilizatorId] = useState('');
    const [cerere, setCerere] = useState(new Date().toISOString().slice(0, 19));
    const [aprobat, setAprobat] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/cereri-autoexcludere', {
                utilizator: { id: parseInt(utilizatorId) },
                cerere,
                aprobat
            });
            setSuccess('Cerere de autoexcludere adăugată cu succes!');
            setError('');
            setUtilizatorId('');
            setCerere(new Date().toISOString().slice(0, 19));
            setAprobat(false);
        } catch (err) {
            setError('Eroare la adăugarea cererii de autoexcludere.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Cerere Autoexcludere</h2>
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
                    <label>Data Cerere:</label>
                    <input
                        type="datetime-local"
                        value={cerere}
                        onChange={(e) => setCerere(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Aprobat:</label>
                    <input
                        type="checkbox"
                        checked={aprobat}
                        onChange={(e) => setAprobat(e.target.checked)}
                    />
                </div>
                <button type="submit">Adaugă Cerere Autoexcludere</button>
            </form>
        </div>
    );
};

export default AddCerereAutoexcludere;