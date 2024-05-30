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
export const getGame = async (gameName: string, page: number, size: number): Promise<Game> => {
    try {
        const response: AxiosResponse<Game> = await api.get(`/api/games/${gameName}`, {
            params: {
                page: page,
                size: size,
            }
        });
        return response.data;
    } catch (error) {
        console.error("getGame() - Error calling api : ", error);
        return defaultGame;
    }
};

