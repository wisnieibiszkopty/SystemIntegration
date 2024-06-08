import api from "../axios.ts";
import {Game} from "../interfaces.ts";
import {AxiosResponse} from "axios";

export const getGames = async (page: number, size: number): Promise<any> => {
    try {
        const response: AxiosResponse<Game[]> = await api.get("/api/games", {
            params: {
                page: page,
                size: size,
            }
        });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error("getGames() - Error calling api: ", error);
        return [];
    }
};
export const exportGame = (format: string, gameId?: number): string => {
    const baseUrl = "http://localhost:8080/api/exports";
    return gameId !== undefined ? `${baseUrl}/${format}/${gameId}` : `${baseUrl}/${format}`;
};

