import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { createTheme, ThemeProvider } from '@mui/material'
import axios from 'axios'
import { Toaster } from 'react-hot-toast'
import { AuthProvider } from './context/AuthContext.jsx'


axios.defaults.baseURL = "http://localhost:5000/api/";
axios.defaults.withCredentials = true;

const theme = createTheme({
  typography: { fontFamily: "Roboto Slab, serif", allVariants: { color: "white" } }
})

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <Toaster position='top-right' />
          <App />
        </ThemeProvider>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>,
)
