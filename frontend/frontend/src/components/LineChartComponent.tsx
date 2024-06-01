import React, {forwardRef, useEffect, useState} from "react";
import {CategoryScale, Chart, LinearScale, LineController, LineElement, PointElement} from "chart.js";
import {GameRecord} from "../api/interfaces.ts";

interface PropsType {
    data: GameRecord[];
}


const LineChartComponent = forwardRef(({data}: PropsType, ref: React.ForwardedRef<any>) => {
    // @ts-ignore
    const [chartInst, setChartInst] = useState<Chart<"line", any, unknown> | null>(null);

    useEffect(() => {
        console.log(data);

        Chart.register(LinearScale, LineElement, LineController, CategoryScale, PointElement);

        // typescript don't want to compile
        console.log(ref);

        const labels = data.map(item => `${item.year} ${item.month.trim()}`);
        const steamStatsData = data.map(item => item.steamStats.steamAveragePlayers);
        const twitchStatsData = data.map(item => item.twitchStats.twitchAvgViewers);

        labels.reverse();
        steamStatsData.reverse();
        twitchStatsData.reverse();

        // @ts-ignore
        const ctx = document.getElementById("chart").getContext('2d');
        const existingChart = Chart.getChart(ctx);
        if (existingChart) {
            existingChart.destroy();
        }

        const chartInst = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Steam Average Players',
                        data: steamStatsData,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: false,
                    },
                    {
                        label: 'Twitch Hours Watched',
                        data: twitchStatsData,
                        borderColor: 'rgba(153, 102, 255, 1)',
                        borderWidth: 1,
                        fill: false,
                    }
                ]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    }
                }
            }
        });

        setChartInst(chartInst);

        if (ref) {
            if (typeof ref === 'function') {
                ref(chartInst);
            } else {
                ref.current = chartInst;
            }
        }

    }, [data]);

    return (
        <div>
            <canvas id="chart" width="800" height="400"></canvas>
        </div>
    );
});

export default LineChartComponent;