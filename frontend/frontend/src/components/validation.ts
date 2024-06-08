import {RegisterFormData, RegisterFormErrors} from "../api/interfaces.ts";
import {LoginFormData, LoginFormErrors} from "../api/interfaces.ts";

export const validateRegister = (formData: RegisterFormData) => {
    const errors: RegisterFormErrors = {};
    if (!formData.username || formData.username.length < 3) errors.username = "Podaj nazwe użytkownika (co najmniej 3 znaki)!";
    if (!formData.email) {
        errors.email = "Podaj adres email!";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
        errors.email = "Niepoprawny format adresu email!";
    }
    if (!formData.password || formData.password.length < 6) {
        errors.password = "Podaj hasło, musi mieć co najmniej 6 znaków!";
    }

    if (!formData.passwordCheck) {
        errors.passwordCheck = "Potwierdź hasło!";
    } else if (formData.passwordCheck !== formData.password) {
        errors.passwordCheck = "Hasła nie pasują do siebie!";
    }
    return errors;
}
export const validateLogin = (formData: LoginFormData) => {
    const errors: LoginFormErrors = {};
    if (!formData.email) errors.email = "Podaj adres email!";
    if (!formData.password) errors.password = "Podaj hasło!";
    return errors;
}