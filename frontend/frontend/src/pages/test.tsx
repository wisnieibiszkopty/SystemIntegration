import {Link} from "react-router-dom";
import DataChart from "../components/DataChart.tsx";

function test() {
    return (
        <>
            <h1>TEST</h1>
            <div>
                <DataChart data={[2, 5, 7, 34]}/>
                <Link to={"/games"}>GIERKI</Link><br/>
                <Link to={"/records"}>WPISY DLA ID=3</Link>
            </div>
        </>
    )
}

export default test;
