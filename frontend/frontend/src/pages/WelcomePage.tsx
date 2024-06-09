import React, {useState} from "react";
import LoginForm from "../components/LoginForm.tsx";
import RegisterForm from "../components/RegisterForm.tsx";

const WelcomePage: React.FC = () => {
    const [showLogin, setShowLogin] = useState(true);

    const handleShowLogin = () => setShowLogin(true);
    const handleShowRegister = () => setShowLogin(false);

    return (
        <div className={'welcome-body'}>
            <h1>
                Zestawienie danych STEAM-TWITCH
            </h1>
            <div className={'welcome-buttons'}>
                {showLogin ?
                    <button
                        onClick={handleShowRegister}
                    >
                        REJESTRACJA &#x2192;
                    </button>
                    :
                    <button
                        onClick={handleShowLogin}
                    >
                    LOGOWANIE &#x2192;
                    </button>
                }
            </div>
            <div className="form-container">
                {showLogin ? <LoginForm/> : <RegisterForm/>}
            </div>
        </div>
    )
}
export default WelcomePage;