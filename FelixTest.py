#!/usr/bin/python

import sys
from Lib import *
from random import randint





g = Graph("undirected");#type \in buechi, directed, etc. or None

h = Graph("undirected");

# g1 = Graph("directed");



numV = 10;

vertices = [];

gregarity = [0];



g.track("g vertices",vertices);


for x in range(numV):
	vertices.append(g.addVertex());
	h.addVertex();
	g.setVertexLabel(x,str(x));
	
g.pauseUntilSpacePressed();

for x in vertices:
	for y in vertices:
		g.addEdge(x,y);
		# g.pauseUntilSpacePressed(2);
	# g.pauseUntilSpacePressed(1);

g.pauseUntilSpacePressed();

for x in vertices:
	for y in vertices:
		g.setEdgeContour((x,y),"dashed");
		
	g.pauseUntilSpacePressed(5);


g.pauseUntilSpacePressed();

for x in vertices:
	g.deleteVertex(x);






