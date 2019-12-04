package com.tnki.core.memox;

import com.tnki.core.auth.AuthApplicationService;
import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.command.LearnItemCommand;
import com.tnki.core.memox.exception.DailyCheckInException;
import com.tnki.core.memox.model.MemoLearningItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class MemoxController {
    final private MemoxApplicationService memoxApplicationService;
    final private AuthApplicationService authApplicationService;

    @Autowired
    public MemoxController(MemoxApplicationService memoxApplicationService, AuthApplicationService authApplicationService) {
        this.memoxApplicationService = memoxApplicationService;
        this.authApplicationService = authApplicationService;
    }

    @RequestMapping(value = "/learn-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public HashMap<String, Object> createLearnItem(@RequestBody @Valid CreateLearnItemCommand command) {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getUserIdByUsername(username);
        int id = memoxApplicationService.createLearnItem(command, userID);
        HashMap<String, Object> res = new LinkedHashMap<>();
        res.put("id", id);
        return res;
    }

    @Transactional
    @RequestMapping(value = "/daily-check-in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void dailyCheckIn() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getUserIdByUsername(username);
        try {
            memoxApplicationService.userDailyCheckIn(userID);
        } catch (Exception e) {
            throw new DailyCheckInException(e, username);
        }
    }

    @RequestMapping(value = "/learn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void learnItem(@RequestBody @Valid LearnItemCommand command) {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getUserIdByUsername(username);
        memoxApplicationService.learnItem(command, userID);
    }

    @RequestMapping(value = "/daily-learn-item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MemoLearningItem> getDailyLearnItem() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getUserIdByUsername(username);
        return memoxApplicationService.getDailyLearnItem(userID);
    }
}
