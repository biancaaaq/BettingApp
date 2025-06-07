import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/api';
import { GrupPrivat, GrupMember } from '../types';
import '../design/GrupDetails.css';

const GrupDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [grup, setGrup] = useState<GrupPrivat | null>(null);
  const [usernameInvitat, setUsernameInvitat] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [isAdmin, setIsAdmin] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchGrup = async () => {
      try {
        const response = await api.get(`/grupuri-private/${id}`);
        console.log('Răspuns API grup:', response.data);
        setGrup(response.data);
        const username = sessionStorage.getItem('username');
        setIsAdmin(username === response.data.admin?.numeUtilizator);
      } catch (err: any) {
        setError('Eroare la încărcarea grupului: ' + (err.response?.data || err.message));
      }
    };
    fetchGrup();
  }, [id]);

  const handleInvite = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post(`/grupuri-private/${id}/invite`, null, { params: { username: usernameInvitat } });
      setSuccess(`Utilizatorul ${usernameInvitat} a fost invitat cu succes!`);
      setError('');
      setUsernameInvitat('');
      // Reîncarcă grupul după invitare
      const response = await api.get(`/grupuri-private/${id}`);
      setGrup(response.data);
    } catch (err: any) {
      const errorMessage = err.response?.data || err.message || 'Eroare necunoscută';
      setError('Eroare la invitarea utilizatorului: ' + errorMessage);
      setSuccess('');
      console.error('Eroare detaliată:', errorMessage);
    }
  };

  if (!grup) {
    return <div>Se încarcă...</div>;
  }

  return (
    <div className="grup-details-page">
      <h2 className="grup-details-title">{grup.nume}</h2>

      <div className="grup-details-layout">
        {/* Secțiunea pentru informații generale */}
        <div className="grup-info">
          {error && <p style={{ color: 'red' }}>{error}</p>}
          {success && <p style={{ color: 'green' }}>{success}</p>}
          <p><strong>Admin:</strong> {grup.admin?.numeUtilizator}</p>
          <p><strong>Miză comună:</strong> {grup.mizaComuna} RON</p>
          <p><strong>Status:</strong> {grup.status}</p>
        </div>

        {/* Lista de membri */}
        <div className="grup-members">
          <h3>Membri</h3>
          {grup.membri && grup.membri.length > 0 ? (
            <ul>
              {grup.membri.map((membru: GrupMember) => (
                <li key={membru.id}>{membru.utilizator.numeUtilizator}</li>
              ))}
            </ul>
          ) : (
            <p>Niciun membru în grup.</p>
          )}
        </div>

        {/* Lista de bilete */}
        <div className="grup-bilete">
          <h3>Pariuri/Bilete</h3>
          {grup.bilete && grup.bilete.length > 0 ? (
            <ul>
              {grup.bilete.map((bilet: any) => (
                <li key={bilet.id}>
                  {bilet.utilizator.numeUtilizator}: Miză {bilet.miza} RON, Câștig potențial {bilet.castigPotential} RON, Status: {bilet.status}
                </li>
              ))}
            </ul>
          ) : (
            <p>Niciun pariu/bilet în grup.</p>
          )}
        </div>

        {/* Formular de invitare (doar pentru admin) */}
        {isAdmin && (
          <div className="invite-form">
            <h3>Invită un utilizator</h3>
            <form onSubmit={handleInvite}>
              <div>
                <label htmlFor="username">Nume utilizator:</label>
                <input
                  id="username"
                  type="text"
                  value={usernameInvitat}
                  onChange={(e) => setUsernameInvitat(e.target.value)}
                  placeholder="Introdu nume utilizator"
                  required
                />
              </div>
              <button type="submit">Trimite invitație</button>
            </form>
          </div>
        )}

        <button onClick={() => navigate('/grupuri')}>Înapoi la grupuri</button>
      </div>
    </div>
  );
};

export default GrupDetails;