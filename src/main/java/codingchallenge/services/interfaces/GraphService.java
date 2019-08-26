package codingchallenge.services.interfaces;

import codingchallenge.domain.graphs.BarGraph;
import codingchallenge.domain.graphs.BubbleGraph;
import codingchallenge.domain.graphs.PosGraph;
import codingchallenge.exceptions.ContestantNotFoundException;

public interface GraphService {

    PosGraph individualPositionGraph(String id) throws ContestantNotFoundException;
    BubbleGraph individualBubbleGraph(String id) throws ContestantNotFoundException;
    PosGraph getAvgGraph(String id) throws ContestantNotFoundException;

    PosGraph teamPositionGraph(String id) throws ContestantNotFoundException;
    BarGraph gradGraph(String id);
    BubbleGraph teamBubbleGraph(String id) throws ContestantNotFoundException;

}
