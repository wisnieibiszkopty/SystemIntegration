import {Game} from "../api/interfaces.ts";
import React from "react";
import {Link} from "react-router-dom";
import missingTexture from "../assets/missing_texture.jpg";

const GameCard: React.FC<{ game: Game}> = ({game}) => {
    return (
        <div className={''}>
            <Link to={`/games/${game.id}/records`} className={''} state={{game}}>
                {game.coverUrl ?
                    <img src={game.coverUrl} alt={`${game.gameName}`} className={'game-card'}/>
                    :
                    <img src={missingTexture} alt={'missing_texture'} className={'game-card'}/>
                }
            </Link>
            <div style={{width: '150px'}}>{game.gameName}</div>
        </div>
    )
}
export default GameCard;