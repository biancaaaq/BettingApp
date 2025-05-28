import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getRole, logout } from '../services/authService';
import '../design/Home.css';

const Home: React.FC = () => {
    const role = getRole();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="home-container">
            <h2>Bine ai venit</h2>
            <div className="admin-link">
                <Link to="/bilete/creare">Creare Bilet</Link>
            </div>
            <div className="admin-link">
                <Link to="/bilete/mele">Biletele Mele</Link>
            </div>
            <div className="admin-link">
                <Link to="/tranzactii">Depunere/Retragere Bani</Link>
            </div>
            <div className="admin-link">
                <Link to="/autoexcludere">Auto-excludere</Link>
            </div>
            {role === 'ADMIN' && (
                <div className="admin-link">
                    <Link to="/admin">Gestionează datele</Link>
                </div>
            )}
            <button className="logout-button" onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default Home;