import React from 'react';
import { Navigate } from 'react-router-dom';
import { getRole, getToken } from '../services/authService';

interface PrivateRouteProps {
    children: React.ReactElement; // Folosim React.ReactElement în loc de React.ReactNode
    requiredRole?: string;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, requiredRole }) => {
    const token = getToken();
    const role = getRole();

    if (!token) {
        return <Navigate to="/login" />;
    }

    if (requiredRole && role !== requiredRole) {
        return <Navigate to="/dashboard" />;
    }

    return children;
};

export default PrivateRoute;