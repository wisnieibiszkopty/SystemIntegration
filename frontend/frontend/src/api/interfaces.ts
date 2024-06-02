export interface Game {
    gameName: string;
    id: number;
    gameRecords: GameRecord[];
    coverUrl: string;
    rating: number;
    ratingCount: number;
    totalRating: number;
    totalRatingCount: number;
    perspectives: PlayerPerspective[];
    genres: GameGenre[];
    modes: GameMode[];
}
export const defaultGame: Game = {
    id: 0,
    gameName: '',
    coverUrl: '',
    rating: 0,
    ratingCount: 0,
    totalRating: 0,
    totalRatingCount: 0,
    gameRecords: [],
    perspectives: [],
    genres: [],
    modes: []
}

export interface GameRecord {
    id: number;
    game: Game;
    year: string;
    month: string;
    time: Date;
    timestamp: string;
    steamStats: SteamStats;
    twitchStats: TwitchStats;
}

export interface SteamStats {
    id: number;
    record: GameRecord;
    steamAveragePlayers: number;
    steamGainPlayers: number;
    steamPeakPlayers: number;
    steamAvgPeakPerc: string;
}

export interface TwitchStats {
    id: number;
    record: GameRecord;
    twitchHoursWatched: number;
    twitchHoursStreamed: number;
    twitchPeakViewers: number;
    twitchPeakChannels: number;
    twitchStreamers: number;
    twitchAvgViewers: number;
    twitchAvgChannels: number;
    twitchAvgViewerRatio: number;
}

interface GameGenre {
    id: number;
    name: string;
    games: Game[];
}
interface GameMode {
    id: number;
    name: string;
    games: Game[];
}


interface PlayerPerspective {
    id: number;
    name: string;
    games: Game[];
}