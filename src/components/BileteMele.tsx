import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { getToken } from '../services/authService';
import '../design/BileteMele.css';

interface Cota {
    id: number;
    descriere: string;
    valoare: number;
}

interface Bilet {
    id: number;
    miza: number;
    cotaTotala: number;
    castigPotential: number;
    status: string;
    dataCreare: string;
    cote: Cota[];
}

const BileteMele: React.FC = () => {
    const [bilete, setBilete] = useState<Bilet[]>([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBilete = async () => {
            try {
                console.log('Token trimis:', getToken());
                const response = await api.get('/bilete/mele');
                setBilete(response.data);
            } catch (err: any) {
                const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
                console.error('Eroare la încărcarea biletelor:', errorMessage);
                setError(`Eroare la încărcarea biletelor: ${errorMessage}`);
            }
        };
        fetchBilete();
    }, []);

    const handleInapoi = () => {
        navigate('/home');
    };

    return (
        <div className="bilete-mele-container">
            <h2>Biletele Mele</h2>
            {error && <p className="error">{error}</p>}
            {bilete.length > 0 ? (
                <table className="bilete-table">
                    <thead>
                        <tr>
                            <th>Miză (RON)</th>
                            <th>Cotă Totală</th>
                            <th>Câștig Potențial (RON)</th>
                            <th>Status</th>
                            <th>Data Creare</th>
                            <th>Cote</th>
                        </tr>
                    </thead>
                    <tbody>
                        {bilete.map((bilet) => (
                            <tr key={bilet.id}>
                                <td>{bilet.miza.toFixed(2)}</td>
                                <td>{bilet.cotaTotala.toFixed(2)}</td>
                                <td>{bilet.castigPotential.toFixed(2)}</td>
                                <td>{bilet.status}</td>
                                <td>{new Date(bilet.dataCreare).toLocaleString()}</td>
                                <td>
                                    <ul>
                                        {bilet.cote.map((cota) => (
                                            <li key={cota.id}>
                                                {cota.descriere}: {cota.valoare.toFixed(2)}
                                            </li>
                                        ))}
                                    </ul>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>Nu ai plasat încă niciun bilet.</p>
            )}
            <div className="action-buttons">
                <button onClick={handleInapoi}>Înapoi</button>
            </div>
        </div>
    );
};

export default BileteMele;