package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCategorieService {

    public UserRepository userRepository;

    private boolean CheckExistanceCategori(String id, Categorie cat) {
        Optional<User> constants = userRepository.findOneByLogin(id);

        User value = constants.orElseThrow(() -> new RuntimeException("No such data found"));

        List<Categorie> ListCAT = value.getUserCategories();
        for (Categorie catt : ListCAT) {
            if (catt.equals(cat)) return true;
        }
        return false;
    }

    User saveCategory(String id, Categorie cat) {
        if (CheckExistanceCategori(id, cat)) {
            throw new CategorieAlreadyInTheSystemException();
        }
        Optional<User> constants = userRepository.findOneByLogin(id);

        User value = constants.orElseThrow(() -> new RuntimeException("No such data found"));

        value.setUserCategories(cat);
        return value;
    }

    List<Categorie> printAllCategories(String id) {
        Optional<User> constants = userRepository.findOneByLogin(id);

        User value = constants.orElseThrow(() -> new RuntimeException("No such data found"));

        assert value != null;
        return value.getUserCategories();
    }
}
