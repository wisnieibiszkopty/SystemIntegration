import React, {useEffect, useState} from "react";
import {GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import GameRecordComponent from "../components/GameRecordComponent.tsx";
import {useParams} from "react-router-dom";

const GameRecordsPage: React.FC = () => {

    const [records, setRecords] = useState<GameRecord[]>();
    const { gameId } = useParams<{gameId: string}>();

    useEffect(() => {
        const fetchRecords = async () => {
            try {
                if (gameId) {
                    const recordsData = await getRecords(gameId);
                    setRecords(recordsData);
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
            <h2>GameRecords dla id={gameId}</h2>
            <div>
                {records && records.map((record) => (
                    <GameRecordComponent record={record} key={record.id}/>
                ))}
            </div>
        </>
    )
}
export default GameRecordsPage;