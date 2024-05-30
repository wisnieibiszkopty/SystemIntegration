import {Game} from "../api/interfaces.ts";
import React from "react";


const GameComponent: React.FC<{ game: Game}> = ({game}) => {
    return (
        <div key={game.id}>
            <p>GRA: {game.gameName}</p>
            <p>
                ILOŚĆ WPISÓW: {game.gameRecords.length},
                OCENA: {game.rating}
            </p>
        </div>
    )
}
export default GameComponent;