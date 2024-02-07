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
import Map from "./Map";
import BuildingList from "./BuildingList";
import Guide from "./Guide";

interface AppState {
    path: Array<number[]> // The coordinates of the paths
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            path: [[]], // initialize an empty route
        };
    }

    // Creates a path out of the text that the user has entered
    findPath = async (newText: string) => {
        const lines: string[] = newText.split('\n');

        // Removing blank lines from input
        lines.sort();
        const blankLines: number = lines.lastIndexOf("") + 1;
        lines.splice(0, blankLines);

        // Get a path from the Spark server and set path to the object that is returned. If the user input is invalid or blank, the path
        // is set to an empty path.
        if (lines.length === 2) {
            try {
                let response = await fetch("http://localhost:4567/find-path?start=" + lines[0].toUpperCase() + "&destination=" + lines[1].toUpperCase());
                if (!response.ok) { // building DNE
                    alert("One or more of the buildings you entered does not exist on campus");
                    return;
                }
                let object = (await response.json()) as Array<number[]>;
                this.setState({
                    path: object
                })
            } catch (e) {
                alert("There was an error contacting the server.");
                console.log(e);
            }
        } else if (lines.length === 0) {
            this.setState({
                path: [[]]
            })
        } else {
            this.setState({
                path: [[]]
            })
            alert("Please enter exactly two buildings on one line each");
        }
    }

    render() {
        return (
            <div>
                <p>Welcome to Campus Paths!</p>
                <BuildingList onChange={this.findPath}/>
                <Map path={this.state.path}/>
                <Guide/>
            </div>

        );
    }

}

export default App;
