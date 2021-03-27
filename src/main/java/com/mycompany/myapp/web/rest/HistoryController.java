package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HistoryLine;
import com.mycompany.myapp.service.HistoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/History")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping(value = "/AddhistoyLine/{login}")
    ResponseEntity<HistoryLine> addhistory(@PathVariable(value = "login") String login, @Valid @RequestBody HistoryLine histo) {
        HistoryLine result = historyService.save(histo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all/{login}")
    public List<HistoryLine> printallhistory(@PathVariable(value = "login") String login) {
        return historyService.AfiicheerTouthistorique(login);
    }

    @GetMapping("/{nomcateg}/{login}")
    public List<HistoryLine> printallhistory(
        @PathVariable(value = "login") String login,
        @PathVariable(value = "nomcateg") String nomcateg
    ) {
        return historyService.AfiicheerAvecnom(login, nomcateg);
    }
}
