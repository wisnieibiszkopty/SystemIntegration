import './App.css'
import Routing from "./routes/Routing.tsx";
import {GameProvider} from "./contexts/GameContext.tsx";
import {AuthProvider, useAuthContext} from "./contexts/AuthContext.tsx";
import {useEffect} from "react";

function App() {
    const {updateToken} = useAuthContext();
    useEffect(() => {
        const token =localStorage.getItem("token");
        if (token){
            updateToken(token)
        }
    });
  return (
    <>
        <AuthProvider>
            <GameProvider>
                <Routing/>
            </GameProvider>
        </AuthProvider>
    </>
  )
}

export default App;
