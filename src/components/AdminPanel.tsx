import React from 'react';
import { Link } from 'react-router-dom';
import { getRole } from '../services/authService';
import ListUtilizatori from './ListUtilizatori';

const AdminPanel: React.FC = () => {
    const role = getRole();

    return (
        <div>
            <h2>Admin Panel</h2>
            {role === 'ADMIN' && (
                <div>
                    <h3>Admin Actions</h3>
                    <ul>
                        <li><Link to="/admin/add-utilizator">Adaugă Utilizator</Link></li>
                        <li><Link to="/admin/add-tranzactie">Adaugă Tranzacție</Link></li>
                        <li><Link to="/admin/add-bilet">Adaugă Bilet</Link></li>
                        <li><Link to="/admin/add-detaliu-bilet">Adaugă Detaliu Bilet</Link></li>
                        <li><Link to="/admin/add-grup-privat">Adaugă Grup Privat</Link></li>
                        <li><Link to="/admin/add-rol-utilizator">Adaugă Rol Utilizator</Link></li>
                        <li><Link to="/admin/add-promotie">Adaugă Promoție</Link></li>
                        <li><Link to="/admin/add-cerere-autoexcludere">Adaugă Cerere Autoexcludere</Link></li>
                        <li><Link to="/admin/add-cota">Adaugă Cotă</Link></li>
                        <li><Link to="/admin/add-meci">Adaugă Meci</Link></li>
                        <li><Link to="/admin/add-balanta">Adaugă Balanță</Link></li>
                    </ul>
                </div>
            )}
            <ListUtilizatori />
        </div>
    );
};

export default AdminPanel;