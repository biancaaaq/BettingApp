import React, { useState } from 'react';
import '../design/TicketsSidebar.css';
import { useNavigate } from 'react-router-dom';
import { getToken } from '../services/authService';
import api from '../services/api';

interface Cota {
  id: number;
  descriere: string;
  valoare: number;
  blocat: boolean;
}

interface Props {
  selectedCote: Cota[];
  onClear: () => void; // ca să golești după pariere
}

const TicketSidebar: React.FC<Props> = ({ selectedCote, onClear }) => {
  const [miza, setMiza] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleCreateTicket = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      if (selectedCote.length === 0) {
        throw new Error('Selectează cel puțin o cotă.');
      }

      const mizaVal = parseFloat(miza);
      const cotaTotala = selectedCote.reduce((total, cota) => total * cota.valoare, 1);
      const castig = mizaVal * cotaTotala;

      const response = await api.post('/bilete', {
        miza: mizaVal,
        cotaTotala,
        castigPotential: castig,
        status: 'ACTIVE',
        dataCreare: new Date().toISOString(),
        cote: selectedCote.map(cota => ({ id: cota.id })),
      });

      console.log('Bilet creat:', response.data);
      setSuccess('Bilet creat cu succes!');
      setMiza('');
      onClear(); // golește cotele selectate
      setTimeout(() => navigate('/bilete/mele'), 2000);
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
              {selectedCote.map((cota) => (
                <li key={cota.id}>{cota.descriere} ({cota.valoare})</li>
              ))}
            </ul>
          )}
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
