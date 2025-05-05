import React, { useState } from 'react';
import api from '../services/api';

const AddGrupPrivat: React.FC = () => {
    const [nume, setNume] = useState('');
    const [adminId, setAdminId] = useState('');
    const [linkInvitatie, setLinkInvitatie] = useState('');
    const [dataCreare, setDataCreare] = useState(new Date().toISOString().slice(0, 19));
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/grupuri-private', {
                nume,
                admin: { id: parseInt(adminId) },
                linkInvitatie,
                dataCreare
            });
            setSuccess('Grup privat adăugat cu succes!');
            setError('');
            setNume('');
            setAdminId('');
            setLinkInvitatie('');
            setDataCreare(new Date().toISOString().slice(0, 19));
        } catch (err) {
            setError('Eroare la adăugarea grupului privat.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Grup Privat</h2>
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
                    <label>ID Admin:</label>
                    <input
                        type="number"
                        value={adminId}
                        onChange={(e) => setAdminId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Link Invitație:</label>
                    <input
                        type="text"
                        value={linkInvitatie}
                        onChange={(e) => setLinkInvitatie(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data Creare:</label>
                    <input
                        type="datetime-local"
                        value={dataCreare}
                        onChange={(e) => setDataCreare(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Adaugă Grup Privat</button>
            </form>
        </div>
    );
};

export default AddGrupPrivat;