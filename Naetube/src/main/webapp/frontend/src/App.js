import { Route, Routes } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";
import Search from "./components/SearchPage/Search"
import Test from "./components/Test/Test"

function App() {
  return (
    <Routes>
      <Route path="/analyze/:url" element={<Analyze />}/>
      <Route path="/" element={<Search />} />
      <Route path="/test" element={<Test/>}/>
    </Routes>
  );
}

export default App;
