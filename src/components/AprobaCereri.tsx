import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

interface CerereAutoexcludere {
    id: number;
    utilizator: { id: number; numeUtilizator: string };
    cerere: string;
    aprobat: boolean;
}

const AprobaCereri: React.FC = () => {
    const [cereri, setCereri] = useState<CerereAutoexcludere[]>([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCereri = async () => {
            try {
                const response = await api.get('/autoexcludere/cereri');
                setCereri(response.data);
            } catch (err: any) {
                const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
                console.error('Eroare la încărcarea cererilor:', errorMessage);
                setError(`Eroare la încărcarea cererilor: ${errorMessage}`);
            }
        };
        fetchCereri();
    }, []);

    const handleAproba = async (id: number) => {
        try {
            const response = await api.put(`/autoexcludere/aproba/${id}`);
            setSuccess(response.data);
            setCereri(cereri.filter(cerere => cerere.id !== id));
            setTimeout(() => setSuccess(''), 2000);
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la aprobarea cererii:', errorMessage);
            setError(`Eroare la aprobarea cererii: ${errorMessage}`);
        }
    };

    const handleInapoi = () => {
        navigate('/admin');
    };

    return (
        <div className="aproba-cereri-container">
            <h2>Aprobare Cereri Auto-excludere</h2>
            {error && <p className="error">{error}</p>}
            {success && <p className="success">{success}</p>}
            {cereri.length > 0 ? (
                <table className="cereri-table">
                    <thead>
                        <tr>
                            <th>Utilizator</th>
                            <th>Data Cererii</th>
                            <th>Acțiuni</th>
                        </tr>
                    </thead>
                    <tbody>
                        {cereri.map((cerere) => (
                            <tr key={cerere.id}>
                                <td>{cerere.utilizator.numeUtilizator}</td>
                                <td>{new Date(cerere.cerere).toLocaleString()}</td>
                                <td>
                                    <button onClick={() => handleAproba(cerere.id)}>Aprobă</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>Nu există cereri de auto-excludere neaprobate.</p>
            )}
            <div className="action-buttons">
                <button onClick={handleInapoi}>Înapoi</button>
            </div>
        </div>
    );
};

export default AprobaCereri;