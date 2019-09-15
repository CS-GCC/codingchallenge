package codingchallenge.services.impl;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.graphs.*;
import codingchallenge.domain.subdomain.*;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.GraphService;
import codingchallenge.services.interfaces.LeaderboardService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class GraphServiceImpl implements GraphService {

    private final ContestantService contestantService;
    private final LeaderboardService leaderboardService;

    @Autowired
    public GraphServiceImpl(ContestantService contestantService,
                            LeaderboardService leaderboardService) {
        this.contestantService = contestantService;
        this.leaderboardService = leaderboardService;
    }

    @Override
    public PosGraph individualPositionGraph(String id) {
        return getPosGraph(leaderboardService.getPositionsForIndividual(id));
    }

    @Override
    public BubbleGraph individualBubbleGraph(String id) {
        IndividualPosition latest =
                leaderboardService.getLatestPositionForIndividual(id);
        if (latest == null) {
            return null;
        }
        List<Score> scores = latest.getScores();
        List<BubbleData> questionBubbles = Lists.newArrayList();
        for (Score score : scores) {
            List<BubbleData> breakdown = Lists.newArrayList(
                    new BubbleData("Correct",
                            score.getCorrect()),
                    new BubbleData("Incorrect",
                            score.getIncorrect()),
                    new BubbleData("Timed Out",
                            score.getTimedOut())
            );
            questionBubbles.add(new BubbleData("Question " + score.getQuestionNumber(), breakdown));
        }
        BubbleData data = new BubbleData("Test Results", questionBubbles);
        return new BubbleGraph(data);
    }

    @Override
    public PosGraph getAvgGraph(String id) throws ContestantNotFoundException {
        Contestant contestant = contestantService.getContestantById(id);
        return teamPositionGraph(contestant.getTeamId());
    }

    @Override
    public PosGraph teamPositionGraph(String id) {
        return getPosGraph(leaderboardService.getPositionsForTeam(id));
    }


    @Override
    public BarGraph gradGraph(String id) {
        List<Contestant> teamContestants =
                contestantService.getContestantsByTeam(id);
        Map<Integer, List<Contestant>> graduationYearGroups =
                teamContestants.stream().collect(groupingBy(Contestant::getGraduationYear));
        List<BarData> barList = Lists.newArrayList();
        for (Map.Entry<Integer, List<Contestant>> gradYear :
                graduationYearGroups.entrySet()) {
            List<Contestant> contestants = gradYear.getValue();
            List<IndividualPosition> positions =
                    leaderboardService.getTeamContestantsLatestPosition(contestants.stream().map(Contestant::getId).collect(Collectors.toList()));
            OptionalDouble q1 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            1)).average();
            OptionalDouble q2 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            2)).average();
            OptionalDouble q3 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            3)).average();
            OptionalDouble q4 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            4)).average();
            OptionalDouble q5 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            5)).average();
            OptionalDouble q6 =
                    positions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            6)).average();
            BarData barData = new BarData(gradYear.getKey(), q1, q2, q3, q4,
                    q5, q6);
            barList.add(barData);
        }
        return new BarGraph(barList);
    }

    @Override
    public BubbleGraph teamBubbleGraph(String id) {
        List<IndividualPosition> contestantPositions =
                leaderboardService.getLatestIndividualPositionsForTeam(id);
        List<BubbleData> questionBubbles = Lists.newArrayList();
        for (int i=1; i<=6; i++) {
            int finalI = i;
            List<BubbleData> breakdown =
                    contestantPositions
                            .stream()
                            .map(c -> new BubbleData(c.getName(),
                                    round(getScoreForQuestion(c, finalI))))
                            .collect(Collectors.toList());
            questionBubbles.add(new BubbleData("Question " + i, breakdown));
        }
        BubbleData data = new BubbleData("Test Results", questionBubbles);
        return new BubbleGraph(data);
    }

    private PosGraph getPosGraph(List<? extends Position> positions) {
        Map<String, List<Coordinates>> coordinateMap =
                positions
                        .stream()
                        .map(Coordinates::new)
                        .collect(groupingBy(Coordinates::getX));
        List<Coordinates> coordinates = Lists.newArrayList();
        for (Map.Entry<String, List<Coordinates>> entry : coordinateMap.entrySet()) {
            List<Coordinates> value = entry.getValue();
            coordinates.add(value.stream().min(Comparator.comparingInt(Coordinates::getY)).get());
        }
        coordinates =
                coordinates.stream().sorted(Comparator.comparing(Coordinates::getX)).collect(Collectors.toList());
        Optional<Coordinates> maxOptional =
                coordinates.stream().max(Comparator.comparingInt(Coordinates::getY));
        Optional<Coordinates> minOptional =
                coordinates.stream().min(Comparator.comparingInt(Coordinates::getY));
        int min = maxOptional.get().getY();
        int max = minOptional.get().getY();
        return new PosGraph(max, min, Collections.singletonList(new PosData(
                "Positions", coordinates)));
    }

    private double getScoreForQuestion(Position position,
                                       int questionNumber) {
        List<Score> scores = position.getScores();
        Optional<Score> score =
                scores.stream().filter(s -> s.getQuestionNumber() == questionNumber).findFirst();
        return score.map(Score::getTotal).orElse(0.0);
    }

    private static double round(double val) {
        val = val*100;
        val = Math.round(val);
        return val /100;
    }
}
