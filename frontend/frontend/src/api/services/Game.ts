import api from "../axios.ts";
import {Game} from "../interfaces.ts";
import {AxiosResponse} from "axios";

export const getGames = async (page: number, size: number, token: string): Promise<any> => {
    try {
        console.log(token);
        const response: AxiosResponse<Game[]> = await api.get("/api/games", {
            headers: { Authorization: `Bearer ${token}` },
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

