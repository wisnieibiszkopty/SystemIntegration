import {Link} from "react-router-dom";
import React from "react";

const Test: React.FC = () => {
    return (
        <>
            <h1>TEST</h1>
            <div>
                <Link to={"/games"}>GIERKI</Link><br/>
            </div>
        </>
    );
}

export default Test;
