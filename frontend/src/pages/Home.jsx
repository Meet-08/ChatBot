import React from 'react'
import { Box, useMediaQuery, useTheme } from '@mui/material'
import TypeAnimation from '../Components/TypingAnimation'
import Footer from '../Components/Footer'

const Home = () => {

    const theme = useTheme();
    const isBelowMd = useMediaQuery(theme.breakpoints.down("md"));


    return (
        <Box width={'100%'} height={'100%'}>
            <Box sx={{
                display: "flex",
                width: "100%",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                mx: "auto",
                mt: 3
            }}>

                <TypeAnimation />

            </Box>

            <Box sx={{
                display: "flex",
                width: "100%",
                flexDirection: { md: "row", xs: "column", sm: "column" },
                gap: 5,
                my: 10,
            }}>
                <img src="robot.png" alt="Robot" className='w-[200px] m-auto' />
                <img src="openai.png" alt="openai" className='w-[200px] m-auto invert-100 rotate' />
            </Box>

            <Box sx={{
                display: "flex",
                width: "100%",
                mx: "auto"
            }}>

                <img src="chat.png" alt="chat" className='flex m-auto rounded-2xl'
                    style={{
                        width: isBelowMd ? "80%" : "60%",
                        boxShadow: "5px -5px 105px #64f3d5",
                        marginTop: 20,
                        marginBottom: 20,
                    }} />

            </Box>

            <Footer />

        </Box>
    )
}

export default Home
