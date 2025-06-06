import axios from 'axios';

const API_KEY = 'd4469c632c778a7e32199e2b2b996df5'; // înlocuiește cu cheia ta reală
const BASE_URL = 'https://api.the-odds-api.com/v4/sports/soccer/odds'; // endpoint general pt. fotbal

export const getLiveOdds = async () => {
  const todayISO = new Date().toISOString().split('T')[0]; // ex: "2025-06-02"

  try {
    const response = await axios.get(BASE_URL, {
      params: {
        apiKey: API_KEY,
        regions: 'au,uk',            // regiune europeană (poți adăuga 'us,uk,eu,au' pentru mai multe)
        markets: 'h2h',           // head-to-head (1X2)
        dateFormat: 'iso',
        oddsFormat: 'decimal'
      }
    });

    // Filtrare locală pentru meciurile din ziua curentă
    const filtered = response.data.filter((match: any) =>
      match.commence_time.startsWith(todayISO)
    );

    return filtered;
  } catch (error) {
    console.error('Eroare la obținerea cotelor live:', error);
    throw error;
  }
};
