import React, { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Avatar, Box, Button, IconButton, Typography } from '@mui/material'
import { useAuth } from '../context/AuthContext'
import { red } from '@mui/material/colors';
import ChatItem from '../Components/chat/ChatItem';
import { IoMdSend } from 'react-icons/io';
import { MdDelete } from "react-icons/md";
import { sendChatRequest, getPreviousChatRequest, deleteChats } from '../helpers/apiCommunicators';
import toast from 'react-hot-toast';


const Chat = () => {

    const auth = useAuth();
    const inputRef = useRef(null);
    const [chatMessages, setChatMessages] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        if (!auth?.user) {
            return navigate("/");
        }
    }, []);

    useEffect(() => {
        if (auth?.isLoggedIn && auth?.user) {
            toast.loading("Loading Chats", { id: "loadchats" });
            getPreviousChatRequest().then((data) => {
                setChatMessages(data);
                toast.success("Successfully loaded chats", { id: "loadchats" });
            }).catch((err) => {
                console.log(err);
                toast.error("Loading failed", { id: "loadchats" });
            });
        }
    }, [auth]);

    const handleSubmit = async () => {
        const content = inputRef.current?.value;
        if (inputRef && inputRef.current) {
            inputRef.current.value = "";
        }
        const newMessage = {
            role: "user",
            content: content
        }

        setChatMessages((prev) => [...prev, newMessage]);

        const chatData = await sendChatRequest(content);
        setChatMessages([...chatData]);
    }

    const handleDeleteChats = async () => {
        toast.loading("Deleting Chats", { id: "deletechats" });
        try {
            deleteChats();
            setChatMessages([]);
            toast.success("Successfully deleted chats", { id: "deletechats" });
        } catch (error) {
            console.log(error);
            toast.error("Deleting failed", { id: "deletechats" });
        }
    }

    return (
        <Box sx={{ display: "flex", flex: 1, width: "100%", height: "100%", mt: 3, gap: 3 }}>
            <Box sx={{ display: { md: "flex", sm: "none", xs: "none" }, flex: 0.2, flexDirection: "column" }}>
                <Box sx={{
                    display: "flex",
                    width: "100%",
                    height: "auto",
                    bgcolor: "rgb(17, 29, 39)",
                    borderRadius: 5,
                    p: 1.5,
                    flexDirection: "column",
                    mx: 3
                }}>
                    <Avatar sx={{
                        mx: "auto", my: 2,
                        bgcolor: "white", color: "black",
                        fontWeight: 700
                    }}>

                        {auth?.user?.name[0]}
                    </Avatar>

                    <Typography sx={{
                        mx: "auto", fontFamily: "Work Sans"
                    }}>
                        You are talking to a Qwen
                    </Typography>
                    <Typography sx={{
                        mx: "auto", fontFamily: "Work Sans", my: 4, p: 3
                    }}>
                        You can ask some question related to knowledge, Business, Advices, Education, etc. But avoid sharing personal information
                    </Typography>
                    <Button sx={{
                        width: "200px",
                        my: 6,
                        color: "white",
                        fontWeight: 700,
                        borderRadius: 3,
                        mx: "auto",
                        bgcolor: red[400],
                        ":hover": {
                            bgcolor: red.A400
                        }
                    }}
                        onClick={handleDeleteChats}
                    >
                        Clear conversion
                    </Button>
                </Box>
            </Box>

            <Box sx={{ display: "flex", flex: { md: 0.8, sm: 1, xs: 1 }, flexDirection: "column", px: 3 }}>
                <Typography sx={{
                    textAlign: "center", fontSize: "40px",
                    color: "white",
                    mb: 2, mx: "auto",
                    fontWeight: 600
                }}>
                    Model - Qwen-2.5:0.5b
                </Typography>
                <Box sx={{
                    width: { md: "75vw", sm: "100%", xs: "100%" },
                    height: "60vh",
                    borderRadius: 3,
                    mx: "auto",
                    display: "flex",
                    flexDirection: "column",
                    overflow: "scroll",
                    overflowX: "hidden",
                    overflowY: "auto",
                    scrollBehavior: "smooth",
                }}>
                    {
                        chatMessages.map((chat, index) => (
                            <ChatItem content={chat.content} role={chat.role} key={index} />
                        ))
                    }
                </Box>
                <div className='w-full rounded-lg bg-[rgb(17,27,39)] flex m-auto mt-6 md:mt-2'>
                    {" "}
                    <input
                        ref={inputRef}
                        type="text"
                        className='w-full bg-transparent p-3 text-xl outline-none text-white'
                    />
                    <IconButton onClick={handleDeleteChats}
                        sx={{
                            ml: "auto",
                            color: "white",
                            display: { md: "none", sm: "block", xs: "block" }
                        }}>
                        <MdDelete />
                    </IconButton>
                    <IconButton onClick={handleSubmit}
                        sx={{
                            ml: "auto",
                            color: "white",
                        }}>
                        <IoMdSend />
                    </IconButton>
                </div>
            </Box>
        </Box>
    )
}

export default Chat
