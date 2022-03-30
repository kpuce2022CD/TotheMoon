import { Route, Routes } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";

function App() {
  return (
    <Routes>
      <Route path="/analyze/:url" element={<Analyze />}>
      </Route>
    </Routes>
  );
}

export default App;
