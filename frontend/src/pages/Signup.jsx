import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Box, Button, Typography } from '@mui/material'
import { IoIosLogIn } from 'react-icons/io'
import toast from 'react-hot-toast'
import CustomizedInput from '../Components/Shared/CustomizedInput'
import { useAuth } from '../context/AuthContext'

const Signup = () => {


    const auth = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (auth?.isLoggedIn) {
            navigate("/");
        }
    }, [auth]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const name = formData.get("username");
        const email = formData.get("email");
        const password = formData.get("password")
        try {
            toast.loading("Signing Up", { id: "signup" });
            await auth?.signup(name, email, password);
            toast.success("Signed Up Successfully", { id: "signup" });
            setTimeout(() => {
                navigate("/");
            }, 100);
        } catch (error) {
            toast.success("Signed Up Failed", { id: "signup" });
            console.log(error);
        }
    }

    return (
        <Box width={{ md: "100%", sm: "100vw", xs: "100vw" }} height={"100%"} display={"flex"} flex={1}>
            <Box p={4} mt={4} display={{ md: "flex", sm: "none", xs: "none" }}>
                <img src="airobot.png" alt="AI Robot"
                    className='w-[400px]' />
            </Box>
            <Box
                display={"flex"}
                flex={{ xs: 1, md: 0.5 }}
                justifyContent={"center"}
                alignItems={"center"}
                px={8} ml={"auto"} mt={8}>

                <form onSubmit={handleSubmit}
                    className='m-auto p-7.5 rounded-xl' style={{ boxShadow: "10px 10px 20px #000" }}>
                    <Box sx={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
                        <Typography variant='h4' textAlign={"center"} p={2} fontWeight={600}>
                            Signup
                        </Typography>
                        <CustomizedInput type="text" name="username" label="User name" />
                        <CustomizedInput type="email" name="email" label="Email" />
                        <CustomizedInput type="password" name="password" label="Password" />
                        <Button type='submit'
                            sx={{
                                px: 2, py: 1,
                                mt: 2,
                                fontWeight: 600,
                                width: "400px",
                                color: "royalblue",
                                borderRadius: 2,
                                bgcolor: "#00fffc",
                                ":hover": { bgcolor: "white", color: "black" }
                            }}
                            endIcon={<IoIosLogIn size={24} />}>
                            Signup
                        </Button>
                    </Box>
                </form>

            </Box>
        </Box>
    )
}

export default Signup
