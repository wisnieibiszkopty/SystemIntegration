import React from "react";
import {Game} from "../api/interfaces.ts";

const GameInfo: React.FC<{game: Game}> = ({game}) => {
    console.log(game);
    // TODO
    // mozna dodac emotki do kazdego typu gry, bedzie ladniej ;D
    return (
        <div className={'game-info'}>
            <img src={game.coverUrl} alt={'game_icon'} className={'game-icon'}/>
            <div className={'game-details'}>
                <h1>{game.gameName}</h1>
                <div className={'game-tags-container'}>
                    {game.genres.map((genre) => (
                        <div className={'game-tag'}>

                            {genre.name}
                        </div>
                    ))}
                    {game.modes.map((mode) => (
                        <div className={'game-tag'}>
                            {mode.name}
                        </div>
                    ))}
                </div>
                <div className={'game-rating'}>
                    <div>Ocena społeczności: {game.rating.toFixed(2)}/100</div>
                    <div>Ilość ocen: {game.ratingCount}</div>
                    <div>Ocena ogólna: {game.totalRating.toFixed(2)}/100</div>
                    <div>Ilość ocen ogółem: {game.totalRatingCount}</div>
                </div>
            </div>
        </div>
    );
}
export default GameInfo;