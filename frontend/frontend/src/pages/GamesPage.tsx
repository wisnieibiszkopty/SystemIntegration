import {useEffect, useState} from "react";
import {Game} from "../api/interfaces.ts";
import {getGames} from "../api/services/Game.ts";
import GameComponent from "../components/GameComponent.tsx";


const GamesPage = () => {
    const [games, setGames] = useState<Game[]>();

    useEffect(() => {
        const fetchGames = async () => {
            try {
                const gamesData = await getGames(0,25);
                setGames(gamesData);
            } catch (error) {
                console.error("GamesPage.useEffect() - Error fetching games: ", error);
            }
        };

        fetchGames().then();
    }, []);
    console.log(games);

    return (
        <>
            <div>
                {games && games.map((game) => (
                    <GameComponent game={game}/>
                ))}
            </div>
        </>
    )
}
export default GamesPage;