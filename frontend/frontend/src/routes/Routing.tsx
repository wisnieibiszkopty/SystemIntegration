import {BrowserRouter, Route, Routes} from "react-router-dom";
import GamesPage from "../pages/GamesPage.tsx";
import GameRecordsPage from "../pages/GameRecordsPage.tsx";
import Test from "../pages/test.tsx";

const Routing = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<Test/>}/>
                <Route path={"/games"} element={<GamesPage/>}/>
                <Route path={"/games/:gameId/records"} element={<GameRecordsPage/>}/>
                <Route path="*" element={
                    <div style={{
                        display: 'flex',
                        height: '90vh',
                        alignItems: 'center',
                        justifyContent:'center',
                    }}>
                        <h1 style={{ color: 'white', fontSize: '400%'}}>404ok?</h1>
                    </div>
                }/>
            </Routes>
        </BrowserRouter>
    )
}
export default Routing;