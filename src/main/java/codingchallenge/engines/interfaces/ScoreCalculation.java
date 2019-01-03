package codingchallenge.engines.interfaces;

import codingchallenge.domain.subdomain.Score;

import java.util.Map;

public interface ScoreCalculation {

    Map<String, Score> calculateScores(int questionNumber);

}
