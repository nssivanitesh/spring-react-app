import React from "react";
import Content from "./Content";
import Line from "./Line";

export default class HomeCard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            selected: 0,
            items: [],
            content: [],
            mismatch: false,
            timeout: false,
        };
    }

    getJourData() {
        fetch("http://192.168.0.17:8080/getJourData")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    handleButtonClick = (data) => {
        if (data) {
            let url = "http://192.168.0.17:8080/getStopsForLine?lineNumber=" + data.lineNumber + "&directionCode=" + data.directionCode;
            fetch(url)
                .then(res => res.json())
                .then(
                    (result) => {
                        this.setState({
                            isLoaded: true,
                            content: result,
                            mismatch: result.length !== data.count
                        });
                    },
                    (error) => {
                        this.setState({
                            isLoaded: true,
                            error
                        });
                    }
                )
        }

    }

    componentDidMount() {
        this.getJourData();
    }

    render() {
        const { error, isLoaded, items, content, mismatch } = this.state;

        return (
            <div className="container relative mx-auto rounded-lg bg-white px-6 pt-10 pb-8 shadow-xl ring-1 ring-gray-900/5">
                <div className="text-3xl w-full bg-blue-200/20 hover:bg-blue-200/50 transition ease-in duration-500 rounded-md p-4 text-center uppercase">SBAB Demo - Spring boot - React App</div>
                <div className="mx-auto">
                    <div className="flex ">
                        {
                            (() => {
                                if (items.length > 0) {
                                    return (<><div className="w-1/3 divide-y divide-gray-300/50 ">
                                        {items.map((item, index) => (
                                            <Line lineContent={item} action={this.handleButtonClick} id={index} key={index} />
                                        ))}
                                    </div>
                                    <Content data={content} />
                                    </>)
                                }
                                else {
                                    return (<><div className="bg-green-200 rounded-md mx-auto p-8 m-8">
                                        <p>Fetching data...</p>
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
