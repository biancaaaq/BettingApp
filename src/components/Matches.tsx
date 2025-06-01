
import React, { useEffect, useState } from 'react';
import api from '../services/api';
import '../design/Matches.css';
import TicketSidebar from '../components/TicketSidebar';
import AddCota from './AddCota';
import { useNavigate } from 'react-router-dom';
import { getToken } from '../services/authService';

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
  blocat: boolean;
}


const Matches: React.FC = () => {
  const [meciuri, setMeciuri] = useState<Meci[]>([]);
  const [coteByMeci, setCoteByMeci] = useState<{ [key: number]: Cota[] }>({});
  const [selectedDate, setSelectedDate] = useState<string>('all');
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
        setSelectedCote([...selectedCote, cota]);
    };
    const handleCreateTicket = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            console.log('Token trimis:', getToken());
            if (selectedCote.length === 0) {
                throw new Error('Selectează cel puțin o cotă pentru a crea biletul.');
            }
            const response = await api.post('/bilete', {
                miza: parseFloat(miza),
                cotaTotala: selectedCote.reduce((total, cota) => total * cota.valoare, 1),
                castigPotential: parseFloat(miza) * selectedCote.reduce((total, cota) => total * cota.valoare, 1),
                status: 'ACTIVE',
                dataCreare: new Date().toISOString(),
                cote: selectedCote.map(cota => ({ id: cota.id })),
            });
            console.log('Răspuns de la server:', response.data);
            setSuccess('Bilet creat cu succes!');
            setSelectedCote([]);
            setMiza('');
            setTimeout(() => navigate('/home'), 2000);
        } catch (err: any) {
            const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
            console.error('Eroare la crearea biletului:', errorMessage);
            setError(`Eroare la crearea biletului: ${errorMessage}`);
        }
    };


  const handleCreate = (miza: number) => {
        // aici trimiți biletul la backend, dacă vrei
        console.log("Trimit bilet:", { miza, selectedCote });
  }
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

  const getNextDays = (count: number) => {
    const today = new Date();
    const days = [];
    for (let i = 0; i < count; i++) {
      const d = new Date(today);
      d.setDate(today.getDate() + i);
      const label = i === 0 ? 'Azi' : i === 1 ? 'Mâine' : d.toLocaleDateString('ro-RO', { weekday: 'short', day: 'numeric', month: 'short' });
      const iso = d.toISOString().split('T')[0];
      days.push({ label, date: iso });
    }
    return [{ label: 'Toate', date: 'all' }, ...days];
  };

  const zile = getNextDays(7);

  const meciuriFiltrate = selectedDate === 'all'
    ? meciuri
    : meciuri.filter(meci => meci.dataMeci.startsWith(selectedDate));

  return (
    <div className="matches-page">
      <h2 className="matches-title">Meciuri disponibile</h2>

      <div className="calendar-menu">
        {zile.map((zi) => (
          <button
            key={zi.date}
            className={`calendar-button ${selectedDate === zi.date ? 'active' : ''}`}
            onClick={() => {
              setSelectedDate(zi.date);
              setSelectedMatch(null); // resetăm detaliile
            }}
          >
            {zi.label}
          </button>
        ))}
      </div>

      <div className="matches-layout">
        {/* Coloana stângă */}
        <div className="matches-list">
          {meciuriFiltrate.map((meci) => (
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

        {/* Coloana dreaptă */}
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
                 onClick={() => handleAddCota(cota)}
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



{/* 

  return (
  <div className="matches-page">
    <div className="matches-grid">
      {/* Coloana 1 – Lista de meciuri */}
      {/* <div className="column column-meciuri">
        <h2>Meciuri Disponibile</h2>
        {meciuri.map((meci) => (
          <div key={meci.id} className="match-card" onClick={() => setSelectedMatch(meci)}>
            <h3>{meci.echipaAcasa} vs {meci.echipaDeplasare}</h3>
            <p><strong>Competiție:</strong> {meci.competitie}</p>
            <p><strong>Data:</strong> {new Date(meci.dataMeci).toLocaleString()}</p>
          </div>
        ))}
      </div> */}

      {/* Coloana 2 – Detalii meci selectat */}
      {/* <div className="column column-detalii">
        {selectedMatch ? (
          <>
            <h2>Detalii Meci</h2>
            <h3>{selectedMatch.echipaAcasa} vs {selectedMatch.echipaDeplasare}</h3>
            <p><strong>Competiție:</strong> {selectedMatch.competitie}</p>
            <p><strong>Data:</strong> {new Date(selectedMatch.dataMeci).toLocaleString()}</p>
            <div className="cote">
              {coteByMeci[selectedMatch.id]?.map((cota) => (
                <button
                  key={cota.id}
                  onClick={() => AddCota(cota)}
                  disabled={cota.blocat}
                >
                  {cota.descriere} ({cota.valoare})
                </button>
              ))}
            </div>
          </>
        ) : (
          <p>Selectează un meci pentru detalii</p>
        )}
      </div>

          <div>
        <TicketSidebar selectedCote={selectedCote} onCreate={handleCreate}/>
      
    </div>
    </div>
  </div> */}


      <div>
         <TicketSidebar selectedCote={selectedCote} onCreate={handleCreate}/>
       </div>
     </div>
   );
};

export default Matches;
function setError(arg0: string) {
  throw new Error('Function not implemented.');
}

