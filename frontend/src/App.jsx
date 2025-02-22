import React from 'react'
import Header from './Components/Header'
import { Route, Routes } from 'react-router-dom'
import { Home, Chat, Login, NotFound, Signup } from './pages/page'
import { useAuth } from './context/AuthContext'

const App = () => {

  const auth = useAuth();

  return (
    <main>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        {auth?.user && auth?.isLoggedIn && < Route path="/chat" element={<Chat />} />}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </main>
  )
}

export default App
