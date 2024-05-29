import axios from "../axios.ts";
import {AxiosResponse} from "axios";

// TODO
// dokonczyc interfejs gry, czyli wszystkie obiekty wchodzace
// w relacje z Game też określić interfejsy.


// interface Game {
//     gameName: string;
//     id: number;
//     // gameRecords: [GameRecord];
//     coverUrl: string;
//     rating: number;
//     ratingCount: number;
//     totalRating: number;
//     totalRatingCount: number;
// //     perspectives: [PlayerPerspective];
// //     genres: [GameGenre];
// //     modes: [GameMode];
// }

export const getGames = async (page: number, size: number): Promise<any> => {
    try {
        const response: AxiosResponse = await axios.get("/api/games", {
            params: {
                page: page,
                size: size,
            }
        });
        return response.data;
    } catch (error) {
        console.error("getGames() - Error fetching data: ", error);
    }
};
export const getGame = async (gameName: string, page: number, size: number): Promise<any> => {
    try {
        const response: AxiosResponse = await axios.get(`/api/games/${gameName}`, {
            params: {
                page: page,
                size: size,
            }
        });
        return response.data;
    } catch (error) {
        console.error("getGame() - Error fetching data: ", error);
    }
};

