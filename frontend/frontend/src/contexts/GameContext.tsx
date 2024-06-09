import React, {createContext, useContext, useEffect, useState} from "react";
import {Game} from "../api/interfaces.ts";
import {getGames} from "../api/services/Game.ts";
import {useAuthContext} from "./AuthContext.tsx";

interface GameContextType {
    games: Game[];
    filteredGames: Game[];
    setFilteredGames: (games: Game[]) => void;
    fetchGames: () => void;
}

const GameContext = createContext<GameContextType>({
    games: [],
    filteredGames: [],
    setFilteredGames: () => {},
    fetchGames: () => {},
});

export const useGameContext = () => {
    const context = useContext(GameContext);
    if (!context) {
        throw new Error("useGameContext must be used within a GameProvider");
    }
    return context;
}

export const GameProvider: React.FC<{children: React.ReactNode}> = ({children}) => {
    const {token, isAuth} = useAuthContext();
    const [games, setGames] = useState<Game[]>([]);
    const [filteredGames, setFilteredGames] = useState<Game[]>([]);
    const fetchGames = async () => {
        try {
            console.log("FETCH GAMES")
            const gamesData = await getGames(0,100, token);
            setGames(gamesData.content);
            setFilteredGames(gamesData.content);
        } catch (error) {
            console.error("GameProvider.fetchGames() - Error fetching games: ", error);
        }
    }

    useEffect(() => {
        console.log(isAuth);
        if (isAuth) fetchGames().then();
    }, [isAuth]);

    return (
        <GameContext.Provider value={{games, filteredGames, setFilteredGames, fetchGames}}>
            {children}
        </GameContext.Provider>
    )
}