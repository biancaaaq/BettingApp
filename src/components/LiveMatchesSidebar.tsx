import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getLiveOdds } from '../services/oddsService';
import '../design/LiveMatchesSidebar.css';

interface Match {
  id: string;
  home_team: string;
  away_team: string;
}

const LiveMatchesSidebar: React.FC = () => {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchLive = async () => {
      try {
        const data = await getLiveOdds();
        setMatches(data);
      } catch (err) {
        console.error("Eroare la încărcarea cotelor live:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchLive();
  }, []);

  const handleClick = () => {
    navigate('/live');
  };

  return (
    <div className="live-matches-sidebar">
      <h3>Meciuri Live</h3>
      {loading ? (
        <p>Se încarcă...</p>
      ) : matches.length === 0 ? (
        <p>Niciun meci live acum.</p>
      ) : (
        matches.slice(0, 4).map((match) => (
          <div
            key={match.id}
            className="live-match-card"
            onClick={handleClick}
            style={{ cursor: 'pointer' }}
          >
            <strong>{match.home_team} vs {match.away_team}</strong>
          </div>
        ))
      )}
    </div>
  );
};

export default LiveMatchesSidebar;