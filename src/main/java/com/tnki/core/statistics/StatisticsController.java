package com.tnki.core.statistics;

import com.tnki.core.auth.AuthApplicationService;
import com.tnki.core.statistics.model.DailyStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
public class StatisticsController {
    final private AuthApplicationService authApplicationService;
    final private StatisticsApplicationService statisticsApplicationService;

    @Autowired
    public StatisticsController(AuthApplicationService authApplicationService, StatisticsApplicationService statisticsApplicationService) {
        this.authApplicationService = authApplicationService;
        this.statisticsApplicationService = statisticsApplicationService;
    }

    @RequestMapping(value = "/daily-statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DailyStatistics getDailyStatistics() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getUserIdByUsername(username);

        return statisticsApplicationService.getUserDailyStatistics(userID);
    }
}