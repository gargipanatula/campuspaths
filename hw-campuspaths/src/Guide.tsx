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

interface BuildingListState {
    display: string // what is shown in the text box
}

class Guide extends Component<{}, BuildingListState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            display: "abbreviation:\tname:\n" +
                "BAG\t\tBagley Hall (East Entrance)\n" +
                "BAG (NE)\tBagley Hall (Northeast Entrance)\n" +
                "BGR\t\tBy George\n" +
                "CHL\t\tChemistry Library (West Entrance)\n" +
                "CHL (NE)\tChemistry Library (Northeast Entrance)\n" +
                "CHL (SE)\tChemistry Library (Southeast Entrance)\n" +
                "CMU\t\tCommunications Building\n" +
                "CSE\t\tPaul G. Allen Center for Computer Science & Engineering\n" +
                "DEN\t\tDenny Hall\n" +
                "EEB\t\tElectrical Engineering Building (North Entrance)\n" +
                "EEB (S)\t\tElectrical Engineering Building (South Entrance)\n" +
                "FSH\t\tFishery Sciences Building\n" +
                "GWN\t\tGowen Hall\n" +
                "HUB\t\tStudent Union Building (Main Entrance)\n" +
                "HUB (Food, W)\tStudent Union Building (West Food Entrance)\n" +
                "HUB (Food, S)\tStudent Union Building (South Food Entrance)\n" +
                "IMA\t\tIntramural Activities Building\n" +
                "KNE\t\tKane Hall (North Entrance)\n" +
                "KNE (E)\t\tKane Hall (East Entrance)\n" +
                "KNE (SE)\tKane Hall (Southeast Entrance)\n" +
                "KNE (S)\t\tKane Hall (South Entrance)\n" +
                "KNE (SW)\tKane Hall (Southwest Entrance)\n" +
                "LOW\t\tLoew Hall\n" +
                "MCC\t\tMcCarty Hall (Main Entrance)\n" +
                "MCC (S)\t\tMcCarty Hall (South Entrance)\n" +
                "MCM\t\tMcMahon Hall (Northwest Entrance)\n" +
                "MCM (SW)\tMcMahon Hall (Southwest Entrance)\n" +
                "MGH\t\tMary Gates Hall (North Entrance)\n" +
                "MGH (E)\t\tMary Gates Hall (East Entrance)\n" +
                "MGH (S)\t\tMary Gates Hall (South Entrance)\n" +
                "MGH (SW)\tMary Gates Hall (Southwest Entrance)\n" +
                "MLR\t\tMiller Hall\n" +
                "MNY\t\tMeany Hall (Northeast Entrance)\n" +
                "MNY (NW)\tMeany Hall (Northwest Entrance)\n" +
                "MOR\t\tMoore Hall\n" +
                "MUS\t\tMusic Building (Northwest Entrance)\n" +
                "MUS (E)\t\tMusic Building (East Entrance)\n" +
                "MUS (SW)\tMusic Building (Southwest Entrance\n" +
                "MUS (S)\t\tMusic Building (South Entrance)\n" +
                "OUG\t\tOdegaard Undergraduate Library\n" +
                "PAA\t\tPhysics/Astronomy Building A\n" +
                "PAB\t\tPhysics/Astronomy Building\n" +
                "PAR\t\tParrington Hall\n" +
                "RAI\t\tRaitt Hall (West Entrance)\n" +
                "RAI (E)\t\tRaitt Hall (East Entrance)\n" +
                "ROB\t\tRoberts Hall\n" +
                "SAV\t\tSavery Hall\n" +
                "SUZ\t\tSuzzallo Library\n" +
                "T65\t\tThai 65\n" +
                "UBS\t\tUniversity Bookstore\n" +
                "UBS (Secret)\tUniversity Bookstore (Secret Entrance)\n"
        };
    }

    render() {
        return (
            <div id="building-list">
                <br/>
                <p>Buildings with abbreviations:</p>
                <textarea
                    rows={50}
                    cols={100}
                    value={this.state.display}
                /> <br/>
            </div>
        );
    }
}

export default Guide;