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
export interface RegisterFormData {
    email: string;
    fullname: string;
    password: string;
    passwordCheck: string;
}
export interface RegisterFormErrors {
    email?: string;
    fullname?: string;
    password?: string;
    passwordCheck?: string;
    [key: string]: string | undefined;
}
export interface LoginFormData {
    email: string;
    password: string;
}

export interface LoginFormErrors {
    email?: string;
    password?: string;
    [key: string]: string | undefined;
}