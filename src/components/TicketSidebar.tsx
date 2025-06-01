import React, { useState } from 'react';
import '../design/TicketsSidebar.css';

interface Cota {
    id: number;
    descriere: string;
    valoare: number;
}

interface Props {
    selectedCote: Cota[];
    onCreate: (miza: number) => void;
}

const TicketSidebar: React.FC<Props> = ({ selectedCote, onCreate }) => {
    const [miza, setMiza] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const mizaNum = parseFloat(miza);
        if (!isNaN(mizaNum)) {
            onCreate(mizaNum);
            setMiza('');
        }
    };

    const cotaTotala = selectedCote.reduce((total, cota) => total * cota.valoare, 1);
    const castig = miza ? (parseFloat(miza) * cotaTotala).toFixed(2) : '0.00';

    return (
        <div className="ticket-sidebar">
            <h3>Creare Bilet</h3>
            <form onSubmit={handleSubmit}>
                <div className="selected-cote">
                    {selectedCote.length === 0 ? (
                        <p>Nicio cotă selectată.</p>
                    ) : (
                        <ul>
                            {selectedCote.map((cota) => (
                                <li key={cota.id}>{cota.descriere} ({cota.valoare})</li>
                            ))}
                        </ul>
                    )}
                </div>
                <div className="sidebar-input">
                    <label htmlFor="miza">Miză:</label>
                    <input
                        id="miza"
                        type="number"
                        value={miza}
                        onChange={(e) => setMiza(e.target.value)}
                        placeholder="Introdu miză"
                        min="1"
                        required
                    />
                </div>
                <div className="sidebar-summary">
                    <p>Cotă totală: <strong>{cotaTotala.toFixed(2)}</strong></p>
                    <p>Câștig potențial: <strong>{castig} RON</strong></p>
                </div>
                <button type="submit" className="sidebar-button">Pariază acum</button>
            </form>
        </div>
    );
};

export default TicketSidebar;
