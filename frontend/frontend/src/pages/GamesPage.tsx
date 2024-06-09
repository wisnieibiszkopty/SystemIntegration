import React, {useEffect, useState} from "react";
import GameCard from "../components/GameCard.tsx";
import {useGameContext} from "../contexts/GameContext.tsx";
import InputField from "../components/InputField.tsx";
import DataExportPanel from "../components/DataExportPanel.tsx";
import {getGamesInfo} from "../api/services/Game.ts";

type gameInfo = {
    id: number;
    name: string;
}

const GamesPage = () => {
    const {games, filteredGames, setFilteredGames} = useGameContext();
    const [searchItem, setSearchItem] = useState('');
    const [selectedType, setSelectedType] = useState<number>();
    const [selectedGenre, setSelectedGenre] = useState<number>();
    const [selectedPerspective, setSelectedPerspective] = useState<number>();
    const [gameTypes, setGameTypes] = useState<[]>([]);
    const [gameGenres, setGameGenres] = useState<[]>([]);
    const [gamePerspectives, setGamePerspectives] = useState<[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 14;
    const totalPages = Math.ceil(filteredGames.length / itemsPerPage);

    const displayedGames = filteredGames.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    )

    // const gameTypes = [
    //     { id: 1, name: 'Single player' },
    //     { id: 2, name: 'Multiplayer' },
    //     { id: 3, name: 'Co-operative' },
    //     { id: 4, name: 'Split screen' },
    //     { id: 5, name: 'Massively Multiplayer Online (MMO)' },
    //     { id: 6, name: 'Battle Royale' }
    // ];
    // const gameGenres = [
    //     { id: 36, name: 'MOBA' },
    //     { id: 24, name: 'Tactical' },
    //     { id: 25, name: "Hack and slash/Beat 'em up" },
    //     { id: 26, name: 'Quiz/Trivia' },
    //     { id: 30, name: 'Pinball' },
    //     { id: 31, name: 'Adventure' },
    //     { id: 32, name: 'Indie' },
    //     { id: 33, name: 'Arcade' },
    //     { id: 34, name: 'Visual Novel' },
    //     { id: 35, name: 'Card & Board Game' }
    // ];

    // const gameTypes = [];
    // const gameGenres = [];
    // const gamePerspectives = [];

    const fetchGamesInfo = async () => {
        try{
            const gamesInfo = await getGamesInfo();
            console.log(gamesInfo);
            setGameTypes(gamesInfo.modes);
            setGameGenres(gamesInfo.genres);
            setGamePerspectives(gamesInfo.perspectives);
        } catch (err){
            console.error("GameProvider.fetchGames() - Error fetching games: ", err);
        }
    }

    useEffect(() => {
        fetchGamesInfo().then();
    }, []);

    console.log(games);

    const filterGames = (name: string, type: number | undefined, genre: number | undefined, perspective: number | undefined) => {
        let temp = games;

        if (name) {
            temp = temp.filter(game =>
                game.gameName.toLowerCase().includes(name.toLowerCase())
            );
        }

        if (type) {
            temp = temp.filter(game =>
                game.modes.some(genre => genre.id === type)
            );
        }

        if (genre) {
            temp = temp.filter(game =>
                game.genres.some(genreObj => genreObj.id === genre)
            );
        }

        if(perspective) {
            temp = temp.filter(game =>
                game.perspectives.some(perspObj => perspObj.id === perspective)
            );
        }

        setFilteredGames(temp);
        setCurrentPage(1);
    };


    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = e.target.value;
        setSearchItem(searchTerm);
        filterGames(searchTerm, selectedType, selectedGenre, selectedPerspective);
    }

    const handleTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const typeValue = Number(e.target.value);
        // @ts-ignore
        if (typeValue) console.log("ZMIANA TYPU: ", gameTypes.find((type: gameInfo) => type.id == typeValue).name);
        setSelectedType(typeValue);
        filterGames(searchItem, typeValue, selectedGenre, selectedPerspective);
    };

    const handleGenreChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const genreValue = Number(e.target.value);
        // @ts-ignore
        if (genreValue) console.log("ZMIANA GATUNKU: ", gameGenres.find(genre => genre.id == genreValue).name);
        setSelectedGenre(genreValue);
        filterGames(searchItem, selectedType, genreValue, selectedPerspective);
    };

    const handlePerspectiveChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        console.log(e);
        const perspectiveValue = Number(e.target.value);
        setSelectedPerspective(perspectiveValue);
        filterGames(searchItem, selectedType, selectedGenre, perspectiveValue);
    }

    const resetFilter = () => {
        setSearchItem('');
        setSelectedType(0);
        setSelectedGenre(0);
        setSelectedPerspective(0);
        setFilteredGames(games);
        setCurrentPage(1);
    }

    const handlePageChange = (newPage: number) => {
        setCurrentPage(newPage);
    }

    return (
        <>
            <header>
                <div className={'header-title'}>
                    <h1>LISTA GIER</h1>
                    <DataExportPanel/>
                </div>
                <div className={'filter-widget'}>
                    <div className={'pagination'}>
                        <div className="">
                            <button onClick={() => {
                                if (currentPage > 1) handlePageChange(currentPage - 1);
                            }}
                                    disabled={currentPage===1}
                            >
                                ←
                            </button>
                            {Array.from({length: totalPages}, (_, i) => i + 1).map(page => (
                                <button
                                    key={page}
                                    onClick={() => handlePageChange(page)}
                                    disabled={page === currentPage}
                                    className={page === currentPage ? 'active' : ''}
                                >
                                    {page}
                                </button>
                            ))}
                            <button onClick={() => {
                                if (currentPage < totalPages) handlePageChange(currentPage + 1);
                            }}
                                disabled={currentPage===totalPages}
                            >
                                →
                            </button>
                        </div>
                    </div>
                    <select value={selectedType} onChange={handleTypeChange} style={{height: '30px'}}>
                        <option value="0">RODZAJ</option>
                        {gameTypes.map((type: gameInfo) => (
                            <option key={type.id} value={type.id}>
                                {type.name}
                            </option>
                        ))}
                    </select>
                    <select value={selectedGenre} onChange={handleGenreChange} style={{height: '30px'}}>
                        <option value="0">GATUNEK</option>
                        {gameGenres.map((genre: gameInfo) => (
                            <option key={genre.id} value={genre.id}>
                                {genre.name}
                            </option>
                        ))}
                    </select>
                    <select value={selectedPerspective} onChange={handlePerspectiveChange} style={{height: '30px'}}>
                        <option value="0">PERSPEKTYWA</option>
                        {gamePerspectives.map((perspective: gameInfo) => (
                            <option key={perspective.id} value={perspective.id}>
                                {perspective.name}
                            </option>
                        ))}
                    </select>
                    <InputField
                        label={''}
                        name={''}
                        type={'text'}
                        value={searchItem}
                        onChange={handleFilterChange}
                        placeholder={'Wprowadź nazwę gry...'}
                    />
                    <button onClick={resetFilter} style={{height: '30px', fontSize: '70%'}}>WYCZYŚĆ</button>
                </div>

            </header>

            <div className={'game-cards-container'}>
                {displayedGames && displayedGames.map((game) => (
                    <GameCard key={game.id} game={game}/>
                ))}
            </div>

            <footer>
                <div className="footer menu-text">@WPWK</div>
            </footer>
        </>
    )
}
export default GamesPage;