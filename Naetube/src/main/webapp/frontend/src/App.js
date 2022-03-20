import { Route, Routes, Switch } from "react-router-dom";
import Analyze from "./components/Analyze/Analyze";

function App() {
  return (
    <Switch>
      <Route path="/analyze">
        <Analyze />}
      </Route>
    </Switch>
  );
}

export default App;
