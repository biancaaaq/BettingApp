import React, { useEffect, useState } from 'react';
import api from '../services/api';
import '../design/Promotii.css';

interface Promotie {
  id: number;
  titlu: string;
  descriere: string;
  imag?: string;
  dataStart: string;
  dataFinal: string;
}

const Promotii: React.FC = () => {
  const [promotii, setPromotii] = useState<Promotie[]>([]);

useEffect(() => {
  const fetchPromotii = async () => {
    try {
      const response = await api.get('/promotii');
      console.log('Date primite de la backend:', response.data); // ← AICI îl pui
      setPromotii(response.data);
    } catch (error) {
      console.error('Eroare la încărcarea promoțiilor:', error);
    }
  };
  fetchPromotii();
}, []);

  const getImagePath = (imag?: string): string => {
    const filename = imag?.trim() || 'basic.jpg';
    return `../promotii/${filename}`;
  };


  const formatDate = (isoDate: string) => {
    const date = new Date(isoDate);
    return date.toLocaleString('ro-RO', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="promotii-page">
      <h2 className="promotii-title">Promoții Active</h2>
      <div className="promotii-grid">
        {promotii.map(p => (
          <div className="promotie-card" key={p.id}>
            <div className="promotie-image-container">
              <img src={getImagePath(p.imag)} alt={p.titlu} />
              <div className="promotie-overlay">
                <h3>{p.titlu}</h3>
                <p>{p.descriere}</p>
                <p><strong>Începe:</strong> {formatDate(p.dataStart)}</p>
                <p><strong>Se încheie:</strong> {formatDate(p.dataFinal)}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Promotii;
