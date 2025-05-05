import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../design/CreateTicket.css';

interface Meci {
    id: number;
    echipaAcasa: string;
    echipaDeplasare: string;
    dataMeci: string;
    competitie: string;
    rezultat?: string;
    blocat: boolean;
}

interface Cota {
    id: number;
    descriere: string;
    valoare: number;
}

const CreateTicket: React.FC = () => {
    const [meciuri, setMeciuri] = useState<Meci[]>([]);
    const [selectedCote, setSelectedCote] = useState<Cota[]>([]);
    const [miza, setMiza] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMeciuri = async () => {
            try {
                const response = await api.get('/meciuri');
                setMeciuri(response.data);
            } catch (err: any) {
                setError('Eroare la încărcarea meciurilor: ' + (err.response?.data || err.message));
            }
        };
        fetchMeciuri();
    }, []);

    const handleAddCota = (cota: Cota) => {
        setSelectedCote([...selectedCote, cota]);
    };

    const handleCreateTicket = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            if (selectedCote.length === 0) {
                throw new Error('Selectează cel puțin o cotă pentru a crea biletul.');
            }
            const response = await api.post('/bilete', {
                miza: parseFloat(miza),
                cotaTotala: selectedCote.reduce((total, cota) => total * cota.valoare, 1),
                castigPotential: parseFloat(miza) * selectedCote.reduce((total, cota) => total * cota.valoare, 1),
                status: 'ACTIVE',
                dataCreare: new Date().toISOString(),
                cote: selectedCote.map(cota => ({ id: cota.id })), // Trimite doar ID-urile cotelor
            });
            console.log('Răspuns de la server:', response.data);
            setSuccess('Bilet creat cu succes!');
            setSelectedCote([]);
            setMiza('');
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la crearea biletului:', errorMessage);
            setError(`Eroare la crearea biletului: ${errorMessage}`);
        }
    };

    return (
        <div className="create-ticket-container">
            <div className="ticket-form">
                <h2>Creare Bilet</h2>
                {error && <p className="error">{error}</p>}
                {success && <p className="success">{success}</p>}
                <form onSubmit={handleCreateTicket}>
                    <div className="form-group">
                        <label>Miză:</label>
                        <input
                            type="number"
                            value={miza}
                            onChange={(e) => setMiza(e.target.value)}
                            required
                            step="0.01"
                        />
                    </div>
                    <div className="selected-cote">
                        <h3>Cote Selectate:</h3>
                        {selectedCote.length === 0 ? (
                            <p>Nicio cotă selectată.</p>
                        ) : (
                            <ul>
                                {selectedCote.map((cota) => (
                                    <li key={cota.id}>
                                        {cota.descriere}: {cota.valoare}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                    <button type="submit">Creează Bilet</button>
                </form>
            </div>
            <div className="matches-list">
                <h2>Meciuri Disponibile</h2>
                {meciuri.map((meci) => (
                    <div key={meci.id} className="match-item">
                        <h3>{meci.echipaAcasa} vs {meci.echipaDeplasare}</h3>
                        <p>Competiție: {meci.competitie}</p>
                        <p>Data: {new Date(meci.dataMeci).toLocaleString()}</p>
                        <div className="cote">
                            <button onClick={() => handleAddCota({ id: 1, descriere: `${meci.echipaAcasa} câștigă`, valoare: 1.5 })}>
                                {meci.echipaAcasa} câștigă (1.5)
                            </button>
                            <button onClick={() => handleAddCota({ id: 2, descriere: `${meci.echipaDeplasare} câștigă`, valoare: 2.0 })}>
                                {meci.echipaDeplasare} câștigă (2.0)
                            </button>
                            <button onClick={() => handleAddCota({ id: 3, descriere: 'Egal', valoare: 3.0 })}>
                                Egal (3.0)
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CreateTicket;