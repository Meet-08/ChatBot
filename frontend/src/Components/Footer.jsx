import React from 'react'

const Footer = () => {
    return (
        <footer>
            <div
                style={{
                    width: "100%",
                    minHeight: "20vh",
                    maxHeight: "30vh",
                    marginTop: 60,
                }}
            >
                <p style={{ fontSize: "30px", textAlign: "center", padding: "20px" }}>
                    Built With love by
                    <span>
                        <a
                            target='_blank'
                            style={{ color: "steelblue" }}
                            className="font-semibold uppercase mx-1 py-2 px-5 rounded-xl tracking-[1px]"
                            href={"https://www.linkedin.com/in/meet-bhuva-75a996333"}
                        >
                            Meet
                        </a>
                    </span>
                    💘
                </p>
            </div>
        </footer>
    )
}

export default Footer
