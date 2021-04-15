package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.HistoryLine;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CategoriesRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.mapper.CategorieAlreadyInTheSystemException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserRepository userRepository;

    private float calculSommeCategori(String login, String nomcatego) {
        float totale = 0;
        List<Categorie> listaa = categoriesRepository.findByUserLoginAndOriginType(login, nomcatego);
        for (Categorie cat : listaa) {
            totale = totale + cat.getMontant();
        }
        return totale;
    }

    public Categorie UpdateCatego(Categorie cat, String login) {
        Categorie catNEW = categoriesRepository.findOneByUserLoginAndNomcatego(login, cat.getNomcatego());
        float montan = cat.getMontant();

        if (!cat.getColor().equals("")) catNEW.setColor(cat.getColor());
        if (cat.getMaxmontant() != 0) catNEW.setMaxmontant(cat.getMaxmontant());
        if (cat.getMinmontant() != 0) catNEW.setMinmontant(cat.getMinmontant());
        if (!cat.getNomcatego().equals("")) catNEW.setNomcatego(cat.getNomcatego());
        if (cat.getPeriodcategorie() != null) catNEW.setPeriodcategorie(cat.getPeriodcategorie());
        if (cat.getMontant() != 0) {
            if (!cat.getOriginType().equals("Catego")) {
                Categorie Categooo = categoriesRepository.findOneByUserLoginAndNomcatego(login, cat.getOriginType());
                Categooo.setMontant(Categooo.getMontant() + cat.getMontant());
                categoriesRepository.save(Categooo);
            }
            catNEW.setMontant(cat.getMontant() + catNEW.getMontant());
            Optional<User> constants = userRepository.findOneByLogin(catNEW.getUserLogin());
            User value = constants.orElseThrow(() -> new RuntimeException("No such data found"));
            if (catNEW.getType().equals("Depense")) value.setUserSolde(cat.getMontant() - value.getUserSolde());
            if (catNEW.getType().equals("Revenus")) value.setUserSolde(cat.getMontant() + value.getUserSolde());
            userRepository.save(value);

            HistoryLine hist = new HistoryLine(catNEW.getNomcatego(), LocalDateTime.now(), montan, catNEW.getUserLogin());
            historyService.save(hist);
        }
        return categoriesRepository.save(catNEW);
    }

    // @Scheduled(cron="*/20 * * * * *", zone="Africa/Tunis")

    //@Scheduled(cron="0 0 7 * * *", zone="Africa/Tunis") // every day at 7:00AM
    /*
    public void Scheduled_task ()
    {
        System.out.println("This a repeated task");
        List<User> listaa = userRepository.findAll();
        for ( User user :listaa) {
            for (Categorie catego :FindAllCategories(user.getLogin()) )
            {
                if (catego.getPeriodcategorie()!=null)
                {
                    if (catego.getPeriodcategorie().getDate_fin()==null)
                    {
                        if (LocalDateTime.now().getDayOfMonth() == catego.getPeriodcategorie().getDate_deb().getDayOfMonth() & (catego.getPeriodcategorie().getNumberleft()>0))
                            {
                                catego.setMontant(catego.getMontant()+catego.getPeriodcategorie().getFixedMontant());
                                categoriesRepository.save(catego);
                                user.setUserSolde(user.getUserSolde()+catego.getPeriodcategorie().getFixedMontant());
                                userRepository.save(user);
                                HistoryLine hist = new HistoryLine(catego.getNomcatego(), LocalDateTime.now(), catego.getPeriodcategorie().getFixedMontant(), catego.getUserLogin());
                                historyService.save(hist);
                            }

                    }
                    else {
                        if (LocalDateTime.now().getDayOfMonth()==catego.getPeriodcategorie().getDate_deb().getDayOfMonth())and()
                        {

                        }
                    }
                }
            }

        }


    }
*/
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
