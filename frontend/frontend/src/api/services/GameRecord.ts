import api from "../axios.ts";
import {AxiosResponse} from "axios";
import {GameRecord} from "../interfaces.ts";

export const getRecords = async (gameId: number): Promise<GameRecord[]> => {
    try {
        const response: AxiosResponse<GameRecord[]> = await api.get(`/api/records/${gameId}`);
        return response.data;
    } catch (error) {
        console.error("getRecords() - Error calling api");
        return [];
    }
};