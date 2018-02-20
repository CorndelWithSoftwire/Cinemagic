import * as React from "react";
import Banner from "./banner";
import Booking from "./booking";

export default class App extends React.Component {
    render() {
        return (
            <div className="app">
                <div className="curtain left-curtain"/>
                <div className="main-content">
                    <Banner/>
                    <Booking/>
                </div>
                <div className="curtain right-curtain"/>
            </div>
        );
    }
}
