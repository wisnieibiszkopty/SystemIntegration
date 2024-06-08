import axios from "axios";
const BASE_URL: string = "http://localhost:8080";

const api = axios.create({
    baseURL: BASE_URL,
    // withCredentials: true,
});
export default api;