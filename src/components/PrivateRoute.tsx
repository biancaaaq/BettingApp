import React from 'react';
import { Navigate } from 'react-router-dom';
import { getToken, getRole } from '../services/authService';

interface PrivateRouteProps {
    children: React.ReactElement; // Folosim React.ReactElement în loc de JSX.Element
    requiredRole?: string;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, requiredRole }) => {
    const token = getToken();
    const role = getRole();

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    if (requiredRole && role !== requiredRole) {
        return <Navigate to="/home" replace />;
    }

    return children;
};

export default PrivateRoute;