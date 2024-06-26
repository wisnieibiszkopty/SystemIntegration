import React, {createContext, useContext, useEffect, useState} from "react";

type AuthContextType = {
    token: string;
    isAuth: boolean;
    updateToken: (accessToken: string) => void;
    resetToken: () => void;
};

const AuthContext = createContext<AuthContextType>({
    token: '',
    isAuth: false,
    updateToken: () => {},
    resetToken: () => {},
});

export const AuthProvider: React.FC<{children: React.ReactNode}> = ({children}) => {
    const [token, setToken] = useState<string>('');
    const [isAuth, setIsAuth] = useState<boolean>(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token !== null && token !== ''){
            console.log("useEffectAUTH",token)
            updateToken(token);
        }
    }, [token]);
  
    const updateToken = (accessToken: string) => {
        console.log("updating token");
        setIsAuth(true);
        setToken(accessToken);
        localStorage.setItem("token", accessToken);
        console.log(token)
    }
    
    const resetToken = () => {
        setIsAuth(false);
        setToken("");
        localStorage.removeItem('token');
    }
    
    return (
        <AuthContext.Provider value={{ token, updateToken, isAuth, resetToken }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuthContext = () => useContext(AuthContext);