import React, { useEffect, useState } from 'react';
import api from '../services/api';
import '../design/Matches.css';
import TicketSidebar from '../components/TicketSidebar';
import { useNavigate } from 'react-router-dom';
import { Cota } from '../context/BetSlipContext';
import Navbar from '../components/Navbar'; // sau '../Navbar' după caz

interface Meci {
  id: number;
  echipaAcasa: string;
  echipaDeplasare: string;
  dataMeci: string;
  competitie: string;
  rezultat?: string;
  blocat: boolean;
}

const LiveToday: React.FC = () => {
  const [meciuri, setMeciuri] = useState<Meci[]>([]);
  const [coteByMeci, setCoteByMeci] = useState<{ [key: number]: Cota[] }>({});
  const [selectedMatch, setSelectedMatch] = useState<Meci | null>(null);
  const [selectedCote, setSelectedCote] = useState<Cota[]>([]);
  const [miza, setMiza] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const navigate = useNavigate();

  const handleAddCota = (cota: Cota) => {
    if (cota.blocat) {
      setError('Această cotă este blocată și nu poate fi selectată.');
      return;
    }
    setSelectedCote(prev =>
      [...prev.filter(existing => existing.idMeci !== cota.idMeci), cota]
    );
  };

  useEffect(() => {
    const fetchMeciuri = async () => {
      try {
        const azi = new Date().toISOString().split('T')[0];
        const response = await api.get('/meciuri');
        const meciuriAzi = response.data.filter((meci: Meci) =>
          meci.dataMeci.startsWith(azi)
        );
        setMeciuri(meciuriAzi);

        const cotePromises = meciuriAzi.map(async (meci: Meci) => {
          const coteResponse = await api.get(`/cote/meci/${meci.id}`);
          return { meciId: meci.id, cote: coteResponse.data };
        });
        const coteResults = await Promise.all(cotePromises);
        const coteMap = coteResults.reduce((acc, { meciId, cote }) => {
          acc[meciId] = cote;
          return acc;
        }, {} as { [key: number]: Cota[] });
        setCoteByMeci(coteMap);
      } catch (err: any) {
        setError('Eroare la încărcarea meciurilor: ' + (err.response?.data || err.message));
      }
    };
    fetchMeciuri();
  }, []);

  return (
    <>
    <Navbar />
    <div className="matches-page">
      <h2 className="matches-title">Meciuri de azi</h2>

      <div className="matches-layout">
        <div className="matches-list">
          {meciuri.map((meci) => (
            <div
              key={meci.id}
              className={`match-card ${selectedMatch?.id === meci.id ? 'selected' : ''}`}
              onClick={() => setSelectedMatch(meci)}
            >
              <h3>{meci.echipaAcasa} vs {meci.echipaDeplasare}</h3>
              <p><em>Competiție:</em> {meci.competitie}</p>
              <p><em>Data:</em> {new Date(meci.dataMeci).toLocaleString()}</p>
            </div>
          ))}
        </div>

        {selectedMatch && (
          <div className="match-details-card">
            <h2>{selectedMatch.echipaAcasa} vs {selectedMatch.echipaDeplasare}</h2>
            <p><strong>Competiție:</strong> {selectedMatch.competitie}</p>
            <p><strong>Data:</strong> {new Date(selectedMatch.dataMeci).toLocaleString()}</p>
            <p><strong>Status:</strong> {selectedMatch.blocat ? 'Blocat' : 'Activ'}</p>

            <h3>Cote disponibile</h3>
            <div className="match-cote">
              {coteByMeci[selectedMatch.id]?.length ? (
                coteByMeci[selectedMatch.id].map((cota) => (
                  <div key={cota.id} className="cota-wrapper">
                    <span className="cota-descriere">{cota.descriere}</span>
                    <button
                      className="cota-button"
                      onClick={() => handleAddCota({ ...cota, idMeci: selectedMatch.id })}
                      disabled={cota.blocat}
                    >
                      {cota.valoare}
                    </button>
                  </div>
                ))
              ) : (
                <p>Nicio cotă disponibilă.</p>
              )}
            </div>

            <button onClick={() => setSelectedMatch(null)}>Închide detalii</button>
          </div>
        )}
      </div>

      <div>
        <TicketSidebar selectedCote={selectedCote} onClear={() => setSelectedCote([])} />
      </div>
    </div>
    </>
  );
};

export default LiveToday;
