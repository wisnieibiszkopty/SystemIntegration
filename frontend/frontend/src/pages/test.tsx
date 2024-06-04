import {Link} from "react-router-dom";

function test() {
    return (
        <>
            <h1>TEST</h1>
            <div>
                <Link to={"/games"}>GIERKI</Link><br/>
                <Link to={"/records"}>WPISY DLA ID=3</Link>
            </div>
        </>
    )
}

export default test;
