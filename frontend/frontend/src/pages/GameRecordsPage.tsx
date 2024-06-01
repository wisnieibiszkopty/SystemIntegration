import React, {useEffect, useRef, useState} from "react";
import {defaultGame, Game, GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import {useParams} from "react-router-dom";
import LineChartComponent from "../components/LineChartComponent.tsx";
import {Chart} from "chart.js";
import GameRecordComponent from "../components/GameRecordComponent.tsx";
import {getGame} from "../api/services/Game.ts";

const GameRecordsPage: React.FC = () => {
    const [records, setRecords] = useState<GameRecord[]>([]);
    const [game, setGame] = useState<Game>(defaultGame);
    const { gameId } = useParams<{gameId: string}>();
    const chartRef = useRef<Chart | null>(null);

    useEffect(() => {
        const fetchRecords = async () => {
            try {
                if (gameId) {
                    const recordsData = await getRecords(gameId);
                    setRecords(recordsData);
                    const gameData = await getGame(parseInt(gameId));
                    setGame(gameData);
                } else {
                    console.error("fetchRecords() - gameId is null")
                }
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };
        fetchRecords().then();
    }, [gameId]);

    return (
        <>
            <div>
                <LineChartComponent ref={chartRef} data={records} game={game}/>
            </div>
            {records && records.map((record) => (
                <GameRecordComponent record={record} key={record.id}/>
            ))}
        </>
    )
}
export default GameRecordsPage;