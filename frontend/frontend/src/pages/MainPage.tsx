import {Link} from "react-router-dom";
import React from "react";

const MainPage: React.FC = () => {
    return (
        <>
            <h1>Integracja systemów</h1>
            <div>
                <div>
                    Projekt zestawiający dane o popularności gier na platformie Twitch,
                    z ilością ich graczy na platformie Steam. DALSZY OPIS PROJEKTU
                </div>
                <ul>
                    Wykorzystane technologie:
                    <li>Spring</li>
                    <li>Hibernate</li>
                    <li>Docker</li>
                    <li>React</li>
                    <li>TypeScript</li>
                </ul>
                <Link to={'https://github.com/wisnieibiszkopty/SystemIntegration'}>Repozytorium projektu na GitHub</Link><br/>
                <Link to={"/games"}>GIERKI</Link><br/>
            </div>
            <footer>
                <div className="footer menu-text">@WPWK</div>
            </footer>
        </>
    );
}

export default MainPage;
