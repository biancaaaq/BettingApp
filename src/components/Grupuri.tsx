import React, { useEffect, useState } from 'react';
import api from '../services/api';
import '../design/Grupuri.css';
import { useNavigate } from 'react-router-dom';
import { getToken } from '../services/authService';
import { GrupPrivat } from '../types';

interface InvitatieGrup {
  id: number;
  grup: { id: number; nume: string };
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
}

const Grupuri: React.FC = () => {
  const [grupuri, setGrupuri] = useState<GrupPrivat[]>([]);
  const [invitatii, setInvitatii] = useState<InvitatieGrup[]>([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [inviteUsername, setInviteUsername] = useState<{ [key: number]: string }>({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      if (!getToken()) {
        console.log('Niciun utilizator autentificat, sărim peste încărcarea grupurilor');
        return;
      }
      try {
        const grupuriResponse = await api.get('/grupuri-private/mele');
        const data = Array.isArray(grupuriResponse.data) ? grupuriResponse.data : [];
        setGrupuri(data);
        const invitatiiResponse = await api.get('/grupuri-private/invitatii');
        setInvitatii(invitatiiResponse.data);
      } catch (err: any) {
        setError('Eroare la încărcarea datelor: ' + (err.response?.data || err.message));
      }
    };
    fetchData();
  }, []);

  const handleCreateGrup = () => {
    navigate('/grupuri/creare');
  };

  const handleInvite = async (grupId: number) => {
    try {
      await api.post(`/grupuri-private/${grupId}/invite`, null, { params: { username: inviteUsername[grupId] } });
      setSuccess(`Utilizatorul ${inviteUsername[grupId]} a fost invitat cu succes!`);
      setError('');
      setInviteUsername(prev => ({ ...prev, [grupId]: '' }));
    } catch (err: any) {
      setError('Eroare la invitarea utilizatorului: ' + (err.response?.data || err.message));
      setSuccess('');
    }
  };

  const handleAcceptInvitatie = async (invitatieId: number) => {
    try {
      await api.put(`/grupuri-private/invitatii/${invitatieId}/accept`);
      setSuccess('Invitație acceptată cu succes!');
      setInvitatii(invitatii.filter(i => i.id !== invitatieId));
    } catch (err: any) {
      setError('Eroare la acceptarea invitației: ' + (err.response?.data || err.message));
    }
  };

  const handleRejectInvitatie = async (invitatieId: number) => {
    try {
      await api.put(`/grupuri-private/invitatii/${invitatieId}/reject`);
      setSuccess('Invitație refuzată cu succes!');
      setInvitatii(invitatii.filter(i => i.id !== invitatieId));
    } catch (err: any) {
      setError('Eroare la refuzarea invitației: ' + (err.response?.data || err.message));
    }
  };

  const isAdmin = (grup: GrupPrivat) => {
    const username = sessionStorage.getItem('username');
    return username === grup.admin?.numeUtilizator;
  };

  return (
    <div className="grupuri-page">
      <div className="grupuri-header">
  <h2 className="grupurile-mele-title">Grupurile Mele</h2>
  <button onClick={handleCreateGrup} className="create-grup-button">Creează Grup Nou</button>

  {error && <p style={{ color: 'red' }}>{error}</p>}
  {success && <p style={{ color: 'green' }}>{success}</p>}
</div>


      <div className="grupuri-columns">
        {/* Coloana 1: Grupuri */}
        <div className="grupuri-col">
          <h3>Grupuri în care sunt membru</h3>
          <div className="grupuri-list">
            {grupuri.length === 0 ? (
              <p>Nu ești membru în niciun grup.</p>
            ) : (
              grupuri.map(grup => (
                <div key={grup.id} className="grup-card">
                  <h3>{grup.nume}</h3>
                  <p><strong>Admin:</strong> {grup.admin?.numeUtilizator}</p>
                  <p><strong>Miză comună:</strong> {grup.mizaComuna} RON</p>
                  <p><strong>Status:</strong> {grup.status}</p>
                  {isAdmin(grup) && (
                    <div className="invite-form">
                      <input
                        type="text"
                        value={inviteUsername[grup.id] || ''}
                        onChange={(e) => setInviteUsername(prev => ({ ...prev, [grup.id]: e.target.value }))}
                        placeholder="Nume utilizator"
                      />
                      <button onClick={() => handleInvite(grup.id)}>Invită</button>
                    </div>
                  )}
                  <button onClick={() => navigate(`/grupuri/${grup.id}`)}>Vezi detalii</button>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Coloana 2: Invitații */}
        <div className="grupuri-col">
          <h3>Invitații primite</h3>
          <div className="invitatii-list">
            {invitatii.length === 0 ? (
              <p>Nu ai invitații în așteptare.</p>
            ) : (
              invitatii.map(invitatie => (
                <div key={invitatie.id} className="invitatie-card">
                  <p><strong>Grup:</strong> {invitatie.grup.nume}</p>
                  <button onClick={() => handleAcceptInvitatie(invitatie.id)}>Acceptă</button>
                  <button onClick={() => handleRejectInvitatie(invitatie.id)}>Refuză</button>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Grupuri;
