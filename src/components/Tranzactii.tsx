import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { getToken } from '../services/authService';
import '../design/Tranzactii.css';

interface Tranzactie {
    id: number;
    tip: string;
    valoare: number;
    dataTranzactie: string;
}


const Tranzactii: React.FC = () => {
    const [valoare, setValoare] = useState('');
    const [balanta, setBalanta] = useState<number | null>(null);
    const [tranzactii, setTranzactii] = useState<Tranzactie[]>([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBalanta = async () => {
            try {
                const response = await api.get('/tranzactii/balanta');
                setBalanta(response.data);
            } catch (err: any) {
                setError('Eroare la încărcarea balanței: ' + (err.response?.data || err.message));
            }
        };

        const fetchTranzactii = async () => {
            try {
                const response = await api.get('/tranzactii/utilizator');
                setTranzactii(response.data);
            } catch (err: any) {
                setError('Eroare la încărcarea tranzacțiilor: ' + (err.response?.data || err.message));
            }
        };

        fetchBalanta();
        fetchTranzactii();
    }, []);

    const handleDepunere = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            console.log('Token trimis pentru depunere:', getToken());
            const response = await api.post('/tranzactii/depunere', {
                valoare: parseFloat(valoare),
            });
            console.log('Răspuns de la server (depunere):', response.data);
            setSuccess('Depunere realizată cu succes!');
            setValoare('');
            // Reîncarcă balanța și tranzacțiile
            const balantaResponse = await api.get('/tranzactii/balanta');
            setBalanta(balantaResponse.data);
            const tranzactiiResponse = await api.get('/tranzactii/utilizator');
            setTranzactii(tranzactiiResponse.data);
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la depunere:', errorMessage);
            setError(`Eroare la depunere: ${errorMessage}`);
        }
    };

    const handleRetragere = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            console.log('Token trimis pentru retragere:', getToken());
            const response = await api.post('/tranzactii/retragere', {
                valoare: parseFloat(valoare),
            });
            console.log('Răspuns de la server (retragere):', response.data);
            setSuccess('Retragere realizată cu succes!');
            setValoare('');
            // Reîncarcă balanța și tranzacțiile
            const balantaResponse = await api.get('/tranzactii/balanta');
            setBalanta(balantaResponse.data);
            const tranzactiiResponse = await api.get('/tranzactii/utilizator');
            setTranzactii(tranzactiiResponse.data);
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la retragere:', errorMessage);
            setError(`Eroare la retragere: ${errorMessage}`);
        }
    };

    const handleInapoi = () => {
        navigate('/home');
    };

    return (
        <div className="tranzactii-container">
            <div className="tranzactii-form">
                <h2>Depunere și Retragere Bani</h2>
                {error && <p className="error">{error}</p>}
                {success && <p className="success">{success}</p>}
                <h3>Balanța curentă: {balanta !== null ? balanta.toFixed(2) : 'Încărcare...'} RON</h3>
                <form onSubmit={handleDepunere}>
                    <div className="form-group">
                        <label>Sumă (RON):</label>
                        <input
                            type="number"
                            value={valoare}
                            onChange={(e) => setValoare(e.target.value)}
                            required
                            step="0.01"
                            min="0.01"
                        />
                    </div>
                    <div className="action-buttons">
                        <button type="submit">Depune</button>
                        <button type="button" onClick={handleRetragere}>Extrage</button>
                        <button type="button" onClick={handleInapoi}>Înapoi</button>
                    </div>
                </form>
            </div>
            <div className="tranzactii-list">
                <h2>Istoric Tranzacții</h2>
                {tranzactii.length > 0 ? (
                    <table className="tranzactii-table">
                        <thead>
                            <tr>
                                <th>Tip</th>
                                <th>Valoare (RON)</th>
                                <th>Data</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tranzactii.map((tranzactie) => (
                                <tr key={tranzactie.id}>
                                    <td>{tranzactie.tip === 'DEPOSIT' ? 'Depunere' : 'Retragere'}</td>
                                    <td>{tranzactie.valoare.toFixed(2)}</td>
                                    <td>{new Date(tranzactie.dataTranzactie).toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>Nu există tranzacții.</p>
                )}
            </div>
        </div>
    );
};

export default Tranzactii;