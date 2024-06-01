import api from "../axios.ts";
import {Game, defaultGame} from "../interfaces.ts";
import {AxiosResponse} from "axios";

export const getGames = async (page: number, size: number): Promise<any> => {
    try {
        const response: AxiosResponse<Game[]> = await api.get("/api/games", {
            params: {
                page: page,
                size: size,
            }
        });
        return response.data;
    } catch (error) {
        console.error("getGames() - Error calling api: ", error);
        return [];
    }
};
export const getGame = async (gameId: number): Promise<Game> => {
    try {
        const response: AxiosResponse<Game> = await api.get(`/api/games/${gameId}`, {});
        if (response) {
            return response.data;
        } else {
            console.log("getGame() - Game not found, id:" + gameId);
            return defaultGame;
        }
    } catch (error) {
        console.error("getGame() - Error calling api : ", error);
        return defaultGame;
    }
};

