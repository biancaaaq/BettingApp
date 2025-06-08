import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { logout } from '../services/authService';
import '../design/AdminPanel.css';

const AdminPanel: React.FC = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="admin-panel-container">
            <h2>Panou Admin</h2>
            <div className="admin-link">
                <Link to="/admin/add-utilizator">Adaugă Utilizator</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-tranzactie">Adaugă Tranzacție</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-bilet">Adaugă Bilet</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-detaliu-bilet">Adaugă Detaliu Bilet</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-grup-privat">Adaugă Grup Privat</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-rol-utilizator">Adaugă Rol Utilizator</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-promotie">Adaugă Promoție</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-cerere-autoexcludere">Adaugă Cerere Auto-excludere</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/aproba-cereri">Aprobare Cereri Auto-excludere</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/manage-users">Gestionează Utilizatori</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-cota">Adaugă Cotă</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-meci">Adaugă Meci</Link>
            </div>
            <div className="admin-link">
                <Link to="/admin/add-balanta">Adaugă Balanță</Link>
            </div>
            <button className="logout-button1" onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default AdminPanel;