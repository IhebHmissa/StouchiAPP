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

    Categorie UpdateCatego(Categorie cat, String login) {
        Categorie catNEW = categoriesRepository.findOneByUserLoginAndNomcatego(login, cat.getNomcatego());
        if (!cat.getColor().equals("")) catNEW.setColor(cat.getColor());
        if (cat.getMaxmontant() != 0) catNEW.setMaxmontant(cat.getMaxmontant());
        if (cat.getMinmontant() != 0) catNEW.setMinmontant(cat.getMinmontant());
        if (!cat.getNomcatego().equals("")) catNEW.setNomcatego(cat.getNomcatego());
        if (cat.getMontant() != 0) catNEW.setMontant(cat.getMontant());
        return categoriesRepository.save(catNEW);
    }

    private Categorie searchByname(Categorie cat, List<Categorie> categs) {
        String login = cat.getUserLogin();
        for (Categorie catt : categs) {
            if (catt.getUserLogin().equals(login)) return catt;
        }
        return null;
    }

    public Categorie save(Categorie cat) {
        if (searchByname(cat, categoriesRepository.findByNomcatego(cat.getNomcatego())) != null) {
            throw new CategorieAlreadyInTheSystemException();
        }
        return categoriesRepository.insert(cat);
    }

    public void delete(String login, String nomcatego) {
        Categorie listaa = categoriesRepository.findOneByUserLoginAndNomcatego(login, nomcatego);
        List<Categorie> listaaaa = categoriesRepository.findByUserLoginAndOriginType(login, nomcatego);
        for (Categorie cat : listaaaa) {
            categoriesRepository.deleteById(cat.getID());
        }
        categoriesRepository.deleteById(listaa.getID());
    }

    public List<Categorie> FindAllCategories(String login) {
        return categoriesRepository.findByUserLogin(login);
    }

    public List<Categorie> FindADepenseCategories(String login) {
        return categoriesRepository.findByUserLoginAndTypeAndOriginType(login, "Depense", "Catego");
    }

    public List<Categorie> RevenusCategories(String login) {
        return categoriesRepository.findByUserLoginAndTypeAndOriginType(login, "Revenus", "Catego");
    }

    public List<Categorie> sousCategeories(String login, String nomcatego) {
        return categoriesRepository.findByUserLoginAndOriginType(login, nomcatego);
    }

    public List<Categorie> ReturnSpecificCategorie(String login, String nomcatego) {
        return categoriesRepository.findByUserLoginAndNomcatego(login, nomcatego);
    }
}
