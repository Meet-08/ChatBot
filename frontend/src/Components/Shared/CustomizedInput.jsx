import { TextField } from '@mui/material'
import React from 'react'

const CustomizedInput = (props) => {
    return (
        <TextField name={props.name} label={props.label} type={props.type} margin='normal'
            slotProps={{
                inputLabel: {
                    sx: {
                        color: "white",
                        '&.Mui-focused': {
                            color: 'white'
                        }
                    }
                },
                input: {
                    sx: {
                        width: "400px",
                        borderRadius: 2,
                        fontSize: 20,
                        color: "white",
                    }
                }
            }} />
    )
}

export default CustomizedInput
