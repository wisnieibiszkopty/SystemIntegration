import React, { useState, ChangeEvent } from 'react';
import InputField from './InputField';
// import { loginUser } from '../../api/services/User';
// import { validateLogin } from '../Form/validation';

interface FormData {
    email: string;
    password: string;
}

interface FormErrors {
    email?: string;
    password?: string;
    [key: string]: string | undefined;
}

const LoginForm: React.FC = () => {
    const [formData, setFormData] = useState<FormData>({
        email: '',
        password: ''
    });
    const [formErrors, setFormErrors] = useState<FormErrors>({});


    // const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    //     e.preventDefault();
    //     // Logika obsługi logowania
    //     // walidacja
    //     const validationResults = validateLogin(formData);
    //     if (Object.keys(validationResults).length > 0) {
    //         setFormErrors(validationResults);
    //         return;
    //     }
    //
    //     try {
    //         await loginUser(formData);
    //         //Logika po zalogowaniu - redirect
    //         navigate('/vGarage');
    //     } catch (error: any) {
    //         // odbiór odpowiedzi z walidacji od serwera i wyswietlenie jej w alercie na stronie
    //         console.log(error.response ? error.response.data : error.message);
    //         const message = (error.response ? error.response.data.message : error.message);
    //         // console.log(error.response.data.error.errors);
    //         alert(`${message}`);
    //     }
    // };

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
                // onSubmit={handleSubmit}
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
