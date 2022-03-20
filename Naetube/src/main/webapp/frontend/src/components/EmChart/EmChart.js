import React from "react";


function EmChart() {

    return(
        <div id="emchart-div">
            <div class="chart-div">
                <canvas id="pieChartCanvas" width="300px" height="300px"></canvas>
                <div id='legend-div' class="legend-div"></div>

            </div>
        </div>
    );
}

export default EmChart;