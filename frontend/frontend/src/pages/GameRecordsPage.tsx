import React, {useEffect, useRef, useState} from "react";
import {Game, GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import {useLocation, useNavigate} from "react-router-dom";
import LineChart from "../components/LineChart.tsx";
import {Chart} from "chart.js";
import GameRecordsTable from "../components/GameRecordsTable.tsx";
import GameInfo from "../components/GameInfo.tsx";
import {useAuthContext} from "../contexts/AuthContext.tsx";
import LoadingSpinner from "../components/LoadingSpinner.tsx";

const GameRecordsPage: React.FC = () => {
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const {token, isAuth} = useAuthContext();
    const [records, setRecords] = useState<GameRecord[]>([]);
    const location = useLocation();
    const navigate = useNavigate();
    const { game } = location.state as {game: Game};
    const chartRef = useRef<Chart | null>(null);

    useEffect(() => {
        if(!isAuth) {
            navigate('/');
            alert("Zaloguj się aby mieć dostęp do tej podstrony");
        }
    }, []);

    useEffect(() => {
        const fetchRecords = async () => {
            setIsLoading(true);
            console.log("useEffect GameRecordSPAge")
            try {
                console.log("FETCHRECORDS START");
                const recordsData = await getRecords(game.id, token);
                setRecords(recordsData);
                console.log("FETCHRECORDS END");
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            } finally {
                setIsLoading(false);
            }
        };
        if (isAuth) {
            fetchRecords()
        }
    }, [game.id, token, isAuth]);

    return (
        <>
            {!isLoading ?
                <div>
                    <GameInfo game={game}/>
                    <div>
                        <LineChart ref={chartRef} data={records}/>
                    </div>
                    <GameRecordsTable records={records}/>
                    <footer>
                        <div className="footer menu-text">@WPWK</div>
                    </footer>
                </div>
            :
                <LoadingSpinner/>
            }
        </>
    )
}
export default GameRecordsPage;