import React, {useEffect, useRef, useState} from "react";
import {defaultGame, Game, GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import {useParams} from "react-router-dom";
import LineChart from "../components/LineChart.tsx";
import {Chart} from "chart.js";
import GameRecordsTable from "../components/GameRecordsTable.tsx";
import {getGame} from "../api/services/Game.ts";
import GameInfo from "../components/GameInfo.tsx";

const GameRecordsPage: React.FC = () => {
    const [records, setRecords] = useState<GameRecord[]>([]);
    const [game, setGame] = useState<Game>(defaultGame);
    const { gameId } = useParams<{gameId: string}>();
    const chartRef = useRef<Chart | null>(null);

    useEffect(() => {
        const fetchGame = async () => {
            try {
                const gameData = await getGame(parseInt(gameId));
                setGame(gameData);
            } catch (error) {
                console.error("fetchGame() - error fetching game: ", error);
            }
        }
        const fetchRecords = async () => {
            try {
                const recordsData = await getRecords(gameId);
                setRecords(recordsData);
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };
        fetchGame().then(fetchRecords);
    }, [gameId]);

    return (
        <>
            <GameInfo game={game}/>
            <div>
                <LineChart ref={chartRef} data={records}/>
            </div>
            <GameRecordsTable records={records}/>
        </>
    )
}
export default GameRecordsPage;