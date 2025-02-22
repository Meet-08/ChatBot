import React, { createContext, useContext, useEffect, useState } from 'react'
import { checkAuthStatus, loginUser, logoutUser, signupUser } from '../helpers/apiCommunicators';
import toast from 'react-hot-toast';

const AuthContext = createContext(null);
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        ; (async () => {
            const data = await checkAuthStatus();
            if (data) {
                setUser({ email: data.email, name: data.name });
                setIsLoggedIn(true);
            }
        })();
    }, []);

    const login = async (email, password) => {
        const data = await loginUser(email, password);
        if (data) {
            setUser({ email: data.email, name: data.name });
            setIsLoggedIn(true);
        }
    }

    const signup = async (name, email, password) => {
        const data = await signupUser(name, email, password);
        if (data) {
            setUser({ email: email, name: name });
            setIsLoggedIn(true);
        }
    }

    const logout = async () => {
        try {
            await logoutUser();
            toast.success("Logout Successfully");
            setUser(null);
            setIsLoggedIn(false);
        } catch (error) {
            console.log(error);
            toast.error("Logout failed")
        }
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
