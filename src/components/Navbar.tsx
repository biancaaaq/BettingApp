import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getRole, logout } from '../services/authService';
import '../design/Home.css';

const Navbar: React.FC = () => {
  const token = localStorage.getItem('token');
  const role = getRole();
  const username = sessionStorage.getItem('username') || 'Vizitator';
  const initiale = username.slice(0, 2).toUpperCase();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <Link to="/home" className="logo">SportsBetting</Link>

      <div className="nav-links">
        <Link to="/meciuri">Meciuri</Link>
        <Link to="/live">Cote Live</Link>
        <Link to="/bilete/mele">Biletele Mele</Link>
        <Link to="/grupuri">Grupuri</Link> {/* Re-adăugat link-ul */}
        <Link to="/contul-meu">Contul Meu</Link>
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
  );
};

export default Navbar;