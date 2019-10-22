package com.tnki.core.memox;

import com.tnki.core.auth.command.LoginCommand;
import com.tnki.core.memox.command.CreateLearnItemCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
public class MemoxController {
    @Autowired MemoxApplicationService memoxApplicationService;

    @RequestMapping(value = "/learn-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public HashMap<String, Object> createLearnItem(@RequestBody @Valid CreateLearnItemCommand command) {
        int id = memoxApplicationService.createLearnItem(command);

        HashMap<String, Object> res = new LinkedHashMap<>();
        res.put("id", id);

        return res;
    }
}
