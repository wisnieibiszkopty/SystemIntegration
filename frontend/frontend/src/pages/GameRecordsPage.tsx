import React, {useEffect, useRef, useState} from "react";
import {Game, GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import {useLocation} from "react-router-dom";
import LineChart from "../components/LineChart.tsx";
import {Chart} from "chart.js";
import GameRecordsTable from "../components/GameRecordsTable.tsx";
import GameInfo from "../components/GameInfo.tsx";
import {useAuthContext} from "../contexts/AuthContext.tsx";

const GameRecordsPage: React.FC = () => {
    const {token} = useAuthContext();
    const [records, setRecords] = useState<GameRecord[]>([]);
    const location = useLocation();
    const { game } = location.state as {game: Game};
    const chartRef = useRef<Chart | null>(null);

    useEffect(() => {
        const fetchRecords = async () => {
            try {
                console.log("FETCHRECORDS START");
                const recordsData = await getRecords(game.id, token);
                setRecords(recordsData);
                console.log("FETCHRECORDS END");
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };
        fetchRecords()
    }, [game]);

    return (
        <>
            <GameInfo game={game}/>
            <div>
                {records.length !== 0 && <LineChart ref={chartRef} data={records}/>}
            </div>
            {records.length !== 0 && <GameRecordsTable records={records}/>}
            <footer>
                <div className="footer menu-text">@WPWK</div>
            </footer>
        </>
    )
}
export default GameRecordsPage;