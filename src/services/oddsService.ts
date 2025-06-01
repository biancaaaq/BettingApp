import axios from 'axios';

const API_KEY = 'd4469c632c778a7e32199e2b2b996df5'; // înlocuiește cu cheia ta reală

const BASE_URL = 'https://api.the-odds-api.com/v4/sports/soccer_uefa_champs_league/odds';

export const getLiveOdds = async () => {
  const response = await axios.get(BASE_URL, {
    params: {
    apiKey: API_KEY,
    regions: 'eu',
    markets: 'h2h',
    dateFormat: 'iso',
    oddsFormat: 'decimal'
    }
  });

  return response.data;
};
