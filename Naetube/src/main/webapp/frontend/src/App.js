import { Route, Routes, Switch } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";

function App() {
  return (
    <Routes>
      <Route path="/analyze" element={<Analyze />}>

      </Route>
    </Routes>
  );
}

export default App;
