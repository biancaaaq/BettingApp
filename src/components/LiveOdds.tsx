import React, { useEffect, useState } from 'react';
import { getLiveOdds } from '../services/oddsService';

interface Bookmaker {
  title: string;
  markets: {
    outcomes: { name: string; price: number }[];
  }[];
}

interface Match {
  id: string;
  home_team: string;
  away_team: string;
  commence_time: string;
  bookmakers: Bookmaker[];
}

const LiveOdds: React.FC = () => {
  const [matches, setMatches] = useState<Match[]>([]);

  useEffect(() => {
    const fetchOdds = async () => {
      try {
        const data = await getLiveOdds();
        setMatches(data);
      } catch (error) {
        console.error("Eroare la preluarea cotelor live:", error);
      }
    };

    fetchOdds();
  }, []);

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Cote Live</h2>
      {matches.map((match) => {
        const now = new Date();
        const startTime = new Date(match.commence_time);
        const minutesPassed = Math.floor((now.getTime() - startTime.getTime()) / 60000);

        const isLive = minutesPassed >= 0 && minutesPassed <= 120;

        return (
          <div key={match.id} style={{ marginBottom: '2rem', padding: '10px', border: '1px solid #ccc' }}>
            <h4>{match.home_team} vs {match.away_team}</h4>
            <p><strong>Minut:</strong> {isLive ? `${minutesPassed}'` : 'Încă nu a început'}</p>
            {match.bookmakers[0]?.markets[0]?.outcomes.map((outcome, idx) => (
              <div key={idx}>
                {outcome.name}: <strong>{outcome.price}</strong>
              </div>
            ))}
          </div>
        );
      })}
    </div>
  );
};

export default LiveOdds;
