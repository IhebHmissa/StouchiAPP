package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HistoryLine;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryLine, String> {
    List<HistoryLine> findByUserLogin(String loginn);
    List<HistoryLine> findByUserLoginAndCategoriName(String loginn, String catego);
}
