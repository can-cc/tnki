package com.tnki.core.memox;

import com.tnki.core.auth.AuthApplicationService;
import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.command.LearnItemCommand;
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
        long userID = authApplicationService.getIdByUsername(username);
        long id = memoxApplicationService.createLearnItem(command, userID);
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
        int userID = authApplicationService.getIdByUsername(username);
        memoxApplicationService.checkIn(userID);
    }

    @RequestMapping(value = "/learn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void learnItem(@RequestBody @Valid LearnItemCommand command) {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getIdByUsername(username);
        memoxApplicationService.learnItem(command, userID);
    }

    @RequestMapping(value = "/daily-learn-item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MemoLearningItem> getDailyLearnItem() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) userDetails).getUsername();
        int userID = authApplicationService.getIdByUsername(username);
        return memoxApplicationService.getDailyLearnItem(userID);
    }

    @RequestMapping(value = "/learning-items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MemoLearningItem> getLearningItems(@RequestParam int offset, @RequestParam int limit, @RequestHeader("X-App-Auth-UserID") int userID) {
        return memoxApplicationService.getLearningItems(userID, offset, limit);
    }
}
