import React from 'react'
import { Link } from 'react-router-dom'

const NavigationLink = (props) => {
    return (
        <Link to={props.to} onClick={props.onClick}
            className={`${props.bg} ${props.textColor} font-semibold uppercase mx-2.5 py-2 px-5 rounded-xl tracking-[1px]`} >
            {props.text}
        </Link>
    )
}

export default NavigationLink
