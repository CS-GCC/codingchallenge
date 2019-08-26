package codingchallenge.controllers;

import codingchallenge.domain.QuickFacts;
import codingchallenge.domain.RegionFacts;
import codingchallenge.domain.graphs.BarGraph;
import codingchallenge.domain.graphs.BubbleGraph;
import codingchallenge.domain.graphs.PosGraph;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.FactsService;
import codingchallenge.services.interfaces.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/posuni/{id}", method = RequestMethod.GET)
    public PosGraph getUniPosGraph(@PathVariable String id) throws ContestantNotFoundException {
        return graphService.teamPositionGraph(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/posind/{id}", method = RequestMethod.GET)
    public PosGraph getIndPosGraph(@PathVariable String id) throws ContestantNotFoundException {
        return graphService.individualPositionGraph(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/bubbleuni/{id}", method = RequestMethod.GET)
    public BubbleGraph getBubbleUniGraph(@PathVariable String id) throws ContestantNotFoundException {
        return graphService.teamBubbleGraph(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/bubbleind/{id}", method = RequestMethod.GET)
    public BubbleGraph getBubbleIndGraph(@PathVariable String id) throws ContestantNotFoundException {
        return graphService.individualBubbleGraph(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/bar/{id}", method = RequestMethod.GET)
    public BarGraph getBarGraph(@PathVariable String id) {
        return graphService.gradGraph(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/graphs/posavg/{id}", method = RequestMethod.GET)
    public PosGraph getAvgGraph(@PathVariable String id) throws ContestantNotFoundException {
        return graphService.getAvgGraph(id);
    }



}
