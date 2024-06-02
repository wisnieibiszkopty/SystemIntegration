import './App.css'
import Routing from "./routes/Routing.tsx";
import {GameProvider} from "./contexts/GameContext.tsx";

function App() {
  return (
    <>
        <GameProvider>
            <Routing/>
        </GameProvider>
    </>
  )
}

export default App;
