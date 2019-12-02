package com.tnki.core.memox;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.exception.DailyCheckInException;
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

@RestController
public class MemoxController {
    final private MemoxApplicationService memoxApplicationService;

    @Autowired
    public MemoxController(MemoxApplicationService memoxApplicationService) {
        this.memoxApplicationService = memoxApplicationService;
    }

    @RequestMapping(value = "/learn-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public HashMap<String, Object> createLearnItem(@RequestBody @Valid CreateLearnItemCommand command) {
        int id = memoxApplicationService.createLearnItem(command);
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
        try {
            memoxApplicationService.userDailyCheckIn(username);
        } catch (Exception e) {
            throw new DailyCheckInException(e, username);
        }
    }
}
