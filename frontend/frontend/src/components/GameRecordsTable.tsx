import {GameRecord} from "../api/interfaces.ts";
import React from "react";

const GameRecordsTable: React.FC<{ records: GameRecord[]}> = ({records}) => {
    return (
        <table>
            <thead>
            <tr>
                <th></th>
                <th>STEAM</th>
                <th></th>
                <th></th>
                <th></th>
                <th>TWITCH</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th>Data</th>
                <th>Średnia graczy</th>
                <th>Przyrost graczy</th>
                <th>Szczyt grających</th>
                <th>RATIO jakies</th>
                <th>Średnia widzów</th>
                <th>Średnia kanałów</th>
                <th>RATIO</th>
                <th>Godziny transmisji</th>
                <th>Godziny obejrzane</th>
                <th>Szczyt kanałów</th>
                <th>Szczyt oglądających</th>
                <th>Ilość streamerów</th>
            </tr>
            </thead>
            <tbody>
            {records.map((record, index) => (
                <tr key={index}>
                    <td>{record.timestamp}</td>
                    <td>{record.steamStats.steamAveragePlayers}</td>
                    <td>{record.steamStats.steamGainPlayers.toFixed(0)}</td>
                    <td>{record.steamStats.steamPeakPlayers}</td>
                    <td>{parseFloat(record.steamStats.steamAvgPeakPerc).toFixed(2)}</td>
                    <td>{record.twitchStats.twitchAvgViewers}</td>
                    <td>{record.twitchStats.twitchAvgChannels}</td>
                    <td>{record.twitchStats.twitchAvgViewerRatio}</td>
                    <td>{record.twitchStats.twitchHoursStreamed}</td>
                    <td>{record.twitchStats.twitchHoursWatched}</td>
                    <td>{record.twitchStats.twitchPeakChannels}</td>
                    <td>{record.twitchStats.twitchPeakViewers}</td>
                    <td>{record.twitchStats.twitchStreamers}</td>
                </tr>
            ))}
            </tbody>
        </table>
    )
}
export default GameRecordsTable;