import axios from "../axios.ts";
import {LoginFormData, RegisterFormData} from "../interfaces.ts";

export const registerUser = async (formData: RegisterFormData) => {
    return await  axios.post('/api/auth/register', formData);
}
export const loginUser = async (formData: LoginFormData) => {
    return await axios.post('/api/auth/authentication', formData);
}