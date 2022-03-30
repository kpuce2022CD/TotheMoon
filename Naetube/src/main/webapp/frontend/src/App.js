import { Route, Routes } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";
import Search from "./components/SearchPage/Search"

function App() {
  return (
    <Routes>
      <Route path="/analyze/:url" element={<Analyze />}>
      </Route>
      <Route path="/" element={<Search />} />
    </Routes>
  );
}

export default App;
