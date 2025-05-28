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
import Tranzactii from './components/Tranzactii';
import BileteMele from './components/BileteMele';
import Autoexcludere from './components/Autoexcludere';
import ContAutoexclus from './components/ContAutoexclus';
import AprobaCereri from './components/AprobaCereri';
import ManageUsers from './components/ManageUsers';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/home" element={<Home />} />
                <Route path="/bilete/creare" element={<CreateTicket />} />
                <Route path="/bilete/mele" element={<BileteMele />} />
                <Route path="/tranzactii" element={<Tranzactii />} />
                <Route path="/autoexcludere" element={<Autoexcludere />} />
                <Route path="/cont-autoexclus" element={<ContAutoexclus />} />
                <Route path="/admin" element={<AdminPanel />} />
                <Route path="/admin/add-utilizator" element={<AddUtilizator />} />
                <Route path="/admin/add-tranzactie" element={<AddTranzactie />} />
                <Route path="/admin/add-bilet" element={<AddBilet />} />
                <Route path="/admin/add-detaliu-bilet" element={<AddDetaliuBilet />} />
                <Route path="/admin/add-grup-privat" element={<AddGrupPrivat />} />
                <Route path="/admin/add-rol-utilizator" element={<AddRolUtilizator />} />
                <Route path="/admin/add-promotie" element={<AddPromotie />} />
                <Route path="/admin/add-cerere-autoexcludere" element={<AddCerereAutoexcludere />} />
                <Route path="/admin/aproba-cereri" element={<AprobaCereri />} />
                <Route path="/admin/manage-users" element={<ManageUsers />} />
                <Route path="/admin/add-cota" element={<AddCota />} />
                <Route path="/admin/add-meci" element={<AddMeci />} />
                <Route path="/admin/add-balanta" element={<AddBalanta />} />
                <Route path="/" element={<Login />} />
            </Routes>
        </Router>
    );
};

export default App;