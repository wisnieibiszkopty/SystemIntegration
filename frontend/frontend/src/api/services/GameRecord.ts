import api from "../axios.ts";
import {AxiosResponse} from "axios";
import {GameRecord} from "../interfaces.ts";

export const getRecords = async (gameId: number, token: string): Promise<GameRecord[]> => {
    try {
        const response: AxiosResponse<GameRecord[]> = await api.get(`/api/records/${gameId}`, {
            headers: { Authorization: `Bearer ${token}` },
        });
        return response.data;
    } catch (error) {
        console.error("getRecords() - Error calling api");
        return [];
    }
};