import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import App1 from './components/LoginPage.js';
import App2 from './components/MainPage.js';
import App3 from './components/EstimationPage.js';
import App4 from './components/MembershipPage.js';
import App5 from './components/AdminPage.js';
import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
  const [hello, setHello] = useState('')

    useEffect(() => {
        axios.get('/api/login')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

  return (
    <div id='App'>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path='/login' element={<App1/>}/>
          <Route path='/main' element={<App2/>}/>
          <Route path='/estimation' element={<App3/>}/>
          <Route path='/user/join' element={<App4 />} />
          <Route path='/admin' element={<App5/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;