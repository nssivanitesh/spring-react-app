import React from "react";
import * as am4core from "@amcharts/amcharts4/core";
import * as am4plugins_wordCloud from "@amcharts/amcharts4/plugins/wordCloud";

export default class Content extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    init() {
        let chart = am4core.create("chartdiv", am4plugins_wordCloud.WordCloud);

        let series = chart.series.push(new am4plugins_wordCloud.WordCloudSeries());
        series.maxCount = 100;
        series.minWordLength = 1;
        series.dataFields.word = "stopPointName";
        series.dataFields.value = "stopPointNumber";

        if (this.props.data && this.props.data.length > 0) {
            series.data = this.props.data;
        } else {
            series.data = [{
                "stopPointName" : "Data missing",
                "stopPointNumber": 100,
            }];
        }

        this.chart = chart;
    }

    componentDidMount() {
        this.init();
    }

    disposeChart() {
        if (this.chart) {
            this.chart.dispose();
        }
    }

    componentDidUpdate() {
        if (this.props.data && this.props.data.length > 0) {
            console.log(this.props.data.length);
            this.init();
        }
    }

    componentWillUnmount() {
        this.disposeChart();
    }

    render() {

        return (
            <div className="flex w-full">
                <div className="w-full p-8">
                    <div className="h-full w-full rounded-lg bg-blue-50 p-4">
                        {
                            (() => {
                                if (this.props.userSelected === false) {
                                    return (<><div className="bg-orange-200 p-4 rounded-md w-full">
                                        <p>Please select a line!</p>
                                    </div></>)
                                }
                                else if (this.props.data && this.props.data.length > 0) {
                                    return (<><div id="chartdiv" className="h-full w-full" key="chartDiv"></div></>)
                                }

                                else {
                                    return (<><div className="bg-red-200 p-4 rounded-md w-full">
                                        <p>Data unavailable for this line.</p>
                                        <p>If this is unexpected, please contact customer support!</p>
                                    </div></>)
                                }
                            })()
                        }
                    </div>
                </div>
            </div>
        );
    }
}
