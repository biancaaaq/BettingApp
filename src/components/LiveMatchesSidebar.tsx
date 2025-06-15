import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../design/LiveMatchesSidebar.css';

interface Match {
  id: number;
  echipaAcasa: string;
  echipaDeplasare: string;
  dataMeci: string;
}

const LiveMatchesSidebar: React.FC = () => {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const fetchTodayMatches = async () => {
    try {
      const azi = new Date().toISOString().split('T')[0];
      const response = await api.get('/meciuri');
      const meciuriAzi = response.data.filter((meci: Match) =>
        meci.dataMeci.startsWith(azi)
      );
      setMatches(meciuriAzi);
    } catch (err) {
      console.error("Eroare la încărcarea meciurilor de azi:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodayMatches(); // la montare
    const interval = setInterval(fetchTodayMatches, 30000); // refresh la 30 secunde
    return () => clearInterval(interval);
  }, []);

  const handleClick = () => {
    navigate('/live');
  };

  return (
    <div className="live-matches-sidebar">
      <h3>Meciuri de azi</h3>
      {loading ? (
        <p>Se încarcă...</p>
      ) : matches.length === 0 ? (
        <p>Niciun meci disponibil azi.</p>
      ) : (
        matches.slice(0, 4).map((match) => (
          <div
            key={match.id}
            className="live-match-card"
            onClick={handleClick}
            style={{ cursor: 'pointer' }}
          >
            <strong>{match.echipaAcasa} vs {match.echipaDeplasare}</strong>
          </div>
        ))
      )}
    </div>
  );
};

export default LiveMatchesSidebar;
