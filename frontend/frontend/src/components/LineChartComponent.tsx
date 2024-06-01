import React, {forwardRef, useEffect, useState} from "react";
import {CategoryScale, Chart, LinearScale, LineController, LineElement, PointElement} from "chart.js";
import {Game, GameRecord} from "../api/interfaces.ts";

interface PropsType {
    data: GameRecord[];
    game: Game;
}


const LineChartComponent = forwardRef(({data, game}: PropsType, ref: React.ForwardedRef<any>) => {
    // @ts-ignore
    const [chartInst, setChartInst] = useState<Chart<"line", any, unknown> | null>(null);

    useEffect(() => {
        console.log(data);

        Chart.register(LinearScale, LineElement, LineController, CategoryScale, PointElement);

        // typescript don't want to compile
        console.log(ref);

        const labels = data.map(item => `${item.year} ${item.month.trim()}`);
        console.log(labels);
        const steamStatsData = data.map(item => item.steamStats.steamAveragePlayers);
        const twitchStatsData = data.map(item => item.twitchStats.twitchAvgViewers);

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
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                    // x: {
                    //     ticks: {
                    //         autoSkip: false, // Ensure all labels are shown
                    //         maxRotation: 60, // Rotate labels for better readability if necessary
                    //         minRotation: 45
                    //     }
                    // }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: `Statystyki dla ${game.gameName}`
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