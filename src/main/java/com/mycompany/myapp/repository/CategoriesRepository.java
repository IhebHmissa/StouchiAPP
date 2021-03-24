package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Categorie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesRepository extends MongoRepository<Categorie, String> {
    List<Categorie> findByNomcatego(String Nom);
    List<Categorie> findByUserLogin(String login);
    Optional<Categorie> findById(String userId);
    List<Categorie> findByUserLoginAndNomcatego(String userr, String typecatergo);
    Categorie findOneByUserLoginAndNomcatego(String userr, String typecatergo);
    List<Categorie> findByUserLoginAndOriginType(String userr, String origin);
    List<Categorie> findByUserLoginAndNomcategoAndType(String userr, String typecatergo, String typee);
    List<Categorie> findByUserLoginAndTypeAndOriginType(String userr, String typee, String nomcategomere);
}
