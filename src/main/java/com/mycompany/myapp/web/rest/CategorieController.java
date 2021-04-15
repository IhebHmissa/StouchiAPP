package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.security.SecurityUtils.getCurrentUserLoginn;

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

    @PutMapping("/Categories/{nomcatego}")
    public ResponseEntity<Categorie> updateCategory(@Valid @RequestBody Categorie cat) {
        Categorie result = categorieService.UpdateCatego(cat, getCurrentUserLoginn());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
      Delete Category
    */
    @DeleteMapping("/Categories/{nomcatego}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "nomcatego") String catname) {
        categorieService.delete(getCurrentUserLoginn(), catname);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    All the Categories ( Depence + Rebate )
     */
    @GetMapping("/Categories")
    public List<Categorie> getAllCategories() {
        return categorieService.FindAllCategories(getCurrentUserLoginn());
    }

    @GetMapping("/Categories/depenseCatego")
    public List<Categorie> getDepenseCategories() {
        return categorieService.FindADepenseCategories(getCurrentUserLoginn());
    }

    @GetMapping("/Categories/revenusCatego")
    public List<Categorie> getRevenusCategories() {
        return categorieService.RevenusCategories(getCurrentUserLoginn());
    }

    @GetMapping("/Categories/SousCategories/{nomcatego}")
    public List<Categorie> getSousCategorie(@PathVariable(value = "nomcatego") String nomcatego) {
        return categorieService.sousCategeories(getCurrentUserLoginn(), nomcatego);
    }

    @GetMapping("/Categories/specificCategorie/{nomcatego}")
    public List<Categorie> getSpecificCategorie(@PathVariable(value = "nomcatego") String nomcatego) {
        return categorieService.ReturnSpecificCategorie(getCurrentUserLoginn(), nomcatego);
    }

    @GetMapping("/Categories/categorieswithperiod")
    public List<Categorie> prinPeriodCategoriesss() {
        return categorieService.prinPeriodCategories(getCurrentUserLoginn());
    }
}
