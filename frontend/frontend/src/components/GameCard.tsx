import {Game} from "../api/interfaces.ts";
import React from "react";
import {Link} from "react-router-dom";

const GameCard: React.FC<{ game: Game}> = ({game}) => {
    return (
        // TODO
        // obrazek nie działa, albo nie ładuja sie bo za dlugo to trwa aby pobrac z bazy
        <Link to={`/games/${game.id}/records`} className={'game-card'}>
            <img src={game.coverUrl} alt={'game_icon'} className={''}/>
            {game.gameName}
        </Link>
    )
}
export default GameCard;