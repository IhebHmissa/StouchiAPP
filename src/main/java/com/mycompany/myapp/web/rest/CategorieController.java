package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.service.CategorieService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Home")
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

    @PutMapping("/Categories/{login}/{nomcatego}")
    public ResponseEntity<Categorie> updateCategory(@PathVariable(value = "login") String login, @Valid @RequestBody Categorie cat) {
        Categorie result = categorieService.UpdateCatego(cat, login);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
      Delete Category
    */
    @DeleteMapping("/Categories/{login}/{nomcatego}")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable(value = "login") String login,
        @PathVariable(value = "nomcatego") String catname
    ) {
        categorieService.delete(login, catname);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    All the Categories ( Depence + Rebate )
     */
    @GetMapping("/Categories/{login}")
    public List<Categorie> getAllCategories(@PathVariable(value = "login") String login) {
        return categorieService.FindAllCategories(login);
    }

    @GetMapping("/Categories/depenseCatego/{login}")
    public List<Categorie> getDepenseCategories(@PathVariable(value = "login") String login) {
        return categorieService.FindADepenseCategories(login);
    }

    @GetMapping("/Categories/revenusCatego/{login}")
    public List<Categorie> getRevenusCategories(@PathVariable(value = "login") String login) {
        return categorieService.RevenusCategories(login);
    }

    @GetMapping("/Categories/SousCategories/{login}/{nomcatego}")
    public List<Categorie> getSousCategorie(
        @PathVariable(value = "login") String login,
        @PathVariable(value = "nomcatego") String nomcatego
    ) {
        return categorieService.sousCategeories(login, nomcatego);
    }

    @GetMapping("/Categories/specificCategorie/{login}/{nomcatego}")
    public List<Categorie> getSpecificCategorie(
        @PathVariable(value = "login") String login,
        @PathVariable(value = "nomcatego") String nomcatego
    ) {
        return categorieService.ReturnSpecificCategorie(login, nomcatego);
    }

    @GetMapping("/Categories/categorieswithperiod/{login}")
    public List<Categorie> prinPeriodCategoriesss(@PathVariable(value = "login") String login) {
        return categorieService.prinPeriodCategories(login);
    }
}
