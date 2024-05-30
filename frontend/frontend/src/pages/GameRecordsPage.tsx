import {useEffect, useState} from "react";
import {GameRecord} from "../api/interfaces.ts";
import {getRecords} from "../api/services/GameRecord.ts";
import GameRecordComponent from "../components/GameRecordComponent.tsx";

const GameRecordsPage = () => {
    const [records, setRecords] = useState<GameRecord[]>();
    const gameId = 3;
    useEffect(() => {
        const fetchRecords = async () => {
            try {
                const recordsData = await getRecords(gameId);
                setRecords(recordsData);
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };

        fetchRecords().then();
    }, []);

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