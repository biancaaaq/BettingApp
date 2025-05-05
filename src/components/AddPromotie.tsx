import React, { useState } from 'react';
import api from '../services/api';

const AddPromotie: React.FC = () => {
    const [titlu, setTitlu] = useState('');
    const [descriere, setDescriere] = useState('');
    const [dataStart, setDataStart] = useState(new Date().toISOString().slice(0, 16));
    const [dataFinal, setDataFinal] = useState(new Date().toISOString().slice(0, 16));
    const [imag, setImag] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await api.post('/promotii', {
                titlu,
                descriere,
                dataStart,
                dataFinal,
                imag
            });
            setSuccess('Promoție adăugată cu succes!');
            setError('');
            setTitlu('');
            setDescriere('');
            setDataStart(new Date().toISOString().slice(0, 16));
            setDataFinal(new Date().toISOString().slice(0, 16));
            setImag('');
        } catch (err) {
            setError('Eroare la adăugarea promoției.');
            setSuccess('');
        }
    };

    return (
        <div>
            <h2>Adaugă Promoție</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Titlu:</label>
                    <input
                        type="text"
                        value={titlu}
                        onChange={(e) => setTitlu(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Descriere:</label>
                    <textarea
                        value={descriere}
                        onChange={(e) => setDescriere(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data Start:</label>
                    <input
                        type="datetime-local"
                        value={dataStart}
                        onChange={(e) => setDataStart(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data Final:</label>
                    <input
                        type="datetime-local"
                        value={dataFinal}
                        onChange={(e) => setDataFinal(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Imagine (URL):</label>
                    <input
                        type="text"
                        value={imag}
                        onChange={(e) => setImag(e.target.value)}
                    />
                </div>
                <button type="submit">Adaugă Promoție</button>
            </form>
        </div>
    );
};

export default AddPromotie;