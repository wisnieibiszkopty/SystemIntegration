import React, {useState, ChangeEvent, FormEvent} from 'react';
import InputField from './InputField';
import {LoginFormData, LoginFormErrors} from "../api/interfaces.ts";
import {validateLogin} from './validation.ts';
import {loginUser} from "../api/services/User.ts";
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../contexts/AuthContext.tsx";
import {useGameContext} from "../contexts/GameContext.tsx";


//TODO if403 to chuj
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
            // odbiór odpowiedzi z walidacji od serwera i wyswietlenie jej w alercie na stronie
            console.log(error.response ? error.response.data : error.message);
            const message = (error.response ? error.response.data.message : error.message);
            // console.log(error.response.data.error.errors);
            // alert(`${message}`);
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
                                label="Hasło"
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                placeholder="wprowadź hasło..."
                                error={formErrors.password}
                            />
                        </div>
                        <div style={{ width: '100%', display: 'flex', justifyContent: 'center'}}>
                            <button type="submit" className="form-btn">Zaloguj się</button>
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

export default LoginForm;
