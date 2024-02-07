/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import "./Map.css";

interface BuildingListProps {
    onChange(buildingList: string): void;  // called when a new building list is ready
}

interface BuildingListState {
    display: string // what is shown in the text box
}

class BuildingList extends Component<BuildingListProps, BuildingListState> {

    constructor(props: BuildingListProps) {
        super(props);
        this.state = {
            display: ""  // text box is initially blank
        };
    }

    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        this.setState({
            display: event.target.value, // updates text box to reflect input
        })
    }

    /**
     * Clears the text box
     */
    clearTextBox = () => {
        this.setState({
            display: "", // makes text box blank
        })
        this.props.onChange("");
    }

    render() {
        return (
            <div id="building-list">
                <br/>
                <p> Enter the abbreviations of exactly two buildings, with one on each line.
                    Your start building should be on the first line, and your destination building on the second.
                    If you are not sure of the abbreviation for a building, please refer to the guide at the
                    bottom of the page.
                </p>
                <p>
                    Click Draw to get the path. Click Clear to clear the box and map.
                </p>
                <p>Buildings:</p>
                <textarea
                    rows={2}
                    cols={30}
                    onChange={this.onInputChange}
                    value={this.state.display}
                /> <br/>
                <button onClick={() => {this.props.onChange(this.state.display)}}>Draw</button>
                <button onClick={this.clearTextBox}>Clear</button>
            </div>
        );
    }
}

export default BuildingList;