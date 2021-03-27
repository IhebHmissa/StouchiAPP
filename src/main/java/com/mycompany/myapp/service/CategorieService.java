package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.repository.CategoriesRepository;
import com.mycompany.myapp.service.mapper.CategorieAlreadyInTheSystemException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    private HistoryService historyService;

    private float calculSommeCategori(String login, String nomcatego) {
        float totlae = 0;
        List<Categorie> listaa = categoriesRepository.findByUserLoginAndOriginType(login, nomcatego);
        for (Categorie cat : listaa) {
            totlae = totlae + cat.getMontant();
        }
        return totlae;
    }

    public Categorie UpdateCatego(Categorie cat, String login) {
        Categorie catNEW = categoriesRepository.findOneByUserLoginAndNomcatego(login, cat.getNomcatego());
        //float montan=cat.getMontant();
        if (!cat.getColor().equals("")) catNEW.setColor(cat.getColor());
        catNEW.setMaxmontant(cat.getMaxmontant());
        catNEW.setMinmontant(cat.getMinmontant());
        if (!cat.getNomcatego().equals("")) catNEW.setNomcatego(cat.getNomcatego());
        if (cat.getMontant() != 0) {
            catNEW.setMontant(cat.getMontant() + catNEW.getMontant());
            if (!cat.getOriginType().equals("Catego")) categoriesRepository
                .findOneByUserLoginAndNomcatego(login, cat.getNomcatego())
                .setMontant(calculSommeCategori(login, cat.getOriginType()));
            /*HistoryLine hist = new HistoryLine (catNEW.getNomcatego(), LocalDateTime.now(),montan,login);
                    historyService.save(hist);*/
        }
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

    public List<Categorie> prinPeriodCategories(String login) {
        List<Categorie> lista = categoriesRepository.findByUserLoginAndPeriodcategorie(login, null);
        List<Categorie> listaat = categoriesRepository.findByUserLogin(login);
        for (Categorie cat : lista) {
            listaat.remove(cat);
        }

        return listaat;
    }
}
