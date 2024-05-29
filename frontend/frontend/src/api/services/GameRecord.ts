import axios from "../axios.ts";

export const getRecords = async (gameId: number) => {
    return await axios.get(`api/records/${gameId}`);
};