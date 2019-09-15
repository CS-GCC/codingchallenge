package codingchallenge.jobs;

import codingchallenge.collections.IndividualPositionRepository;
import codingchallenge.collections.TeamPositionRepository;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@SuppressWarnings("Duplicates")
@Component
public class RunPositionClean {

    private final IndividualPositionRepository individualPositionRepository;
    private final TeamPositionRepository teamPositionRepository;

    private final Logger logger =
            LoggerFactory.getLogger(RunPositionClean.class);

    @Autowired
    public RunPositionClean(IndividualPositionRepository individualPositionRepository, TeamPositionRepository teamPositionRepository) {
        this.individualPositionRepository = individualPositionRepository;
        this.teamPositionRepository = teamPositionRepository;
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void runClean() {
        logger.info("Beginning clean. Current time: " + new Date());
        Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        Map<String, List<IndividualPosition>> individualPositions =
                getIndividualPositions(date);
        Map<String, List<TeamPosition>> teamPositions =
                getTeamPositions(date);
        List<String> individualIds = cleanIndividualPositions(individualPositions);
        List<String> teamIds = cleanTeamPositions(teamPositions);
        individualPositionRepository.deleteAllByIdIn(individualIds);
        teamPositionRepository.deleteAllByIdIn(teamIds);
    }

    private Map<String, List<IndividualPosition>> getIndividualPositions(Date date) {
        List<IndividualPosition> individualPositions =
                individualPositionRepository.findAllByTimestampBefore(date);
        return
                individualPositions.stream().collect(groupingBy(IndividualPosition::getContestantId));
    }

    private Map<String, List<TeamPosition>> getTeamPositions(Date date) {
        List<TeamPosition> teamPositions =
                teamPositionRepository.findAllByTimestampBefore(date);
        return
                teamPositions.stream().collect(groupingBy(TeamPosition::getTeamId));
    }

    private List<String> cleanIndividualPositions(Map<String,
            List<IndividualPosition>> positionMap) {
        List<String> idsToClean = Lists.newArrayList();
        for (List<IndividualPosition> entries :
                positionMap.values()) {
            Map<String, List<Position>> entryMap =
                    entries.stream().collect(groupingBy(Position::createSimpleDate));
            for (List<Position> datePositions : entryMap.values()) {
                if (datePositions.size() > 1) {
                    datePositions.remove(datePositions.stream().min(Comparator.comparingDouble(Position::getPosition)).get());
                    idsToClean.addAll(datePositions.stream().map(Position::getId).collect(Collectors.toList()));
                }
            }
        }
        return idsToClean;
    }

    private List<String> cleanTeamPositions(Map<String,
            List<TeamPosition>> positionMap) {
        List<String> idsToClean = Lists.newArrayList();
        for (List<TeamPosition> entries :
                positionMap.values()) {
            Map<String, List<Position>> entryMap =
                    entries.stream().collect(groupingBy(Position::createSimpleDate));
            for (List<Position> datePositions : entryMap.values()) {
                if (datePositions.size() > 1) {
                    datePositions.remove(datePositions.stream().min(Comparator.comparingDouble(Position::getPosition)).get());
                    idsToClean.addAll(datePositions.stream().map(Position::getId).collect(Collectors.toList()));
                }
            }
        }
        return idsToClean;
    }

}
