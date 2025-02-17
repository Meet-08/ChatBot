import React from 'react'
import Header from './Components/Header'
import { Route, Routes } from 'react-router-dom'
import { Home, Chat, Login, NotFound, Signup } from './pages/page'

const App = () => {
  return (
    <main>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </main>
  )
}

export default App
