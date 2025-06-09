import React, { useState, useEffect } from 'react';
import '../design/TicketsSidebar.css';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import {  GrupPrivat } from '../types';
import { getToken } from '../services/authService';
import { Cota } from '../context/BetSlipContext';

interface Props {
  selectedCote: Cota[];
  onClear: () => void;
}

const TicketSidebar: React.FC<Props> = ({ selectedCote, onClear }) => {
  const [miza, setMiza] = useState('');
  const [grupId, setGrupId] = useState<number | null>(null);
  const [grupuri, setGrupuri] = useState<GrupPrivat[]>([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const fetchGrupuri = async () => {
    if (!getToken()) {
      console.log('Niciun utilizator autentificat, sărim peste încărcarea grupurilor');
      return;
    }
    try {
      const response = await api.get('/grupuri-private/mele');
      console.log('Răspuns API grupuri:', response.data); // Log pentru depanare
      const data = Array.isArray(response.data) ? response.data : (response.data?.data || []);
      setGrupuri(data.filter((g: GrupPrivat) => g.status === 'IN_ASTEPTARE' || g.status === 'ACTIV'));
    } catch (err: any) {
      const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
      setError('Eroare la încărcarea grupurilor: ' + errorMessage);
    }
  };

  useEffect(() => {
    fetchGrupuri();
  }, []);

  const handleCreateTicket = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (selectedCote.length === 0) {
        throw new Error('Selectează cel puțin o cotă.');
      }

      const mizaVal = parseFloat(miza);
      const cotaTotala = selectedCote.reduce((total, cota) => total * cota.valoare, 1);
      const castig = mizaVal * cotaTotala;

      const biletData = {
        miza: mizaVal,
        cotaTotala,
        castigPotential: castig,
        status: 'ACTIVE',
        dataCreare: new Date().toISOString(),
        cote: selectedCote.map(cota => ({ id: cota.id })),
      };

      const response = grupId
        ? await api.post(`/grupuri-private/${grupId}/bilet`, biletData)
        : await api.post('/bilete', biletData);

      console.log('Bilet creat:', response.data);
      setSuccess('Bilet creat cu succes!');
      setMiza('');
      setGrupId(null);
      onClear();
      setTimeout(() => navigate('/bilete/mele'), 2000);
      await fetchGrupuri(); // Reîncarcă grupurile după creare bilet
    } catch (err: any) {
      const msg = err.response?.data || err.message || 'Eroare necunoscută';
      setError(`Eroare: ${msg}`);
    }
  };

  const cotaTotala = selectedCote.reduce((total, cota) => total * cota.valoare, 1);
  const castig = miza ? (parseFloat(miza) * cotaTotala).toFixed(2) : '0.00';

  return (
    <div className="ticket-sidebar">
      <h3>Creare Bilet</h3>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
      <form onSubmit={handleCreateTicket}>
        <div className="selected-cote">
          {selectedCote.length === 0 ? (
            <p>Nicio cotă selectată.</p>
          ) : (
            <ul>
          {selectedCote.map((cota, index) => (
            <li key={`${cota.id}-${cota.idMeci}-${index}`}>
            {cota.descriere} ({cota.valoare})
            </li>
))}

</ul>

          )}
        </div>

        <div className="sidebar-input">
          <label htmlFor="grup">Grup (opțional):</label>
          <select
            id="grup"
            value={grupId || ''}
            onChange={(e) => setGrupId(e.target.value ? parseInt(e.target.value) : null)}
          >
            <option value="">Fără grup</option>
            {grupuri.map(grup => (
              <option key={grup.id} value={grup.id}>{grup.nume} (Miză: {grup.mizaComuna})</option>
            ))}
          </select>
        </div>

        <div className="sidebar-input">
          <label htmlFor="miza">Miză:</label>
          <input
            id="miza"
            type="number"
            value={miza}
            onChange={(e) => setMiza(e.target.value)}
            placeholder="Introdu miză"
            min="1"
            required
          />
        </div>

        <div className="sidebar-summary">
          <p>Cotă totală: <strong>{cotaTotala.toFixed(2)}</strong></p>
          <p>Câștig potențial: <strong>{castig} RON</strong></p>
        </div>

        <button type="submit" className="sidebar-button">Pariază acum</button>
      </form>
    </div>
  );
};

export default TicketSidebar;