package com.mycompany.myapp.config.StouchiAPP;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.repository.CategoriesRepository;
import com.mycompany.myapp.service.mapper.CategorieAlreadyInTheSystemException;
import com.mycompany.myapp.service.mapper.NoSuchCategorieFoundException;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categorie> findAll() {
        return categoriesRepository.findAll();
    }

    public Optional<Categorie> findCategorie(String id) {
        return categoriesRepository.findById(id);
    }

    public Categorie getById(String id) {
        try {
            return categoriesRepository.findById(id).get();
        } catch (NoSuchCategorieFoundException ex) {
            throw new NoSuchCategorieFoundException();
        }
    }

    public Categorie UpdateCatego(Categorie cat, String id) {
        Categorie catNEW = categoriesRepository.findById(id).orElseThrow(() -> new NoSuchCategorieFoundException());

        catNEW.setColor(cat.getColor());
        catNEW.setMaxmontant(cat.getMaxmontant());
        catNEW.setMinmontant(cat.getMinmontant());
        catNEW.setNomcatego(cat.getNomcatego());
        catNEW.setMontant(cat.getMontant());
        return categoriesRepository.save(catNEW);
    }

    public Categorie save(Categorie cat) {
        if (categoriesRepository.findByNomcatego(cat.getNomcatego()) != null) {
            throw new CategorieAlreadyInTheSystemException();
        }
        return categoriesRepository.insert(cat);
    }

    public void delete(String id) {
        categoriesRepository.deleteById(id);
    }

    public List<Categorie> FindAllCategories() {
        return categoriesRepository.findAll();
    }
}
