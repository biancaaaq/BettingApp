import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../design/Autoexcludere.css';

const Autoexcludere: React.FC = () => {
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleAutoexcludere = async () => {
    try {
      const response = await api.post('/autoexcludere');
      setSuccess(response.data);
      setTimeout(() => navigate('/home'), 2000);
    } catch (err: any) {
      const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
      console.error('Eroare la auto-excludere:', errorMessage);
      setError(`Eroare la auto-excludere: ${errorMessage}`);
    }
  };

  const handleInapoi = () => {
    navigate('/home');
  };

  return (
    <div className="autoexcludere-page">
      <div className="autoexcludere-container">
        <h2>Auto-excludere</h2>
        {error && <p className="error">{error}</p>}
        {success && <p className="success">{success}</p>}
        <p>
          Ești sigur că vrei să te auto-excluzi? Această acțiune va crea o cerere care trebuie aprobată de un admin.
          După aprobare, contul tău va fi dezactivat, și nu vei mai putea paria până când un admin îți reactivează contul.
        </p>
        <div className="action-buttons">
          <button onClick={handleAutoexcludere}>Confirmă Auto-excludere</button>
          <button onClick={handleInapoi}>Înapoi</button>
        </div>
      </div>
    </div>
  );
};

export default Autoexcludere;
