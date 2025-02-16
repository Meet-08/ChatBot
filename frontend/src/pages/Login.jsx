import React from 'react'
import { Box, Button, Typography } from '@mui/material'
import CustomizedInput from '../Components/Shared/CustomizedInput'

const Login = () => {
    return (
        <Box width={"100%"} height={"100%"} display={"flex"} flex={1}>
            <Box p={4} mt={4} display={{ md: "flex", sm: "none", xs: "none" }}>
                <img src="airobot.png" alt="AI Robot"
                    className='w-[400px]' />
            </Box>
            <Box
                display={"flex"}
                flex={{ xs: 1, md: 0.5 }}
                justifyContent={"center"}
                alignItems={"center"}
                p={2} ml={"auto"} mt={16}>

                <form className='m-auto p-7.5 rounded-xl' style={{ boxShadow: "10px 10px 20px #000" }}>
                    <Box sx={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
                        <Typography variant='h4' textAlign={"center"} p={2} fontWeight={600}>
                            Login
                        </Typography>
                        <CustomizedInput type="email" name="email" label="Email" />
                        <CustomizedInput type="password" name="password" label="Password" />
                        <Button type='submit'
                            sx={{
                                px: 2, py: 1,
                                mt: 2,
                                width: "400px",
                                borderRadius: 2,
                                bgcolor: "#00fffc",
                                ":hover": { bgcolor: "white", color: "black" }
                            }}>
                            Login
                        </Button>
                    </Box>
                </form>

            </Box>
        </Box>
    )
}

export default Login
