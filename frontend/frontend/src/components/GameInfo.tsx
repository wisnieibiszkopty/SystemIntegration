import React from "react";
import {Game} from "../api/interfaces.ts";
import missingTexture from "../assets/missing_texture.jpg";
import DataExportPanel from "./DataExportPanel.tsx";

const GameInfo: React.FC<{game: Game}> = ({game}) => {
    return (
        <div className={'game-info'}>
            {game.coverUrl ?
                <img src={game.coverUrl} alt={'game_icon'} className={'game-icon'}/>
                :
                <img src={missingTexture} alt={'missing_texture'} className={'game-icon'}/>
            }
            <div className={'game-details'}>
                <h1>{game.gameName}</h1>
                {(game.genres.length !== 0 || game.modes.length !== 0) &&
                    <div className={'game-tags-container'} >
                        {game.genres.length !== 0 && game.genres.map((genre) => (
                            <div className={'game-tag'} key={genre.id}>
                                {genre.name}
                            </div>
                        ))}
                        {game.modes.length !== 0 && game.modes.map((mode) => (
                            <div className={'game-tag'}  key={mode.id}>
                                {mode.name}
                            </div>
                        ))}
                    </div>
                }
                {game.rating !== 0 &&
                    <div className={'game-rating'}>
                        <div>Ocena społeczności: {game.rating.toFixed(2)}/100</div>
                        <div>Ilość ocen: {game.ratingCount}</div>
                        <div>Ocena ogólna: {game.totalRating.toFixed(2)}/100</div>
                        <div>Ilość ocen ogółem: {game.totalRatingCount}</div>
                    </div>
                }
                <DataExportPanel game={game}/>
            </div>

        </div>
    );
}
export default GameInfo;