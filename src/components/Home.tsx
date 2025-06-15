import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getRole, logout } from '../services/authService';
import TicketSidebar from '../components/TicketSidebar';
import LiveMatchesSidebar from './LiveMatchesSidebar';
import '../design/Home.css';

const Home: React.FC = () => {
  const token = localStorage.getItem('token');
  const role = getRole();
  const navigate = useNavigate();

  const username = sessionStorage.getItem('username') || 'Utilizator';
  const [selectedCote, setSelectedCote] = useState([]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="home-page">
      <nav className="navbar">
        <Link to="/home" className="logo">SportsBetting</Link>
        <div className="nav-links">
          <Link to="/meciuri">Meciuri</Link>
          <Link to="/live">Cote Live</Link>
          <Link to="/bilete/mele">Biletele Mele</Link>
          <Link to="/grupuri">Grupuri</Link>
          <Link to="/contul-meu">Contul-Meu</Link>
          <Link to="/promotii">Promoții</Link>
          {role === 'ADMIN' && <Link to="/admin">Admin Panel</Link>}
        </div>
        <div className="profile-info">
          {token ? (
            <>
              <div className="user-details">
                <span className="username">{username}</span>
              </div>
              <button onClick={handleLogout} className="logout-button">Logout</button>
            </>
          ) : (
            <Link to="/login" className="logout-button">Login</Link>
          )}
        </div>
      </nav>

      <div className="page-layout">
        <div className="sidebar-form">
          <LiveMatchesSidebar />
        </div>

        <div className="page-content">
          <h2 className="home-promotii-title">Cele mai bune promoții</h2>
          <div className="home-promotii-cards-grid">
            {[1, 2, 3, 4].map(numar => (
              <div
                key={numar}
                className="home-promotie-card"
                onClick={() => navigate('/promotii')}
                style={{ cursor: 'pointer' }}
              >
                <img
                  src={`/promotii/promotie_${numar}.jpg`}
                  alt={`Promotie ${numar}`}
                  className="home-promotie-img"
                />
              </div>
            ))}
          </div>
        </div>

        <div className="sidebar-form">
          <TicketSidebar selectedCote={selectedCote} onClear={() => setSelectedCote([])} />
        </div>
      </div>
    </div>
  );
};

export default Home;
