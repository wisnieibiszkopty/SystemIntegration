import React, {useEffect, useState} from "react";
import GameCard from "../components/GameCard.tsx";
import {useGameContext} from "../contexts/GameContext.tsx";
import InputField from "../components/InputField.tsx";
import DataExportPanel from "../components/DataExportPanel.tsx";
import {getGamesInfo} from "../api/services/Game.ts";
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../contexts/AuthContext.tsx";
import LoadingSpinner from "../components/LoadingSpinner.tsx";

type gameInfo = {
    id: number;
    name: string;
}

const GamesPage = () => {
    const {
        games,
        isLoading,
        filteredGames, setFilteredGames,
        selectedView, setSelectedView,
        selectedType, setSelectedType,
        selectedGenre, setSelectedGenre,
        currentPage, setCurrentPage,
        searchItem, setSearchItem
    } = useGameContext();

    const itemsPerPage = 14;
    const totalPages = (filteredGames) ? Math.ceil(filteredGames.length / itemsPerPage) : 0;
    const navigate = useNavigate();
    const {resetToken, isAuth} = useAuthContext();

    const displayedGames = (filteredGames) ? filteredGames.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    ) : [];

    const gameTypes = [
        { id: 1, name: 'Single player' },
        { id: 2, name: 'Multiplayer' },
        { id: 3, name: 'Co-operative' },
        { id: 4, name: 'Split screen' },
        { id: 5, name: 'Massively Multiplayer Online (MMO)' },
        { id: 6, name: 'Battle Royale' }
    ];
    const gameGenres = [
        { id: 36, name: 'MOBA' },
        { id: 24, name: 'Tactical' },
        { id: 25, name: "Hack and slash/Beat 'em up" },
        { id: 26, name: 'Quiz/Trivia' },
        { id: 30, name: 'Pinball' },
        { id: 31, name: 'Adventure' },
        { id: 32, name: 'Indie' },
        { id: 33, name: 'Arcade' },
        { id: 34, name: 'Visual Novel' },
        { id: 35, name: 'Card & Board Game' }
    ];

    const gameViews = [
        { id: 1, name: "First person" },
        { id: 2, name: "Third person" },
        { id: 3, name: "Bird view / Isometric" },
        { id: 4, name: "Side view" },
        { id: 5, name: "Text" },
        { id: 6, name: "Auditory" },
        { id: 7, name: "Virtual Reality" }
    ];

    useEffect(() => {
        if(!isAuth){
            navigate('/');
            alert("Zaloguj się aby mieć dostęp do tej podstrony");
        }
    }, [isAuth]);

    // const fetchGamesInfo = async () => {
    //     try{
    //         const gamesInfo = await getGamesInfo();
    //         console.log(gamesInfo);
    //         setSelectedType(gamesInfo.modes);
    //         setSelectedGenre(gamesInfo.genres);
    //         setSelectedView(gamesInfo.perspectives);
    //     } catch (err){
    //         console.error("GameProvider.fetchGames() - Error fetching games: ", err);
    //     }
    // }
    //
    // useEffect(() => {
    //     fetchGamesInfo().then();
    // }, []);

    console.log(games);

    const filterGames = (name: string, type: number | undefined, genre: number | undefined, view: number | undefined) => {
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

        if (view) {
            temp = temp.filter(game =>
                game.perspectives.some(genreObj => genreObj.id === view)
            );
        }

        setFilteredGames(temp);
        setCurrentPage(1);
    };

    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = e.target.value;
        setSearchItem(searchTerm);
        filterGames(searchTerm, selectedType, selectedGenre, selectedView);
    }

    const handleTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const typeValue = Number(e.target.value);
        // @ts-ignore
        if (typeValue) console.log("ZMIANA TYPU: ", gameTypes.find((type: gameInfo) => type.id == typeValue).name);
        setSelectedType(typeValue);
        filterGames(searchItem, typeValue, selectedGenre, selectedView);
    };

    const handleGenreChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const genreValue = Number(e.target.value);
        // @ts-ignore
        if (genreValue) console.log("ZMIANA GATUNKU: ", gameGenres.find(genre => genre.id == genreValue).name);
        setSelectedGenre(genreValue);
        filterGames(searchItem, selectedType, genreValue, selectedView);
    };
    const handleViewChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const viewValue = Number(e.target.value);
        if (viewValue) console.log("ZMIANA PERSPEKTYWY: ", gameViews.find(genre => genre.id == viewValue).name);
        setSelectedView(viewValue);
        filterGames(searchItem, selectedType, selectedGenre, viewValue);
    };

    const resetFilter = () => {
        setSearchItem('');
        setSelectedView(0);
        setSelectedType(0);
        setSelectedGenre(0);
        setSelectedView(0);
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
                    <button onClick={() => {
                        resetToken();
                        navigate("/");
                    }}
                    className={'logout-button'}
                    >
                        WYLOGUJ
                    </button>
                </div>
                <div className={'filter-widget'}>
                    <select value={selectedView} onChange={handleViewChange} style={{height: '30px'}}>
                        <option value="0">PERSPEKTYWA</option>
                        {gameViews.map((view) => (
                            <option key={view.id} value={view.id}>
                                {view.name}
                            </option>
                        ))}
                    </select>
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

            {!isLoading ?
                <div className={'game-cards-container'}>
                    <div className={'pagination'}>
                        <div className="">
                            <button onClick={() => {
                                if (currentPage > 1) handlePageChange(currentPage - 1);
                            }}
                                    disabled={currentPage === 1}
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
                                    disabled={currentPage === totalPages}
                            >
                                →
                            </button>
                        </div>
                    </div>
                    {displayedGames && displayedGames.map((game) => (
                        <GameCard key={game.id} game={game}/>
                    ))}
                </div>
            :
                <div className={'game-cards-container'}>
                    <LoadingSpinner/>
                </div>
            }
            <footer>
                <div>@WPWK</div>
            </footer>
        </>
    )
}
export default GamesPage;