import React, {useState, ChangeEvent, FormEvent} from 'react';
import InputField from './InputField';
import {LoginFormData, LoginFormErrors} from "../api/interfaces.ts";
import {validateLogin} from './validation.ts';
import {loginUser} from "../api/services/User.ts";
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../contexts/AuthContext.tsx";

const LoginForm: React.FC = () => {
    const {updateToken} = useAuthContext();
    const navigate = useNavigate();
    const [formData, setFormData] = useState<LoginFormData>({
        email: '',
        password: ''
    });
    const [formErrors, setFormErrors] = useState<LoginFormErrors>({});

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // Logika obsługi logowania
        // walidacja
        const validationResults = validateLogin(formData);
        if (Object.keys(validationResults).length > 0) {
            setFormErrors(validationResults);
            return;
        }

        try {
            const response =  await loginUser(formData);
            console.log(response.data)
            updateToken(response.data.token);
            //Logika po zalogowaniu - redirect
            navigate('/games');
        } catch (error: any) {
            console.error(error);
            if (error.response.status === 403){
                alert('Niepoprawne dane logowania!');
                // Why is it clearing inputs?
                //handleReset();
            }
        }
    };

    const handleReset = () => {
        setFormData({
            email: '',
            password: ''
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
        <form
            onSubmit={handleSubmit}
            onReset={handleReset}
        >
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
                        label="Hasło"
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        placeholder="wprowadź hasło..."
                        error={formErrors.password}
                    />
                </div>
                <div className={'form-button'}>
                    <button type="submit">Zaloguj się</button>
                </div>
            </div>
        </form>
    );
};

export default LoginForm;
