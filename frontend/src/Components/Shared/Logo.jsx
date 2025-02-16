import { Typography } from '@mui/material'
import React from 'react'
import { Link } from 'react-router-dom'

const Logo = () => {
    return (
        <div className='flex mr-auto items-center gap-3.5'>
            <Link to="/">
                <img src="gemini.png" alt="Gemini" className='w-7.5 h-7.5' />
            </Link>
            <Typography sx={{
                display: { md: "block", sm: "none", xs: "none" }, mr: "auto",
                fontWeight: "800", textShadow: "2px 2px 2px #000"
            }}>
                <span className='text-xl'>Boot</span>-AI
            </Typography>
        </div>
    )
}

export default Logo
