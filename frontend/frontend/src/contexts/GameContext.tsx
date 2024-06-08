import React, {createContext, useContext, useEffect, useState} from "react";
import {Game} from "../api/interfaces.ts";
import {getGames} from "../api/services/Game.ts";

interface GameContextType {
    games: Game[];
    filteredGames: Game[];
    setFilteredGames: (games: Game[]) => void;
}

const GameContext = createContext<GameContextType>(undefined);

export const useGameContext = () => {
    const context = useContext(GameContext);
    if (!context) {
        throw new Error("useGameContext must be used within a GameProvider");
    }
    return context;
}

export const GameProvider: React.FC<{children: React.ReactNode}> = ({children}) => {
    const [games, setGames] = useState<Game[]>([]);
    const [filteredGames, setFilteredGames] = useState<Game[]>([]);
    const fetchGames = async () => {
        try {
            const gamesData = await getGames(0,100);
            setGames(gamesData.content);
            setFilteredGames(gamesData.content);
        } catch (error) {
            console.error("GameProvider.fetchGames() - Error fetching games: ", error);
        }
    }

    useEffect(() => {
        fetchGames().then();
    }, []);

    return (
        <GameContext.Provider value={{games, filteredGames, setFilteredGames}}>
            {children}
        </GameContext.Provider>
    )
}