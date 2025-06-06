import React, {useState, ChangeEvent, FormEvent} from 'react';
import InputField from './InputField';
import {registerUser} from "../api/services/User.ts";
import {validateRegister} from './validation.ts';
import {RegisterFormData, RegisterFormErrors} from "../api/interfaces.ts";
import {redirect, useNavigate} from "react-router-dom";
import {useAuthContext} from "../contexts/AuthContext.tsx";

const RegisterForm: React.FC = () => {
    const {updateToken} = useAuthContext();
    const navigate = useNavigate();
    const [formData, setFormData] = useState<RegisterFormData>({
        email: '',
        fullname: '',
        password: '',
        passwordCheck: ''
    });

    const [formErrors, setFormErrors] = useState<RegisterFormErrors>({});

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // Logika obsługi przesłania formularza rejestracji

        // walidacja
        const validationResults = validateRegister(formData);
        if (Object.keys(validationResults).length > 0) {
            setFormErrors(validationResults);
            return;
        }

        try {
            // Sprawdz czy hasła się zgadzają
            if (formData.password === formData.passwordCheck) {
                // Logika po zarejestrowaniu - redirect
                console.log('user: ', formData);
                const response = await registerUser(formData);
                console.log('response: ', response.data);
                updateToken(response.data.token);
                navigate('/games');
            } else {
                setFormErrors({ passwordCheck: 'Hasła nie pasują do siebie' });
            }
        } catch (error: any) {
            // odbiór odpowiedzi z walidacji od serwera i wyswietlenie jej w alercie na stronie
            console.log("error data: ", error.response);
            console.log("error message: ", error.message);
            console.log(error.response ? error.response.data : error.message);
            // const message = (error.response ? error.response.data.message : error.message);
            const message = error.response.data;
            alert(`${message}`);
            //handleReset();
        }
    };

    const handleReset = () => {
        setFormData({
            email: '',
            fullname: '',
            password: '',
            passwordCheck: ''
        });
        setFormErrors({});
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
        // Resetowanie błędu dla aktualizowanego pola
        if (formErrors[name]) {
            setFormErrors({
                ...formErrors,
                [name]: undefined
            });
        }
    };

    return (
        <form onSubmit={handleSubmit} onReset={handleReset}>
            <div style={{ marginLeft: '25%', marginRight: '25%', marginBottom: '2%', marginTop: '2%' }}>
                <div className={'form-body'}>
                    <InputField
                        label="Adres e-mail"
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        placeholder="wprowadź adres email..."
                        error={formErrors.email}
                    />
                    <InputField
                        label="Nazwa użytkownika"
                        type="text"
                        name="fullname"
                        value={formData.fullname}
                        onChange={handleChange}
                        placeholder="wprowadź nazwę użytkownika..."
                        error={formErrors.fullname}
                    />
                    <InputField
                        label="Hasło"
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        placeholder="wprowadź hasło..."
                        error={formErrors.password}
                    />
                    <InputField
                        label="Potwierdź hasło"
                        type="password"
                        name="passwordCheck"
                        value={formData.passwordCheck}
                        onChange={handleChange}
                        placeholder="powtórz hasło..."
                        error={formErrors.passwordCheck}
                    />
                </div>
                <div className={'form-button'}>
                    <button type="submit">Załóż konto</button>
                </div>
            </div>
        </form>
    );
};

export default RegisterForm;
