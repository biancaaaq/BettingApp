import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Home from './components/Home';
import AdminPanel from './components/AdminPanel';
import AddUtilizator from './components/AddUtilizator';
import AddTranzactie from './components/AddTranzactie';
import AddBilet from './components/AddBilet';
import AddDetaliuBilet from './components/AddDetaliuBilet';
import AddGrupPrivat from './components/AddGrupPrivat';
import AddRolUtilizator from './components/AddRolUtilizator';
import AddPromotie from './components/AddPromotie';
import AddCerereAutoexcludere from './components/AddCerereAutoexcludere';
import AddCota from './components/AddCota';
import AddMeci from './components/AddMeci';
import AddBalanta from './components/AddBalanta';
import CreateTicket from './components/CreateTicket';
import PrivateRoute from './services/PrivateRoute';

const App: React.FC = () => {
    return (
        <Router>
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/home" element={<PrivateRoute><Home /></PrivateRoute>} />
                    <Route path="/create-ticket" element={<PrivateRoute><CreateTicket /></PrivateRoute>} />
                    <Route path="/admin" element={<PrivateRoute requiredRole="ADMIN"><AdminPanel /></PrivateRoute>} />
                    <Route path="/admin/add-utilizator" element={<PrivateRoute requiredRole="ADMIN"><AddUtilizator /></PrivateRoute>} />
                    <Route path="/admin/add-tranzactie" element={<PrivateRoute requiredRole="ADMIN"><AddTranzactie /></PrivateRoute>} />
                    <Route path="/admin/add-bilet" element={<PrivateRoute requiredRole="ADMIN"><AddBilet /></PrivateRoute>} />
                    <Route path="/admin/add-detaliu-bilet" element={<PrivateRoute requiredRole="ADMIN"><AddDetaliuBilet /></PrivateRoute>} />
                    <Route path="/admin/add-grup-privat" element={<PrivateRoute requiredRole="ADMIN"><AddGrupPrivat /></PrivateRoute>} />
                    <Route path="/admin/add-rol-utilizator" element={<PrivateRoute requiredRole="ADMIN"><AddRolUtilizator /></PrivateRoute>} />
                    <Route path="/admin/add-promotie" element={<PrivateRoute requiredRole="ADMIN"><AddPromotie /></PrivateRoute>} />
                    <Route path="/admin/add-cerere-autoexcludere" element={<PrivateRoute requiredRole="ADMIN"><AddCerereAutoexcludere /></PrivateRoute>} />
                    <Route path="/admin/add-cota" element={<PrivateRoute requiredRole="ADMIN"><AddCota /></PrivateRoute>} />
                    <Route path="/admin/add-meci" element={<PrivateRoute requiredRole="ADMIN"><AddMeci /></PrivateRoute>} />
                    <Route path="/admin/add-balanta" element={<PrivateRoute requiredRole="ADMIN"><AddBalanta /></PrivateRoute>} />
                    <Route path="/" element={<Login />} />
                </Routes>
        </Router>
    );
};

export default App;