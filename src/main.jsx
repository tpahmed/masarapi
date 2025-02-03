// src/main.jsx
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
// import { ToastProvider } from './context/ToastContext'; // Remove this line

createRoot(document.getElementById('root')).render(
  <StrictMode>
    {/* Remove ToastProvider wrapping */}
    <App />
  </StrictMode>
);