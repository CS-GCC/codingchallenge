package codingchallenge.services.impl;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.Team;
import codingchallenge.domain.graphs.*;
import codingchallenge.domain.subdomain.*;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.GraphService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.TeamService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class GraphServiceImpl implements GraphService {

    private final ContestantService contestantService;
    private final TeamService teamService;
    private final LeaderboardService leaderboardService;

    @Autowired
    public GraphServiceImpl(ContestantService contestantService,
                            TeamService teamService,
                            LeaderboardService leaderboardService) {
        this.contestantService = contestantService;
        this.teamService = teamService;
        this.leaderboardService = leaderboardService;
    }

    @Override
    public PosGraph individualPositionGraph(String id) throws ContestantNotFoundException {
        Contestant contestant = contestantService.getContestantById(id);
        return getPosGraph(contestant.getPositions());
    }

    @Override
    public BubbleGraph individualBubbleGraph(String id) throws ContestantNotFoundException {
        Contestant contestant = contestantService.getContestantById(id);
        TimeStampPosition latest = getLatestPosition(contestant);
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
    public PosGraph teamPositionGraph(String id) throws ContestantNotFoundException {
        Team team = teamService.getTeamById(id);
        return getPosGraph(team.getPositions());
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
            List<TimeStampPosition> timeStampPositions =
                    contestants.stream().map(this::getLatestPosition).collect(Collectors.toList());
            double q1 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            1)).sum();
            double q2 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            2)).sum();
            double q3 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            3)).sum();
            double q4 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            4)).sum();
            double q5 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            5)).sum();
            double q6 =
                    timeStampPositions.stream().mapToDouble(t -> getScoreForQuestion(t,
                            6)).sum();
            BarData barData = new BarData(gradYear.getKey(), q1, q2, q3, q4,
                    q5, q6);
            barList.add(barData);
        }
        return new BarGraph(barList);
    }

    @Override
    public BubbleGraph teamBubbleGraph(String id) {
        TeamPosition position = leaderboardService.getLatestPositionForTeam(id);
        if (position == null) {
            return null;
        }
        List<IndividualPosition> contestantPositions =
                position.getContestants().stream().map(leaderboardService::getLatestPositionForIndividual).collect(Collectors.toList());
        List<BubbleData> questionBubbles = Lists.newArrayList();
        for (int i=1; i<=6; i++) {
            int finalI = i;
            List<BubbleData> breakdown =
                    contestantPositions
                            .stream()
                            .map(c -> new BubbleData(c.getName(), getScoreForQuestion(c, finalI)))
                            .collect(Collectors.toList());
            questionBubbles.add(new BubbleData("Question " + i+1, breakdown));
        }
        BubbleData data = new BubbleData("Test Results", questionBubbles);
        return new BubbleGraph(data);
    }

    private PosGraph getPosGraph(List<TimeStampPosition> positions) {
        List<Coordinates> coordinates =
                positions
                        .stream()
                        .map(Coordinates::new)
                        .collect(Collectors.toList());
        Optional<Coordinates> maxOptional =
                coordinates.stream().max(Comparator.comparingInt(Coordinates::getY));
        Optional<Coordinates> minOptional =
                coordinates.stream().min(Comparator.comparingInt(Coordinates::getY));
        int min = minOptional.get().getY();
        int max = maxOptional.get().getY();
        return new PosGraph(max, min, Collections.singletonList(new PosData(
                "Positions", coordinates)));
    }

    private TimeStampPosition getLatestPosition(Contestant contestant) {
        List<TimeStampPosition> positions = contestant.getPositions();
        if (positions.isEmpty()) {
            return null;
        }
        return
                positions.get(positions.size() - 1);
    }

    private double getScoreForQuestion(Position position,
                                       int questionNumber) {
        List<Score> scores = position.getScores();
        Optional<Score> score =
                scores.stream().filter(s -> s.getQuestionNumber() == questionNumber).findFirst();
        return score.map(Score::getTotal).orElse(0.0);
    }
}
