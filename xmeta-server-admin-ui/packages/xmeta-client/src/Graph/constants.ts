export type Ranker = "network-simplex" | "tight-tree" | "longest-path";
export type Direction = "down" | "right";
export type Edge = "hard" | "rounded";

// #import: filename
// #arrowSize: 1
// #bendSize: 0.3
// #direction: down | right
// #gutter: 5
// #edgeMargin: 0
// #gravity: 1
// #edges: hard | rounded
// #background: transparent
// #fill: #eee8d5; #fdf6e3
// #fillArrows: false
// #font: Calibri
// #fontSize: 12
// #leading: 1.25
// #lineWidth: 3
// #padding: 8
// #spacing: 40
// #stroke: #33322E
// #title: filename
// #zoom: 1
// #acyclicer: greedy
// #ranker: network-simplex | tight-tree | longest-path

export type Directive = {
    arrowSize: number;
    bendSize: number;
    direction: Direction;
    gutter: number;
    edgeMargin: number;
    gravity: number;
    edges: Edge;
    background: string;
    fill: string;
    fillArrows: boolean;
    font: string;
    fontSize: number;
    leading: number;
    lineWidth: number;
    padding: number;
    spacing: number;
    stroke: string;
    zoom: number;
    acyclicer: string;
    ranker: Ranker;
}

export const DEFAULT_DIRECTIVE :Directive = {
    arrowSize: 1,
    bendSize: 0.3,
    direction: "right",
    gutter: 5,
    edgeMargin: 0,
    gravity: 1,
    edges: "hard",
    background: "transparent",
    fill: "#e8eaf0;#f5f6fa",
    fillArrows: false,
    font: "Calibri",
    fontSize: 12,
    leading: 1.25,
    lineWidth: 1,
    padding: 8,
    spacing: 60,
    stroke: "#33322E",
    zoom: 0.8,
    acyclicer: "greedy",
    ranker: "longest-path",
}
