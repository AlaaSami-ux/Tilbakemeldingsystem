import { Header } from "./components/Header";
import { Footer } from "@fremtind/jkl-footer-react";
import {TbTable} from "./components/Body/TbTable";

function App() {
  return (
    <div className="App">
      <Header/>
      <TbTable/>
      {/* <Footer /> */}
    </div>
  );
}

export default App;
