import {Game} from "../api/interfaces.ts";
import React from "react";
import {Link} from "react-router-dom";

const GameComponent: React.FC<{ game: Game}> = ({game}) => {
    return (
        <div>
            <p>
                GRA: {game.gameName} - OCENA: {game.rating}
                <Link to={`/games/${game.id}/records`}> WPISY</Link>
            </p>
        </div>
    )
}
export default GameComponent;