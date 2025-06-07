import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../design/CreateGrup.css';

const CreateGrup: React.FC = () => {
  const [nume, setNume] = useState('');
  const [mizaComuna, setMizaComuna] = useState('');
  const [linkInvitatie, setLinkInvitatie] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/grupuri-private', { nume, linkInvitatie }, { params: { mizaComuna: parseFloat(mizaComuna) } });
      setSuccess('Grup creat cu succes!');
      setError('');
      setNume('');
      setMizaComuna('');
      setLinkInvitatie('');
      setTimeout(() => navigate('/grupuri'), 2000);
    } catch (err: any) {
      setError('Eroare la crearea grupului: ' + (err.response?.data || err.message));
      setSuccess('');
    }
  };

  return (
    <div className="create-grup-page">
      <h2>Creează Grup Privat</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nume:</label>
          <input
            type="text"
            value={nume}
            onChange={(e) => setNume(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Miză comună:</label>
          <input
            type="number"
            step="0.01"
            value={mizaComuna}
            onChange={(e) => setMizaComuna(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Link invitație:</label>
          <input
            type="text"
            value={linkInvitatie}
            onChange={(e) => setLinkInvitatie(e.target.value)}
            required
          />
        </div>
        <button type="submit">Creează Grup</button>
      </form>
    </div>
  );
};

export default CreateGrup;