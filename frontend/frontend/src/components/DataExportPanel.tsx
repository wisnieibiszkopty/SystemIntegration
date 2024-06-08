import React from "react";
import {Game} from "../api/interfaces.ts";
import {exportGame} from "../api/services/Game.ts";

const DataExportPanel: React.FC<{game?: Game}> = ({game}) => {
    return (
        <div className={'data-export-panel'}>
            {game !== undefined ?
                <div className={'data-export-container'}>
                    <div className={'data-export-panel-links'}>
                        <a href={exportGame('json', game.id)} download={"data_" + game.gameName + ".json"}>.json⬇️</a>
                        <a href={exportGame('xml', game.id)} download={"data_" + game.gameName + ".xml"}>.xml⬇️</a>
                        <a href={exportGame('csv', game.id)} download={"data_" + game.gameName + ".csv"}>.csv⬇️</a>
                    </div>
                </div>
                :
                <div className={'data-export-container'}>
                    <div className={'data-export-panel-links'}>
                        <a href={exportGame('json')} download="data.json">.json⬇️</a>
                        <a href={exportGame('xml')} download="data.xml">.xml⬇️</a>
                        <a href={exportGame('csv')} download="data.csv">.csv⬇️</a>
                    </div>
                </div>
            }
        </div>
    );
}
export default DataExportPanel;