package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.security.SecurityUtils.getCurrentUserLoginn;

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

    @PostMapping(value = "/AddhistoyLine")
    ResponseEntity<HistoryLine> addhistory(@Valid @RequestBody HistoryLine histo) {
        HistoryLine result = historyService.save(histo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<HistoryLine> printallhistory() {
        return historyService.AfiicheerTouthistorique(getCurrentUserLoginn());
    }

    @GetMapping("/{nomcateg}")
    public List<HistoryLine> printallhistory(@PathVariable(value = "nomcateg") String nomcateg) {
        return historyService.AfiicheerAvecnom(getCurrentUserLoginn(), nomcateg);
    }
}
