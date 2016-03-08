var cy, myLayout;
			
var layoutOptions = {
	name: 'dagre',
	fit: true, 					// whether to fit to viewport
	padding: 30, 				// fit padding
	avoidOverlap: true, 		// prevents node overlap, may overflow boundingBox if not enough space
	avoidOverlapPadding: 10 	// extra spacing around nodes when avoidOverlap: true
}

function redraw(){
	myLayout = cy.makeLayout(layoutOptions);
	myLayout.run();
}

function initCy(){
	cy = window.cy = cytoscape({
		container: document.getElementById('cy'),

		boxSelectionEnabled: false,
		autounselectify: true,
		
		zoomingEnabled: true,
		userZoomingEnabled: true,
		panningEnabled: true,
		userPanningEnabled: true,
		boxSelectionEnabled: false,
		
		minZoom: 1,
		maxZoom: 3,
		wheelSensitivity: 0.5,
		
		style: [
			{
				selector: 'node',
				style: {
					'content': 'data(id)',
					'text-opacity': 0.5,
					'text-valign': 'top',
					'text-halign': 'center',
					'background-color': '#000000'
				}
			},
			{
				selector: 'edge',
				style: {
					'width': 4,
					'line-color': '#CCCCCC',
					'target-arrow-color': '#CCCCCC'
				}
			},
			{
				selector: 'edge.undirected',
				style: {
					'target-arrow-shape': 'none'
				}
			},
			{
				selector: 'edge.directed',
				style: {
					'target-arrow-shape': 'triangle'
				}
			}
		],

		elements: {
			nodes: [],	
			edges: []
		}
	});
}