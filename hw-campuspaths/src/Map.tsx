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

interface MapProps {
    path: Array<number[]>; // The path between two buildings
}

interface MapState {
    backgroundImage: HTMLImageElement | null; // The image displayed in the background
}

class Map extends Component<MapProps, MapState> {

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapProps) {
        super(props);
        this.state = {
            backgroundImage: null,
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    // Draws the canvas and path
    redraw = () => {

        // Sets up the canvas and loads the image
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");

        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;

            // Clears the existing drawing so we can start fresh
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draws each segment in the path
        let segments: Array<number[]> = this.props.path;
        for (let i = 0; i < segments.length; i++) {
            let segment: number[] = segments[i];
            ctx.beginPath();
            ctx.lineWidth = 5;
            ctx.strokeStyle = "red";
            ctx.moveTo(Number(segment[0]), Number(segment[1]));
            ctx.lineTo(Number(segment[2]), Number(segment[3]));
            ctx.stroke();
        }
    }

    render() {
        return (
            <div id="map">
                <canvas ref={this.canvas}/>
            </div>
        )
    }
}

export default Map;