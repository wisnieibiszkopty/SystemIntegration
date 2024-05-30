import {GameRecord} from "../api/interfaces.ts";
import React from "react";


const GameRecordComponent: React.FC<{ record: GameRecord}> = ({record}) => {
    return (
        <div key={record.id}>
            <p>Data: {record.year} - {record.month}</p>
            <p>
                średnia graczy: {record.steamStats.steamAveragePlayers},
                średnia widzów: {record.twitchStats.twitchAvgViewers}
            </p>
        </div>
    )
}
export default GameRecordComponent;