import React, { createContext, useContext, useEffect, useState } from 'react'

const AuthContext = createContext(null);
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        //TO do
    }, []);

    const login = async (email, password) => {

    }

    const signup = async (name, email, password) => {

    }

    const logout = async () => {

    }

    const value = {
        user,
        isLoggedIn,
        login,
        signup,
        logout
    }
    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
