import React, {useState} from "react";
import GameCard from "../components/GameCard.tsx";
import {useGameContext} from "../contexts/GameContext.tsx";
import InputField from "../components/InputField.tsx";

const GamesPage = () => {
    const {games, filteredGames, setFilteredGames} = useGameContext();
    const [searchItem, setSearchItem] = useState('');
    const [selectedType, setSelectedType] = useState<number>();

    const gameTypes = [
        { id: 1, name: 'Single player' },
        { id: 2, name: 'Multiplayer' },
        { id: 3, name: 'Co-operative' },
        { id: 4, name: 'Split screen' },
        { id: 5, name: 'Massively Multiplayer Online (MMO)' },
        { id: 6, name: 'Battle Royale' }
    ];

    // TODO
    // Poprawić wybór gameType, dodac gameGenre
    // Do poprawy wyswietlanie gier - podczas usuwania wyszukiwanej nazwy nie pokazuje gier na biezaco

    const filterGames = (name: string, type: number) => {
        let temp = games;

        if (name) {
            temp = filteredGames.filter(game =>
                game.gameName.toLowerCase().includes(name.toLowerCase())
            );
        }

        if (type) {
            temp = filteredGames.filter(game =>
                game.genres.map(type1 => type1.id === type)
            );
        }

        setFilteredGames(temp);
    };


    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const searchTerm = e.target.value;
        setSearchItem(searchTerm);
        filterGames(searchTerm, selectedType);
    }
    const handleTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const typeValue = e.target.value;
        setSelectedType(Number(typeValue));
        filterGames(searchItem, Number(typeValue));
    };
    const resetFilter = () => {
        setSearchItem('');
        // setSelectedType('');
        setFilteredGames(games);
    }

    return (
        <>
            <header>
                <h2>LISTA GIER</h2>
                <div className={'filter-widget'}>
                    <select value={selectedType} onChange={handleTypeChange} disabled={false}>
                        <option value="">narazie nei dziala</option>
                        {gameTypes.map((type) => (
                            <option key={type.id} value={type.id}>
                                {type.name}
                            </option>
                        ))}
                    </select>
                    <div className={'input-filter'}>
                        <InputField
                            label={''}
                            name={''}
                            type={'text'}
                            value={searchItem}
                            onChange={handleFilterChange}
                            placeholder={'Wprowadź nazwę gry...'}
                        />
                        <button onClick={resetFilter} style={{fontSize: '55%'}}>
                            ❌
                        </button>
                    </div>
                </div>
                <div>
                    <h2>EXPORTS</h2>
                    <a href={"http://localhost:8080/api/exports/json"} download="data.json">Json</a>
                    <a href={"http://localhost:8080/api/exports/xml"} download="data.xml">Xml</a>
                    <a href={"http://localhost:8080/api/exports/csv"} download="data.csv">Csv</a>
                </div>
            </header>
            <div className={'game-cards-container'}>
                {filteredGames && filteredGames.map((game) => (
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