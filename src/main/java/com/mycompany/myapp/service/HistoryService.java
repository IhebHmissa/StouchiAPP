package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.HistoryLine;
import com.mycompany.myapp.repository.HistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public List<HistoryLine> AfiicheerTouthistorique(String login) {
        return historyRepository.findByUserLogin(login);
    }

    public List<HistoryLine> AfiicheerAvecnom(String loginn, String nom) {
        return historyRepository.findByUserLoginAndCategoriName(loginn, nom);
    }

    public HistoryLine save(HistoryLine cat) {
        HistoryLine hist = new HistoryLine(cat.getCategoriName(), LocalDateTime.now(), cat.getMontant(), cat.getUserLogin());
        return historyRepository.insert(hist);
    }
}
