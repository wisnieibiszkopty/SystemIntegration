import {Game} from "../api/interfaces.ts";
import React from "react";

// TODO
// Gdzie sie podziala tablica gameRecords dla Game
const GameComponent: React.FC<{ game: Game}> = ({game}) => {
    return (
        <div>
            <p>GRA: {game.gameName} - OCENA: {game.rating}</p>
        </div>
    )
}
export default GameComponent;