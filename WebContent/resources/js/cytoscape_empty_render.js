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
	cy.fit();
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
		
		minZoom: 0.5,
		maxZoom: 3,
		wheelSensitivity: 0.5,
		
		style: cytoscape.stylesheet().selector('node').css(
			{
				'content': 'data(id)',
				'text-opacity': 0.5,
				'text-valign': 'top',
				'text-halign': 'center',
				'background-color': '#000000'
		 	}
		).selector('edge') .css(
			{
				'width': 4,
				'line-color': '#CCCCCC',
				'target-arrow-color': '#CCCCCC',
				'label': 'data(label)'
			}
		).selector('edge.undirected') .css(
			{
				'target-arrow-shape': 'none'
			}
		).selector('edge.directed') .css(
			{
				'target-arrow-shape': 'triangle'
			}
		).selector('.highlighted').css(
			{
				'background-color' : '#61bffc',
				'line-color' : '#61bffc',
				'target-arrow-color' : '#61bffc',
				'transition-property' : 'background-color, line-color, target-arrow-color',
				'transition-duration' : '0.5s'
			}
		),
		
		elements: {
			nodes: [],	
			edges: []
		}
	});
}

function doAlgorithm(algName, nodeId, isDirected){
	
	var nodeSelector = '#' + nodeId;
	var alg;
	
	switch (algName) {
	case 'BFS':
		alg = cy.elements().bfs(nodeSelector, function(){}, isDirected); 
		break;
	case 'DFS':
		alg = cy.elements().dfs(nodeSelector, function(){}, isDirected); 
		break;
	case 'DIJ':
		break;
	default:
		break;
	}
	
	var i = 0;
	var highlightNextEle = function(){
	  if( i < alg.path.length ){
	    alg.path[i].addClass('highlighted');
	  
	    i++;
	    setTimeout(highlightNextEle, 1000);
	  }
	};

	// kick off first highlight
	highlightNextEle();
}
