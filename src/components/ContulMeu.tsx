import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';


interface Tranzactie {
  id: number;
  tip: string;
  valoare: number;
  dataTranzactie?: string;
}

interface ContDate {
  nume: string;
  email: string;
  balanta: number;
  tranzactii: Tranzactie[];
  autoexcludere?: any;
}

const ContulMeu: React.FC = () => {
  const [cont, setCont] = useState<ContDate | null>(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCont = async () => {
      try {
        const response = await api.get('/contul-meu');
        setCont(response.data);
      } catch (err: any) {
        setError('A apărut o eroare la încărcarea datelor: ' + (err.response?.data || err.message));
      }
    };

    fetchCont();
  }, []);

  const handleInapoi = () => {
    navigate('/home');
  };

  if (error) {
    return <div className="error" style={{ color: 'red' }}>{error}</div>;
  }

  if (!cont) {
    return <div>Se încarcă datele contului...</div>;
  }

  return (
    <div className="contul-meu-container">
      <div className="cont-info">
        <h2>Contul Meu</h2>
        <p><strong>Nume:</strong> {cont.nume}</p>
        <p><strong>Email:</strong> {cont.email}</p>
        <p><strong>Balanță:</strong> {cont.balanta.toFixed(2)} RON</p>
        {cont.autoexcludere && (
        <p style={{ color: 'red' }}>
            ⚠ Ai o cerere de autoexcludere aprobată.
         </p>
        )}

          <button onClick={() => navigate('/autoexcludere')}>
            Trimite cerere de auto-excludere
            </button>
      </div>

      <div className="tranzactii-list">
        <h3>Istoric Tranzacții</h3>
        <button onClick={() => navigate('/tranzactii')}>
          Depuneri-Retrageri
       </button>

        {cont.tranzactii.length === 0 ? (
          <p>Nu ai tranzacții înregistrate.</p>
        ) : (
          <table className="tranzactii-table">
            <thead>
              <tr>
                <th>Tip</th>
                <th>Valoare (RON)</th>
                <th>Data</th>
              </tr>
            </thead>
            <tbody>
              {cont.tranzactii.map((tranzactie) => (
                <tr key={tranzactie.id}>
                  <td>{tranzactie.tip === 'DEPOSIT' ? 'Depunere' : 'Retragere'}</td>
                  <td>{tranzactie.valoare.toFixed(2)}</td>
                  <td>{tranzactie.dataTranzactie ? new Date(tranzactie.dataTranzactie).toLocaleString() : '-'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default ContulMeu;