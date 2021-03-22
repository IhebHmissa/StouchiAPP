package com.mycompany.myapp.config.StouchiAPP;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.service.mapper.NoSuchCategorieFoundException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/home")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    /*
       Create a new Category
     */
    @PostMapping(value = { "/Categories" })
    public ResponseEntity<Categorie> CreateCategory(@Valid @RequestBody Categorie cate) {
        Categorie result = categorieService.save(cate);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /*
    Update The Category
    */

    @PutMapping("/Categories/{id}")
    public ResponseEntity<Categorie> updateCategory(@PathVariable(value = "id") String id, @Valid @RequestBody Categorie cat)
        throws NoSuchCategorieFoundException {
        Categorie result = categorieService.UpdateCatego(cat, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
      Delete Category
    */
    @DeleteMapping("/Categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") String id) {
        categorieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    All the Categories ( Depence + Rebate )
     */
    @GetMapping("/Categories")
    public List<Categorie> getAllEmployees() {
        return categorieService.FindAllCategories();
    }
}
