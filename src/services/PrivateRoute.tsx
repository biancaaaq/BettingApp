// import React from 'react';
// import { Navigate } from 'react-router-dom';
// import { getRole, getToken } from '../services/authService';

// interface PrivateRouteProps {
//     children: React.ReactElement; // Folosim React.ReactElement în loc de React.ReactNode
//     requiredRole?: string;
// }

// const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, requiredRole }) => {
//     const token = getToken();
//     const role = getRole();

//     if (!token) {
//         return <Navigate to="/login" />;
//     }

//     if (requiredRole && role !== requiredRole) {
//         return <Navigate to="/dashboard" />;
//     }

//     return children;
// };

// export default PrivateRoute;
import React, { JSX } from 'react';
import { Navigate } from 'react-router-dom';
import { getToken, getRole } from '../services/authService';




interface PrivateRouteProps {
    children: React.ReactElement; // Folosim React.ReactElement în loc de JSX.Element
    requiredRole?: string;
}
// const PrivateRoute = ({ children }: { children: JSX.Element }) => {
//   const token = localStorage.getItem('token');
//   return token ? children : <Navigate to="/login" />;
// };


const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, requiredRole }) => {
    // const token = getToken();
    const token = localStorage.getItem("token");
    // const role = getRole();
    return token ? children : <Navigate to="/login" />;

    // if (!token) {
    //     return <Navigate to="/login" replace />;
    // }

    // if (requiredRole && role !== requiredRole) {
    //     return <Navigate to="/home" replace />;
    // }

    // return children;
};


export default PrivateRoute;