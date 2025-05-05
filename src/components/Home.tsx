import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getRole, logout } from '../services/authService';
import '../design/Home.css';
//<div className="admin-link">
//<Link to="/create-ticket">Creare Bilet</Link>
//</div>
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