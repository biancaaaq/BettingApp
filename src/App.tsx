// import React from 'react';
// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import Login from './components/Login';
// import Home from './components/Home';
// import AdminPanel from './components/AdminPanel';
// import AddUtilizator from './components/AddUtilizator';
// import AddTranzactie from './components/AddTranzactie';
// import AddBilet from './components/AddBilet';
// import AddDetaliuBilet from './components/AddDetaliuBilet';
// import AddGrupPrivat from './components/AddGrupPrivat';
// import AddRolUtilizator from './components/AddRolUtilizator';
// import AddPromotie from './components/AddPromotie';
// import AddCerereAutoexcludere from './components/AddCerereAutoexcludere';
// import AddCota from './components/AddCota';
// import AddMeci from './components/AddMeci';
// import AddBalanta from './components/AddBalanta';
// import CreateTicket from './components/CreateTicket';
// import Tranzactii from './components/Tranzactii';
// import BileteMele from './components/BileteMele';
// import Autoexcludere from './components/Autoexcludere';
// import ContAutoexclus from './components/ContAutoexclus';
// import AprobaCereri from './components/AprobaCereri';
// import ManageUsers from './components/ManageUsers';
// import PrivateRoute from './components/PrivateRoute';

// const App: React.FC = () => {
//     return (
//   <Routes>
//     {/* Public */}
//     <Route path="/home" element={<Home />} />
//     <Route path="/login" element={<Login />} />

//     {/* Protejate */}
//     <Route path="/bilete/creare" element={
//       <PrivateRoute><CreateTicket /></PrivateRoute>
//     } />
//     <Route path="/bilete/mele" element={
//       <PrivateRoute><BileteMele /></PrivateRoute>
//     } />
//     <Route path="/tranzactii" element={
//       <PrivateRoute><Tranzactii /></PrivateRoute>
//     } />

//     {/* Redirect implicit */}
//     <Route path="*" element={<Home />} />
//   </Routes>
// );
//     return (
//         <Router>
//             <Routes>
//                 <Route path="/login" element={<Login />} />
//                 <Route path="/home" element={<Home />} />
//                 <Route path="/bilete/creare" element={<CreateTicket />} />
//                 <Route path="/bilete/mele" element={<BileteMele />} />
//                 <Route path="/tranzactii" element={<Tranzactii />} />
//                 <Route path="/autoexcludere" element={<Autoexcludere />} />
//                 <Route path="/cont-autoexclus" element={<ContAutoexclus />} />
//                 <Route path="/admin" element={<AdminPanel />} />
//                 <Route path="/admin/add-utilizator" element={<AddUtilizator />} />
//                 <Route path="/admin/add-tranzactie" element={<AddTranzactie />} />
//                 <Route path="/admin/add-bilet" element={<AddBilet />} />
//                 <Route path="/admin/add-detaliu-bilet" element={<AddDetaliuBilet />} />
//                 <Route path="/admin/add-grup-privat" element={<AddGrupPrivat />} />
//                 <Route path="/admin/add-rol-utilizator" element={<AddRolUtilizator />} />
//                 <Route path="/admin/add-promotie" element={<AddPromotie />} />
//                 <Route path="/admin/add-cerere-autoexcludere" element={<AddCerereAutoexcludere />} />
//                 <Route path="/admin/aproba-cereri" element={<AprobaCereri />} />
//                 <Route path="/admin/manage-users" element={<ManageUsers />} />
//                 <Route path="/admin/add-cota" element={<AddCota />} />
//                 <Route path="/admin/add-meci" element={<AddMeci />} />
//                 <Route path="/admin/add-balanta" element={<AddBalanta />} />
//                 <Route path="/" element={<Home />} />
//             </Routes>
//         </Router>
//     );
// };

// export default App;

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
import PrivateRoute from './components/PrivateRoute';
import Matches from './components/Matches';
import LiveOdds from './components/LiveOdds';
import Layout from './components/Layout';
import { BetSlipProvider } from './context/BetSlipContext';


const App: React.FC = () => {
    return (
      <BetSlipProvider>
        <Router>
            <Routes>
                {/* Public */}
                <Route path="/login" element={<Login />} />
                <Route path="/home" element={<Home />} />
                <Route path="/meciuri" element={<Layout><Matches /></Layout>} />
                <Route path="/live" element={<Layout><LiveOdds /></Layout>} />

                {/* Protejate */}
                {/* <Route path="/bilete/creare" element={<Layout><PrivateRoute><CreateTicket /></PrivateRoute></Layout>} /> */}
                <Route path="/bilete/mele" element={<Layout><PrivateRoute><BileteMele /></PrivateRoute></Layout>} />
                <Route path="/tranzactii" element={<Layout><PrivateRoute><Tranzactii /></PrivateRoute></Layout>} />
                <Route path="/autoexcludere" element={<Layout><PrivateRoute><Autoexcludere /></PrivateRoute></Layout>} />
                <Route path="/cont-autoexclus" element={<PrivateRoute><ContAutoexclus /></PrivateRoute>} />
                <Route path="/admin" element={<Layout><PrivateRoute><AdminPanel /></PrivateRoute></Layout>} />
                <Route path="/admin/add-utilizator" element={<PrivateRoute><AddUtilizator /></PrivateRoute>} />
                <Route path="/admin/add-tranzactie" element={<PrivateRoute><AddTranzactie /></PrivateRoute>} />
                <Route path="/admin/add-bilet" element={<PrivateRoute><AddBilet /></PrivateRoute>} />
                <Route path="/admin/add-detaliu-bilet" element={<PrivateRoute><AddDetaliuBilet /></PrivateRoute>} />
                <Route path="/admin/add-grup-privat" element={<PrivateRoute><AddGrupPrivat /></PrivateRoute>} />
                <Route path="/admin/add-rol-utilizator" element={<PrivateRoute><AddRolUtilizator /></PrivateRoute>} />
                <Route path="/admin/add-promotie" element={<PrivateRoute><AddPromotie /></PrivateRoute>} />
                <Route path="/admin/add-cerere-autoexcludere" element={<PrivateRoute><AddCerereAutoexcludere /></PrivateRoute>} />
                <Route path="/admin/aproba-cereri" element={<PrivateRoute><AprobaCereri /></PrivateRoute>} />
                <Route path="/admin/manage-users" element={<PrivateRoute><ManageUsers /></PrivateRoute>} />
                <Route path="/admin/add-cota" element={<PrivateRoute><AddCota /></PrivateRoute>} />
                <Route path="/admin/add-meci" element={<PrivateRoute><AddMeci /></PrivateRoute>} />
                <Route path="/admin/add-balanta" element={<PrivateRoute><AddBalanta /></PrivateRoute>} />
               

                {/* Implicit redirect către home */}
                <Route path="*" element={<Home />} />
            </Routes>
        </Router>
     </BetSlipProvider>     
    );
};

export default App;
