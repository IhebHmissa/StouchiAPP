package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Categorie;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesRepository extends MongoRepository<Categorie, String> {
    Categorie findByNomcatego(String Nom);

    Optional<Categorie> findById(String userId);
}
