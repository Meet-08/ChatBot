import React from 'react';
import { TypeAnimation } from 'react-type-animation';

const AnimatedTextMessage = ({ text, delay = 50 }) => {
    return (
        <TypeAnimation
            sequence={[
                text,
                delay,
            ]}
            wrapper="span"
            cursor={true}
            repeat={0}
            style={{ display: 'inline-block' }}
        />
    );
};

export default AnimatedTextMessage;
