import React, { useState } from 'react';
import api from '../services/api';

const AddMeci: React.FC = () => {
    const [echipaAcasa, setEchipaAcasa] = useState('');
    const [echipaDeplasare, setEchipaDeplasare] = useState('');
    const [dataMeci, setDataMeci] = useState(new Date().toISOString().slice(0, 19));
    const [competitie, setCompetitie] = useState('');
    const [rezultat, setRezultat] = useState('');
    const [blocat, setBlocat] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/meciuri', {
                echipaAcasa,
                echipaDeplasare,
                dataMeci,
                competitie,
                rezultat,
                blocat
            });
            setSuccess('Meci adăugat cu succes!');
            setError('');
            setEchipaAcasa('');
            setEchipaDeplasare('');
            setDataMeci(new Date().toISOString().slice(0, 19));
            setCompetitie('');
            setRezultat('');
            setBlocat(false);
        } catch (err) {
            setError('Eroare la adăugarea meciului.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Meci</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Echipa Acasă:</label>
                    <input
                        type="text"
                        value={echipaAcasa}
                        onChange={(e) => setEchipaAcasa(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Echipa Deplasare:</label>
                    <input
                        type="text"
                        value={echipaDeplasare}
                        onChange={(e) => setEchipaDeplasare(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data Meci:</label>
                    <input
                        type="datetime-local"
                        value={dataMeci}
                        onChange={(e) => setDataMeci(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Competiție:</label>
                    <input
                        type="text"
                        value={competitie}
                        onChange={(e) => setCompetitie(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Rezultat:</label>
                    <input
                        type="text"
                        value={rezultat}
                        onChange={(e) => setRezultat(e.target.value)}
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
                <button type="submit">Adaugă Meci</button>
            </form>
        </div>
    );
};

export default AddMeci;