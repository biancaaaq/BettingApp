// import axios from 'axios';

// const API_KEY = 'a1eac2c37dd30094c57db6431ab6abf2'; // înlocuiește cu cheia ta reală
// const BASE_URL = 'https://v3.football.api-sports.io'; // endpoint general pt. fotbal

// export const getLiveOdds = async () => {
//   const todayISO = new Date().toISOString().split('T')[0]; // ex: "2025-06-02"

//   try {
//     const response = await axios.get(BASE_URL, {
//       params: {
//         apiKey: API_KEY,
//         regions: 'au,uk',            // regiune europeană (poți adăuga 'us,uk,eu,au' pentru mai multe)
//         markets: 'h2h',           // head-to-head (1X2)
//         dateFormat: 'iso',
//         oddsFormat: 'decimal'
//       }
//     });

//     // Filtrare locală pentru meciurile din ziua curentă
//     const filtered = response.data.filter((match: any) =>
//       match.commence_time.startsWith(todayISO)
//     );

//     return filtered;
//   } catch (error) {
//     console.error('Eroare la obținerea cotelor live:', error);
//     throw error;
//   }
// };
import axios from 'axios';

export const getLiveOddsFromBackend = async () => {
  try {
    const response = await axios.get("http://localhost:8080/meciuri/live/azi");
    return response.data;
  } catch (error) {
    console.error("Eroare la obținerea meciurilor live din backend:", error);
    throw error;
  }
};
