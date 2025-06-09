import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { getToken } from '../services/authService';
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
    idMeci: number; // ✅ nou
    descriere: string;
    valoare: number;
    blocat: boolean;
}

const CreateTicket: React.FC = () => {
    const [meciuri, setMeciuri] = useState<Meci[]>([]);
    const [coteByMeci, setCoteByMeci] = useState<{ [key: number]: Cota[] }>({});
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

                const cotePromises = response.data.map(async (meci: Meci) => {
                    const coteResponse = await api.get(`/cote/meci/${meci.id}`);
                    return { meciId: meci.id, cote: coteResponse.data };
                });
                const coteResults = await Promise.all(cotePromises);
                const coteMap = coteResults.reduce((acc, { meciId, cote }) => {
                    acc[meciId] = cote.map((c: any) => ({ ...c, idMeci: meciId })); // ✅ aici adaugi idMeci în fiecare cotă
                    return acc;
                }, {} as { [key: number]: Cota[] });

                setCoteByMeci(coteMap);
            } catch (err: any) {
                setError('Eroare la încărcarea meciurilor: ' + (err.response?.data || err.message));
            }
        };
        fetchMeciuri();
    }, []);

    const handleAddCota = (cota: Cota) => {
    if (cota.blocat) {
        setError('Această cotă este blocată.');
        return;
    }

    const nou = [
        ...selectedCote.filter(existing => existing.idMeci !== cota.idMeci),
        cota
    ];

    console.log("🟡 Cota selectată:", cota);
    console.log("🟡 Lista nouă selectedCote:", nou);

    setSelectedCote(nou);
};



    const handleCreateTicket = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            if (selectedCote.length === 0) {
                throw new Error('Selectează cel puțin o cotă pentru a crea biletul.');
            }
            const total = selectedCote.reduce((t, c) => t * c.valoare, 1);
            const mizNumeric = parseFloat(miza);
            console.log("La submit, selectedCote:", selectedCote);
            const response = await api.post('/bilete', {
                miza: mizNumeric,
                cotaTotala: total,
                castigPotential: mizNumeric * total,
                status: 'ACTIVE',
                dataCreare: new Date().toISOString(),
                cote: selectedCote.map(cota => ({ id: cota.id })),
            });
            setSuccess('Bilet creat cu succes!');
            setSelectedCote([]);
            setMiza('');
            setTimeout(() => navigate('/home'), 2000);
        } catch (err: any) {
            const msg = err.response?.data || err.message || 'Eroare necunoscută';
            setError(`Eroare la crearea biletului: ${msg}`);
        }
    };

    const handleInapoi = () => {
        navigate('/home');
    };

    // return (
    //     <div className="create-ticket-container">
    //         <div className="ticket-form">
    //             <h2>Creare Bilet</h2>
    //             {error && <p className="error">{error}</p>}
    //             {success && <p className="success">{success}</p>}
    //             <form onSubmit={handleCreateTicket}>
    //                 <div className="form-group">
    //                     <label>Miză:</label>
    //                     <input
    //                         type="number"
    //                         value={miza}
    //                         onChange={(e) => setMiza(e.target.value)}
    //                         required
    //                         step="0.01"
    //                     />
    //                 </div>
    //                 <div className="selected-cote">
    //                     <h3>Cote Selectate:</h3>
    //                     {selectedCote.length === 0 ? (
    //                         <p>Nicio cotă selectată.</p>
    //                     ) : (
    //                         <ul>
    //                             {selectedCote.map((cota) => (
    //                                 <li key={cota.id}>
    //                                     {cota.descriere}: {cota.valoare}
    //                                 </li>
    //                             ))}
    //                         </ul>
    //                     )}
    //                 </div>
    //                 <div className="action-buttons">
    //                     <button type="submit">Creează Bilet</button>
    //                     <button type="button" onClick={handleInapoi}>Înapoi</button>
    //                 </div>
    //             </form>
    //         </div>
    //         <div className="matches-list">
    //             <h2>Meciuri Disponibile</h2>
    //             {meciuri.map((meci) => (
    //                 <div key={meci.id} className="match-item">
    //                     <h3>{meci.echipaAcasa} vs {meci.echipaDeplasare}</h3>
    //                     <p>Competiție: {meci.competitie}</p>
    //                     <p>Data: {new Date(meci.dataMeci).toLocaleString()}</p>
    //                     <div className="cote">
    //                         {coteByMeci[meci.id]?.length > 0 ? (
    //                             coteByMeci[meci.id].map((cota) => (
    //                                 <button
    //                                     key={cota.id}
    //                                     onClick={() => handleAddCota({ ...cota, idMeci: meci.id })}
    //                                     disabled={cota.blocat}
    //                                     style={{ opacity: cota.blocat ? 0.5 : 1 }}
    //                                 >
    //                                     {cota.descriere} ({cota.valoare})
    //                                 </button>
    //                             ))
    //                         ) : (
    //                             <p>Nu există cote disponibile pentru acest meci.</p>
    //                         )}
    //                     </div>
    //                 </div>
    //             ))}
    //         </div>
    //     </div>
    // );

    return (
        <div className="create-ticket-container">
            <div className="create-ticket-layout">
                <div className="matches-list">
                    <h2>Meciuri Disponibile</h2>
                    {meciuri.map((meci) => (
                        <div key={meci.id} className="match-item">
                            <h3>{meci.echipaAcasa} vs {meci.echipaDeplasare}</h3>
                            <p>Competiție: {meci.competitie}</p>
                            <p>Data: {new Date(meci.dataMeci).toLocaleString()}</p>
                            <div className="cote">
                                {coteByMeci[meci.id]?.length > 0 ? (
                                    coteByMeci[meci.id].map((cota) => (
                                        <button
                                            key={cota.id}
                                            onClick={() => handleAddCota({ ...cota, idMeci: meci.id })}
                                            disabled={cota.blocat}
                                            style={{ opacity: cota.blocat ? 0.5 : 1 }}
                                        >
                                            {cota.descriere} ({cota.valoare})
                                        </button>
                                    ))
                                ) : (
                                    <p>Nu există cote disponibile pentru acest meci.</p>
                                )}
                            </div>
                        </div>
                    ))}
                </div>

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
                                            {cota.descriere} ({cota.valoare})
                                        </li>
                                    ))}
                                </ul>
                            )}
                        </div>
                        <div className="action-buttons">
                            <button type="submit">Creează Bilet</button>
                            <button type="button" onClick={handleInapoi}>Înapoi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default CreateTicket;
