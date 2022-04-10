import { Route, Routes } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";
import axios from 'axios';
import {useState} from 'react';
import Search from "./components/SearchPage/Search";




function App() {


  return (
    <Routes>
      <Route path="/analyze" element={<Analyze />} />
      <Route path="/" element={<Search />} />
    </Routes>


  );
}

export default App;
