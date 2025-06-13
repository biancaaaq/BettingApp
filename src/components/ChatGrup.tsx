import React, { useEffect, useState, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import api from '../services/api';
import { getToken, getUsername, getUsernameFromToken } from '../services/authService';
import '../design/GrupDetails.css';

interface Mesaj {
  id: number;
  grup: { id: number; nume: string };
  utilizator: { id: number; numeUtilizator: string };
  continut: string;
  timestamp: string;
}

interface ChatGrupProps {
  grupId: number;
}

const ChatGrup: React.FC<ChatGrupProps> = ({ grupId }) => {
  const [mesaje, setMesaje] = useState<Mesaj[]>([]);
  const [mesajNou, setMesajNou] = useState('');
  const [error, setError] = useState('');
  const [isConnected, setIsConnected] = useState(false);
  const clientRef = useRef<Client | null>(null);

  useEffect(() => {
    console.log('Token:', getToken());
    const fetchMesaje = async () => {
      try {
        const response = await api.get(`/grupuri-private/${grupId}/mesaje`);
        console.log('Mesaje încărcate de la backend:', response.data);
        const updatedMesaje = response.data.map((m: Mesaj) => ({
          ...m,
          utilizator: { ...m.utilizator, numeUtilizator: m.utilizator.numeUtilizator || getUsernameFromToken(getToken()) || 'Anonim' }
        }));
        setMesaje(updatedMesaje);
      } catch (e: any) {
        setError('Eroare la încărcarea mesajelor: ' + (e.response?.data || e.message));
      }
    };
    fetchMesaje();

    if (!clientRef.current) {
      console.log('Creare client STOMP');
      clientRef.current = new Client({
        webSocketFactory: () => {
          console.log('Inițializare SockJS');
          return new SockJS('http://localhost:8081/ws');
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        debug: (str) => {
          console.log('STOMP Debug:', str);
        },
      });

      clientRef.current.onConnect = (frame) => {
        console.log('WebSocket conectat cu succes:', frame);
        setIsConnected(true);
        clientRef.current!.subscribe(`/topic/grup/${grupId}`, (message) => {
          console.log('Mesaj brut primit:', message.body);
          try {
            const mesaj = JSON.parse(message.body) as Mesaj;
            console.log('Mesaj parsat:', mesaj);
            setMesaje((prev) => [...prev, mesaj]);
          } catch (e) {
            console.error('Eroare la parsarea mesajului:', e);
            setError('Eroare la procesarea mesajului primit');
          }
        });
      };

      clientRef.current.onStompError = (frame) => {
        console.error('Eroare STOMP:', frame);
        setError('Eroare WebSocket: ' + (frame.headers['message'] || 'Eroare necunoscută'));
        setIsConnected(false);
      };

      clientRef.current.onWebSocketError = (error) => {
        console.error('Eroare WebSocket:', error);
        setError('Eroare conexiune WebSocket: ' + error.message);
        setIsConnected(false);
      };

      clientRef.current.onWebSocketClose = (event) => {
        console.log('WebSocket închis:', event);
        setIsConnected(false);
        setError('Conexiune WebSocket închisă. Reîncercăm...');
      };

      console.log('Activare client WebSocket');
      clientRef.current.activate();
    }

    return () => {
      if (clientRef.current) {
        console.log('Dezactivare client WebSocket');
        clientRef.current.deactivate();
        clientRef.current = null;
      }
    };
  }, [grupId]);

  const trimiteMesaj = () => {
    console.log('Încercare trimitere mesaj:', mesajNou, 'Conectat:', isConnected);
    if (mesajNou.trim() && isConnected && clientRef.current) {
      try {
        const token = getToken();
        const utilizatorId = 1; // Înlocuiește cu logica reală dacă e disponibilă
        const username = getUsername() || getUsernameFromToken(token) || 'Anonim';
        const mesajLocal: Mesaj = {
          id: Date.now(), // ID temporar
          grup: { id: grupId, nume: 'Grup Temp' },
          utilizator: { id: utilizatorId, numeUtilizator: username },
          continut: mesajNou,
          timestamp: new Date().toISOString(),
        };
        setMesaje((prev) => [...prev, mesajLocal]); // Afișare instantanee locală
        clientRef.current.publish({
          destination: `/app/grup/${grupId}/mesaj`,
          body: JSON.stringify({
            continut: mesajNou,
            grupId: grupId,
            utilizatorId: utilizatorId,
          }),
        });
        setMesajNou('');
      } catch (e: any) {
        console.error('Eroare la publicare:', e);
        setError('Eroare la trimiterea mesajului: ' + e.message);
      }
    } else if (!isConnected) {
      setError('Nu ești conectat la chat. Te rugăm să reîncerci.');
    }
  };

  return (
    <div className="chat-container">
      <h3>Chat Grup</h3>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div className="mesaje">
        {mesaje.length === 0 ? (
          <p>Nu există mesaje în acest grup.</p>
        ) : (
          mesaje.map((m) => (
            <div key={m.id} className="mesaj">
              <strong>{m.utilizator.numeUtilizator || 'Anonim'}</strong>: {m.continut || 'Fără conținut'}{' '}
              <small>{m.timestamp ? new Date(m.timestamp).toLocaleString() : 'Fără dată'}</small>
            </div>
          ))
        )}
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={mesajNou}
          onChange={(e) => setMesajNou(e.target.value)}
          placeholder="Scrie un mesaj..."
          disabled={!isConnected}
        />
        <button onClick={trimiteMesaj} disabled={!isConnected}>Trimite</button>
      </div>
    </div>
  );
};

export default ChatGrup;