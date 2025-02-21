import React from 'react'
import { Avatar, Box, Typography } from '@mui/material'
import { useAuth } from '../../context/AuthContext'
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import ReactMarkdown from 'react-markdown';
import { coldarkDark } from 'react-syntax-highlighter/dist/esm/styles/prism';

const extractCodeFromString = (message) => {
    if (message.includes("```")) {
        const blocks = message.split("```");
        return blocks;
    }
}

const isCodeBlock = (str) => {
    if (str.includes("=") || str.includes(";") ||
        str.includes("[") || str.includes("]") ||
        str.includes("{") || str.includes("}") ||
        str.includes("#") || str.includes("//")) {
        return true;
    }
    return false;
}

const ChatItem = ({ content, role }) => {

    const auth = useAuth();
    const messageBlocks = extractCodeFromString(content);


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
                {
                    !messageBlocks && (
                        <Typography fontSize={"20px"}>
                            <ReactMarkdown>
                                {content}
                            </ReactMarkdown>
                        </Typography>
                    )
                }
                {
                    messageBlocks &&
                    messageBlocks.length &&
                    messageBlocks.map((block, index) =>
                        isCodeBlock(block) ? (
                            <SyntaxHighlighter key={index} style={coldarkDark} language='java' showLineNumbers wrapLongLines>
                                {block}
                            </SyntaxHighlighter>
                        ) : (
                            <Typography key={index} fontSize={"20px"}>
                                <ReactMarkdown>
                                    {block}
                                </ReactMarkdown>
                            </Typography>
                        )
                    )
                }
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
                <Typography fontSize={"20px"}>
                    {content}
                </Typography>
            </Box>
        </Box>
    )
}

export default ChatItem
