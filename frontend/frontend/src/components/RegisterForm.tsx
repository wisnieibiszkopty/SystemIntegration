import React, {useState, ChangeEvent, FormEvent} from 'react';
import InputField from './InputField';
import {registerUser} from "../api/services/User.ts";
import {validateRegister} from './validation.ts';
import {RegisterFormData, RegisterFormErrors} from "../api/interfaces.ts";
import {redirect} from "react-router-dom";
import {useAuthContext} from "../contexts/AuthContext.tsx";

const RegisterForm: React.FC = () => {
    const {updateToken} = useAuthContext();
    const [formData, setFormData] = useState<RegisterFormData>({
        email: '',
        username: '',
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
                redirect('/games');
            } else {
                setFormErrors({ passwordCheck: 'Hasła nie pasują do siebie' });
            }
        } catch (error: any) {
            // odbiór odpowiedzi z walidacji od serwera i wyswietlenie jej w alercie na stronie
            console.log(error.response ? error.response.data : error.message);
            const message = (error.response ? error.response.data.message : error.message);
            alert(`${message}`);
            handleReset();
        }
    };

    const handleReset = () => {
        setFormData({
            email: '',
            username: '',
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
        <div className="modal-form-body">
            <form
                onSubmit={handleSubmit}
                onReset={handleReset}
                className="modal-body">
                <div>
                    <div style={{ marginLeft: '25%', marginRight: '25%', marginBottom: '2%', marginTop: '2%' }}>
                        <div style={{ width: '100%' }}>
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
                                name="username"
                                value={formData.username}
                                onChange={handleChange}
                                placeholder="wprowadź nazwę użytkownika..."
                                error={formErrors.username}
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
                        <div style={{ width: '100%', display: 'flex', justifyContent: 'center'}}>
                            <button type="submit" className="form-btn">Załóż konto</button>
                        </div>
                    </div>
                </div>
            </form>
            <footer>
                <div className="footer menu-text">@WPWK</div>
            </footer>
        </div>
    );
};

export default RegisterForm;
