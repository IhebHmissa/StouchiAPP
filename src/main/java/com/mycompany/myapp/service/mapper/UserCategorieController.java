package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.User;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Home/Cont")
public class UserCategorieController {

    private UserCategorieService userCategorieService;

    @PostMapping(value = "/{id}")
    public ResponseEntity<User> updateCategory(@PathVariable(value = "id") String id, @Valid @RequestBody Categorie cat) {
        User result = userCategorieService.saveCategory(id, cat);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public List<Categorie> updateCategory(@PathVariable(value = "id") String id) {
        return userCategorieService.printAllCategories(id);
    }
}
