import { Route, Routes } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";

function App() {
  return (
    <Routes>
      <Route path="/analyze" element={<Analyze />} />
    </Routes>
  );
}

export default App;
