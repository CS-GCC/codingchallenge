package codingchallenge.engines.interfaces;

import codingchallenge.domain.subdomain.Score;

import java.util.List;
import java.util.Map;

public interface ScoreCalculation {

    Map<String, Score> calculateScores(List<String> contestants,
                                       int questionNumber);

}
