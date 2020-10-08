

$(document).ready(function () {
    function loadBarChart() {

        $.getJSON('/api/voteCount', function (data) {
            console.log(data);
            var state = data["State"];
            initBarChart("barChart1", data["State"]["label"], data["State"]["data"]);
            initBarChart("barChart2", data["Party"]["label"], data["Party"]["data"]);
            if ( !(data["Candidate"]["label"] === undefined ||  data["Candidate"]["label"].length === 0)) {
                // array empty or does not exist
                initBarChart("barChart3", data["Candidate"]["label"], data["Candidate"]["data"]);
            }else {
                $("#barchart3id").hide();
            }

        });

    }

    loadBarChart();

    setInterval(function () {
        loadBarChart();
    }, 5000);

});

function initBarChart(elementId, labelData, intData) {
    var ctx = document.getElementById(elementId).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            barPercentage: 0.5,
            barThickness: 6,
            maxBarThickness: 8,
            minBarLength: 2,
            labels: labelData,// ["Direct", "Affiliate", "E-mail", "Other"]
            datasets: [{
                backgroundColor: [
                    "#ffffff",
                    "rgba(255, 255, 255, 0.70)",
                    "rgba(255, 255, 255, 0.50)",
                    "rgba(255, 255, 255, 0.20)",
                    "rgba(201, 255, 255, 0.30)",
                    "rgba(222, 255, 255, 0.40)",
                    "rgba(215, 255, 255, 0.80)",
                    "rgba(205, 255, 255, 0.20)",
                    "rgba(255, 255, 255, 0.20)",
                    "rgba(255, 255, 255, 0.20)",
                    "rgba(255, 255, 255, 0.20)",
                ],
                data: intData,// [1856, 262, 1802, 1105]
                borderWidth: [0, 0, 0, 0]
            }]
        },
        options: {
            maintainAspectRatio: false,
            legend: {
                position: "bottom",
                display: false,
                labels: {
                    fontColor: '#FFFFFF',
                    boxWidth: 15
                }
            },
            tooltips: {
                displayColors: true,
            },
            scales: {
                xAxes: [{
                    ticks: {
                        fontColor: "#FFFFFF", // this here
                    },
                }],
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontColor: "#FFFFFF"
                    }
                }]
            }
        }
    });
}