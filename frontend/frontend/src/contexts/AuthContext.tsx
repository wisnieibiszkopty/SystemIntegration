import React, {createContext, useContext, useState} from "react";

type AuthContextType = {
    token: string;
    isAuth: boolean;
    //user: User;
    updateToken: (accessToken: string) => void;
    resetToken: () => void;
};

const AuthContext = createContext<AuthContextType>({
    token: '',
    isAuth: false,
    //user: new User("","", "",[]),
    updateToken: () => {},
    resetToken: () => {}
});

export const AuthProvider: React.FC<{children: React.ReactNode}> = ({ children }) => {
    const [token, setToken] = useState<string>('');
    const [isAuth, setIsAuth] = useState<boolean>(false);

    const updateToken = (token: string) => {
        console.log("Updating token: " + token);
        setIsAuth(true);
        setToken(token);
        const parts = token.split('.');
        const decodedPayload = JSON.parse(atob(parts[1]));
        console.log(decodedPayload);
    };

    const resetToken = () => {
        setIsAuth(false);
        setToken("");
        localStorage.setItem('token', null);
    };

    return (
      <AuthContext.Provider value={{token, isAuth, updateToken, resetToken}}>
          {children}
      </AuthContext.Provider>
    );
}

export const useAuthContext = () => useContext(AuthContext);