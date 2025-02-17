import React from 'react'
import { Avatar, Box, Typography } from '@mui/material'
import { useAuth } from '../../context/AuthContext'

const ChatItem = ({ content, role }) => {

    const auth = useAuth();

    return role === "assistant" ? (
        <Box sx={{
            display: "flex",
            width: { md: "50%", sm: "100%", xs: "100%" },
            p: 2, my: 2,
            bgcolor: "#004d5612",
            gap: 2,
            borderRadius: 3
        }}>
            <Avatar sx={{
                ml: 0,
            }}>
                <img src="openai.png" alt="openai" className='w-7.5' />
            </Avatar>
            <Box>
                <Typography fontSize={"20px"}>
                    {content}
                </Typography>
            </Box>
        </Box>
    ) : (
        <Box sx={{
            display: "flex",
            p: 2,
            ml: "auto",
            width: { md: "50%", sm: "100%", xs: "100%" },
            bgcolor: "#004d56",
            gap: 2,
            borderRadius: 3,
        }}>
            <Avatar sx={{
                ml: 0,
                bgcolor: "black",
                color: "white"
            }}>
                {auth?.user?.name[0]}
            </Avatar>
            <Box>
                <Typography fontSize={"20px"} color='textSecondary'>
                    {content}
                </Typography>
            </Box>
        </Box>
    )
}

export default ChatItem
