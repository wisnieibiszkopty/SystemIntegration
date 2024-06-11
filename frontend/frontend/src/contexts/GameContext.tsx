import React, {createContext, useContext, useEffect, useState} from "react";
import {Game} from "../api/interfaces.ts";
import {getGames} from "../api/services/Game.ts";
import {useAuthContext} from "./AuthContext.tsx";

interface GameContextType {
    games: Game[];
    isLoading: boolean;
    filteredGames: Game[];
    currentPage: number;
    searchItem: string;
    selectedView: number;
    selectedType: number;
    selectedGenre: number;
    setIsLoading: (temp: boolean) => void;
    setFilteredGames: (games: Game[]) => void;
    setCurrentPage: (page: number) => void;
    setSearchItem: (name: string) => void;
    setSelectedView: (view: number) => void;
    setSelectedType: (typeId: number) => void;
    setSelectedGenre: (genreId: number) => void;
}

const GameContext = createContext<GameContextType>({
    games: [],
    isLoading: false,
    filteredGames: [],
    currentPage: 1,
    searchItem: '',
    selectedView: 0,
    selectedType: 0,
    selectedGenre: 0,
    setIsLoading: () => {},
    setFilteredGames: () => {},
    setCurrentPage: () => {},
    setSearchItem: () => {},
    setSelectedView: () => {},
    setSelectedType: () => {},
    setSelectedGenre: () => {},
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
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [searchItem, setSearchItem] = useState('');
    const [filteredGames, setFilteredGames] = useState<Game[]>([]);
    const [selectedView, setSelectedView] = useState<number>();
    const [selectedType, setSelectedType] = useState<number>();
    const [selectedGenre, setSelectedGenre] = useState<number>();

    useEffect(() => {
        const fetchGames = async () => {
            setIsLoading(true);
            try {
                console.log("FETCH GAMES")
                const gamesData = await getGames(0,100, token);
                setGames(gamesData.reverse());
                setFilteredGames(gamesData);
            } catch (error) {
                console.error("GameProvider.fetchGames() - Error fetching games: ", error);
            } finally {
                setIsLoading(false);
            }
        }

        if (isAuth) fetchGames().then();
    }, [isAuth, token]);

    return (
        <GameContext.Provider value={{
            games,
            isLoading,
            setIsLoading,
            filteredGames,
            setFilteredGames,
            selectedView,
            setSelectedView,
            selectedType,
            selectedGenre,
            setSelectedType,
            setSelectedGenre,
            currentPage,
            setCurrentPage,
            searchItem,
            setSearchItem,
        }}>
            {children}
        </GameContext.Provider>
    )
}