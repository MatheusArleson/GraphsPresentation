var redraw, g, renderer, layouter, graphCanvasDivName;

function initDracula(graphCanvasDiv) {
	graphCanvasDivName = graphCanvasDiv;
	
	var width = getCanvasWidth();
    var height = getCanvasHeight();
    
    g = new Graph();

    layouter = new Graph.Layout.Spring(g);
    renderer = new Graph.Renderer.Raphael(graphCanvasDiv, g, width, height);
}

function getCanvasHeight(graphCanvasDivId) {
	var height = $('#' + graphCanvasDivId).height();
	return height;
}

function getCanvasWidth(graphCanvasDivId) {
	var width = $('#' + graphCanvasDivId).width();
	return width;
};

function redraw() {
	var w = getCanvasWidth(graphCanvasDivName);
    var h = getCanvasHeight(graphCanvasDivName);
    
    renderer.setHeight(h);
    renderer.setWidth(w);
    
    layouter.layout();
    renderer.draw();
};