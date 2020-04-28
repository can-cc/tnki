package com.tnki.core.statistics;

import com.tnki.core.auth.AuthApplicationService;
import com.tnki.core.common.MemoDateUtil;
import com.tnki.core.statistics.exception.DailyStatisticsNotFoundException;
import com.tnki.core.statistics.model.DailyStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class StatisticsController {
    final private AuthApplicationService authApplicationService;
    final private StatisticsApplicationService statisticsApplicationService;

    @Autowired
    public StatisticsController(AuthApplicationService authApplicationService, StatisticsApplicationService statisticsApplicationService) {
        this.authApplicationService = authApplicationService;
        this.statisticsApplicationService = statisticsApplicationService;
    }

    @RequestMapping(value = "/daily-learn-statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DailyStatistics getDailyStatistics(@RequestHeader("X-App-Auth-UserID") long userID) {
        Optional<DailyStatistics> optional = statisticsApplicationService.getDailyStatistics(userID, MemoDateUtil.today());
        if (!optional.isPresent()) {
            throw new DailyStatisticsNotFoundException();
        }
        return optional.get();
    }
}
