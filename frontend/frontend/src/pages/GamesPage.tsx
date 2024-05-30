import {useEffect, useState} from "react";
import {Game} from "../api/interfaces.ts";
import {getGames} from "../api/services/Game.ts";
import GameComponent from "../components/GameComponent.tsx";

const GamesPage = () => {
    const [games, setGames] = useState<Game[]>([]);
    useEffect(() => {
        const fetchGames = async () => {
            try {
                const gamesData = await getGames(0,25);
                console.log(gamesData.content);
                setGames(gamesData.content);
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };
        fetchGames().then();
    }, []);

    return (
        <>
            <h2>GIERKI</h2>
            <div>
                {games && games.map((game) => (
                    <div  key={game.id}>
                        <GameComponent game={game}/>
                    </div>
                ))}
            </div>
        </>
    )
}
export default GamesPage;