import React, { useEffect, useState } from 'react';
import api from '../services/api';
import '../design/BileteMele.css';
import { Bilet } from '../types';
import { getToken } from '../services/authService';

const BileteMele: React.FC = () => {
  const [bilete, setBilete] = useState<Bilet[]>([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    const fetchBilete = async () => {
      if (!getToken()) {
        console.log('Niciun utilizator autentificat, sărim peste încărcarea biletelor');
        return;
      }
      try {
        const response = await api.get('/bilete/mele');
        setBilete(response.data || []);
      } catch (err: any) {
        setError('Eroare la încărcarea biletelor: ' + (err.response?.data || err.message));
      }
    };
    fetchBilete();
  }, []);

  return (
    <div className="bilete-mele-page">
      <h2 className="bilete-mele-title">Biletele Mele</h2>

      <div className="bilete-mele-layout">
        {/* Secțiunea pentru mesaje */}
        <div className="bilete-mele-messages">
          {error && <p style={{ color: 'red' }}>{error}</p>}
          {success && <p style={{ color: 'green' }}>{success}</p>}
        </div>

        {/* Lista de bilete */}
        <div className="bilete-mele-list">
          <h3>Bilete active</h3>
          {bilete.length === 0 ? (
            <p>Nu ai bilete active.</p>
          ) : (
            bilete.map((bilet) => (
              <div key={bilet.id} className="bilet-card">
                <h3>Bilet #{bilet.id}</h3>
                <p><strong>Miză:</strong> {bilet.miza} RON</p>
                <p><strong>Cota totală:</strong> {bilet.cotaTotala}</p>
                <p><strong>Câștig potențial:</strong> {bilet.castigPotential} RON</p>
                <p><strong>Status:</strong> {bilet.status}</p>
                <p><strong>Data creării:</strong> {new Date(bilet.dataCreare).toLocaleString()}</p>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default BileteMele;