import React, {forwardRef, useEffect, useState} from "react";
import {CategoryScale, LinearScale, LineController, LineElement, PointElement} from "chart.js";
import {GameRecord} from "../api/interfaces.ts";
import Chart from 'chart.js/auto';

interface PropsType {
    data: GameRecord[];
}


const LineChart = forwardRef(({data}: PropsType, ref: React.ForwardedRef<any>) => {
    // @ts-ignore
    const [chartInst, setChartInst] = useState<Chart<"line", any, unknown> | null>(null);

    useEffect(() => {
        console.log(data);

        Chart.register(LinearScale, LineElement, LineController, CategoryScale, PointElement);

        // typescript don't want to compile
        console.log(ref);

        const labels = data.map(item => `${item.year} ${item.month.trim()}`).reverse();
        console.log(labels);
        const steamStatsData = data.map(item => item.steamStats).reverse();
        const twitchStatsData = data.map(item => item.twitchStats).reverse();
        const steamData = [
            { data: steamStatsData.map(item => item.steamAveragePlayers), label: "Średnia graczy", },
            { data: steamStatsData.map(item => item.steamGainPlayers), label: "Przyrost graczy", },
            { data: steamStatsData.map(item => item.steamPeakPlayers), label: "Najwięcej graczy jednocześnie", },
            { data: steamStatsData.map(item => item.steamAvgPeakPerc), label: "Ratio", },
        ];
        const twitchData = [
            { data: twitchStatsData.map(item => item.twitchHoursWatched), label: "Godziny obejrzane" },
            { data: twitchStatsData.map(item => item.twitchHoursStreamed), label: "Godziny streamowane" },
            { data: twitchStatsData.map(item => item.twitchPeakViewers), label: "Najwięcej widzów jednocześnie" },
            { data: twitchStatsData.map(item => item.twitchPeakChannels), label: "Najwięcej streamów jednocześnie" },
            { data: twitchStatsData.map(item => item.twitchStreamers), label: "Ilość streamerów" },
            { data: twitchStatsData.map(item => item.twitchAvgViewers), label: "Średnia ilośc widzów" },
            { data: twitchStatsData.map(item => item.twitchAvgChannels), label: "Średnia ilość streamerów" },
            { data: twitchStatsData.map(item => item.twitchAvgViewerRatio), label: "RATIO" },
        ];
        const steamDataSets = steamData.map(({data,label}) => (
            {
                data: data,
                label: label,
                borderWidth: 1,
                fill: false,
                hidden: label !== "Najwięcej graczy jednocześnie",
            }
        ));
        const twitchDataSets = twitchData.map(({data, label}) => (
            {
                data: data,
                label: label,
                borderWidth: 1,
                fill: false,
                hidden: label !== "Najwięcej widzów jednocześnie",
            }
        ));


        const dataSets = [
            ...steamDataSets,
            ...twitchDataSets
        ]
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
                datasets: dataSets,
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
                        title:{
                            text: "Dostępne wykresy",
                            padding: 5,
                        },
                        display: true,
                        position: 'right',
                        labels: {
                        }
                    },
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
            <canvas id="chart" width={'1200px'} height={'600px'}></canvas>
        </div>
    );
});

export default LineChart;