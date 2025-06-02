import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getRole, logout } from '../services/authService';
import '../design/Home.css';
import TicketSidebar from '../components/TicketSidebar';
import { useState } from 'react';
import LiveMatchesSidebar from './LiveMatchesSidebar';

const Home: React.FC = () => {
    const token = localStorage.getItem('token');
    const role = getRole();
    const navigate = useNavigate();

    const username = sessionStorage.getItem('username') || 'Utilizator';
    const initiale = username.slice(0, 2).toUpperCase();
    const [selectedCote, setSelectedCote] = useState([]);

    const handleCreate = (miza: number) => {
        // aici trimiți biletul la backend, dacă vrei
        console.log("Trimit bilet:", { miza, selectedCote });
    };

    const handleLogout = () => {
        logout();
        navigate('/login');
    };
    const handleGoToCreateTicket = () => {
        navigate('/bilete/creare');
    };

    return (
        <div className="home-page">
            <nav className="navbar">
                <Link to="/home" className="logo">SportsBetting</Link>

                <div className="nav-links">
                    {/* <Link to="/bilete/creare">Creare Bilet</Link> */}
                    <Link to="/meciuri">Meciuri</Link>
                    <Link to="/live">Cote Live</Link>
                    <Link to="/bilete/mele">Biletele Mele</Link>
                    {/* <Link to="/tranzactii">Tranzacții</Link>
                    <Link to="/autoexcludere">Auto-excludere</Link> */}
                    <Link to="/contul-meu">Contul-Meu</Link>
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
            <div className="home-content-layout">
            {/* Coloana stânga cu meciuri live */}
                <div className="left-sidebar">
                    <LiveMatchesSidebar />
                </div>
            </div>

            {token? (
            <div className="home-content">
                <h1 className="pariaza-titlu" onClick={handleGoToCreateTicket}>Pariază acum!</h1>
            </div>) :(<div className="home-content">
                         < Link to="/login" className="pariaza-titlu" >Pariază acum!</Link>
                       </div> )}
                                              <div>
                       <TicketSidebar selectedCote={selectedCote} onClear={() => setSelectedCote([])} />

</div>
                       </div>


       
    );
};

export default Home;
// import React from 'react';
// import { Link, useNavigate } from 'react-router-dom';
// import { getRole, logout } from '../services/authService';
// import '../design/Home.css';

// const Home: React.FC = () => {
//     const token = localStorage.getItem('token');
//     const role = getRole();
//     const navigate = useNavigate();

//     const username = localStorage.getItem('username') || 'Vizitator';
//     const initiale = username.slice(0, 2).toUpperCase();

//     const handleLogout = () => {
//         logout();
//         navigate('/login');
//     };

//     return (
//         <div className="home-page">
//             <nav className="navbar">
//                 <div className="logo">
//                     <Link to="/home" className="logo-link">SportsBetting</Link>
//                 </div>

//                 <div className="nav-links">
//                     <Link to="/bilete/creare">Creare Bilet</Link>
//                     <Link to="/bilete/mele">Biletele Mele</Link>
//                     <Link to="/tranzactii">Tranzacții</Link>
//                     <Link to="/autoexcludere">Auto-excludere</Link>
//                     {role === 'ADMIN' && <Link to="/admin">Admin Panel</Link>}
//                 </div>

//                 <div className="profile-info">
//                     {token ? (
//                         <>
//                             <div className="user-details">
//                                 <span className="username">{username}</span>
//                             </div>
//                             <button onClick={handleLogout} className="logout-button">Logout</button>
//                         </>
//                     ) : (
//                         <Link to="/login" className="login-button">Login</Link>
//                     )}
//                 </div>
//             </nav>

//             <div className="home-content">
//                 <h2>Bine ai venit!</h2>
//             </div>
//         </div>
//     );
// };

// export default Home;

