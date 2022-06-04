import React from "react";

export default class Line extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    
  }

  render() {
    return (
        <button className="delay-400 block w-full rounded-lg p-4 text-left transition ease-in hover:bg-blue-50 focus:bg-blue-100 active:bg-blue-100" onClick={() => this.props.action(this.props.lineContent)}>
            <div className="flex">
                <div className="flex flex-col w-4/5">
                    <div>Line: {this.props.lineContent.lineNumber}</div>
                    <div>Direction: {this.props.lineContent.directionCode}</div>
                </div>
                <div className="w-1/5 text-center">
                <span className="bg-green-100 text-green-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded dark:bg-green-200 dark:text-green-900">{this.props.lineContent.count}</span>
                </div>
            </div>
            
        </button>
    );
  }
}
