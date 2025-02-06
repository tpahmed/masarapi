import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Users from './pages/Users';
import Rapport from './pages/Rapport';
import Teacher from './pages/Teacher';
import Grades from './pages/grades';
import './index.css';


const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/users" element={<Users />} />
        <Route path="/rapport" element={<Rapport />} />
        <Route path="/teacher" element={<Teacher />} />
        <Route path="/grades" element={<Grades />} />

        </Routes>
    </Router>
  );
};

export default App; 