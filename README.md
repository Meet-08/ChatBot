# Full Stack AI Chat Application

A full stack AI chat application that provides users with an interactive and intelligent chat experience. This application is built with a modern web stack ensuring a responsive, secure, and scalable solution.

## Overview

The AI Chat Application offers:

- **Real-Time Communication:** Seamless interaction with an AI-powered chat assistant.
- **User-Centric Design:** A modern, responsive interface built with React, Tailwind CSS, and Material UI.
- **Robust Backend:** A Spring Boot-based backend managing RESTful APIs and business logic.
- **Efficient Data Management:** MongoDB is used for persisting user data and chat history.
- **Enhanced Security:** Secure authentication and authorization using JWT tokens integrated with Spring Security.

## Technology Stack

### Frontend

- **React:** JavaScript library for building user interfaces.
- **Tailwind CSS:** Utility-first CSS framework for rapid styling.
- **Material UI:** React components library for a sleek and modern UI.

### Backend

- **Spring Boot:** Framework for creating stand-alone, production-grade Spring based Applications.
- **Spring Security:** Provides robust security features including authentication and authorization.
- **JWT:** JSON Web Tokens used for secure, stateless authentication.
- **Ollama Integration:** AI model integration via Ollama, with configurable model options.

### Database

- **MongoDB:** NoSQL database for flexible and scalable data storage.

## Features

- **Interactive Chat:** Engage in a conversation with an AI assistant that processes and responds to queries in real time.
- **User Authentication:** Secure login and registration with JWT-based authentication.
- **Responsive UI:** Optimized for both desktop and mobile environments.
- **Modern Design:** Uses Tailwind CSS and Material UI to ensure an attractive, user-friendly interface.
- **Scalable Architecture:** The combination of Spring Boot and MongoDB supports high traffic and data loads.
- **Flexible AI Models:** Users can switch between different AI models available on Ollama by simply removing the `ollama.url` property from `application.properties` and specifying the desired model.
