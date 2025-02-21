import React from 'react'
import { AppBar, Toolbar } from '@mui/material'
import Logo from './Shared/Logo'
import { useAuth } from '../context/AuthContext'
import NavigationLink from './Shared/NavigationLink'


const Header = () => {

    const auth = useAuth();

    return (
        <AppBar sx={{ bgcolor: "transparent", position: "static", boxShadow: "none" }}>
            <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
                <Logo />
                <div>
                    {auth?.isLoggedIn ? (
                        <>
                            <NavigationLink bg='bg-[#00fffc]' to="/chat" text="Go to Chat" textColor="text-black" />
                            <NavigationLink bg='bg-[#51538f]' to="/" text="logout" textColor="text-white" onClick={auth.logout} />
                        </>
                    ) : (
                        <>
                            <NavigationLink bg='bg-[#00fffc]' to="/login" text="Login" textColor="text-black" />
                            <NavigationLink bg='bg-[#51538f]' to="/signup" text="signup" textColor="text-white" />
                        </>
                    )}
                </div>
            </Toolbar>
        </AppBar>
    )
}

export default Header
