package com.mycompany.myapp.service;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.repository.CategoriesRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;
    private final CategoriesRepository categoriesRepository;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager,
        CategoriesRepository categoriesRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.categoriesRepository = categoriesRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(
                user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Activated user: {}", user);
                    return user;
                }
            );
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(
                user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    return user;
                }
            );
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(
                user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    return user;
                }
            );
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                }
            );
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                }
            );
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setUserSolde(userDTO.getUserSolde());
        Categorie cat1 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Nourritures et boissons", "FFFFF");
        categoriesRepository.save(cat1);
        Categorie cat2 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Logement", "FFFFF");
        categoriesRepository.save(cat2);
        Categorie cat3 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Santé", "FFFFF");
        categoriesRepository.save(cat3);
        Categorie cat4 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Services public", "FFFFF");
        categoriesRepository.save(cat4);
        Categorie cat5 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Transport", "FFFFF");
        categoriesRepository.save(cat5);
        Categorie cat6 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Education", "FFFFF");
        categoriesRepository.save(cat6);
        Categorie cat7 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Loisir", "FFFFF");
        categoriesRepository.save(cat7);
        Categorie cat8 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Divers", "FFFFF");
        categoriesRepository.save(cat8);
        Categorie cat9 = new Categorie("Depense", newUser.getLogin(), "Catego", 0, "Imprévus", "FFFFF");
        categoriesRepository.save(cat9);
        Categorie cat10 = new Categorie("Depense", newUser.getLogin(), "Nourritures et boissons", 0, "Alimentation", "FFFFF");
        categoriesRepository.save(cat10);
        Categorie cat11 = new Categorie("Depense", newUser.getLogin(), "Nourritures et boissons", 0, "Bar café", "FFFFF");
        categoriesRepository.save(cat11);
        Categorie cat12 = new Categorie("Depense", newUser.getLogin(), "Nourritures et boissons", 0, "Restaurant Fast Food", "FFFFF");
        categoriesRepository.save(cat12);
        Categorie cat13 = new Categorie("Depense", newUser.getLogin(), "Logement", 0, "Loyer", "FFFFF");
        categoriesRepository.save(cat13);
        Categorie cat14 = new Categorie("Depense", newUser.getLogin(), "Logement", 0, "Prêt", "FFFFF");
        categoriesRepository.save(cat14);
        Categorie cat15 = new Categorie("Depense", newUser.getLogin(), "Logement", 0, "Taxes", "FFFFF");
        categoriesRepository.save(cat15);
        Categorie cat16 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Assurances", "FFFFF");
        categoriesRepository.save(cat16);
        Categorie cat17 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Consultation", "FFFFF");
        categoriesRepository.save(cat17);
        Categorie cat18 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Médicaments", "FFFFF");
        categoriesRepository.save(cat18);
        Categorie cat19 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Radiologie", "FFFFF");
        categoriesRepository.save(cat19);
        Categorie cat20 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Hospitalisation", "FFFFF");
        categoriesRepository.save(cat20);
        Categorie cat21 = new Categorie("Depense", newUser.getLogin(), "Santé", 0, "Autre personnel médical", "FFFFF");
        categoriesRepository.save(cat21);
        Categorie cat22 = new Categorie("Depense", newUser.getLogin(), "Services public", 0, "Electricité", "FFFFF");
        categoriesRepository.save(cat22);
        Categorie cat23 = new Categorie("Depense", newUser.getLogin(), "Services public", 0, "Chauffage", "FFFFF");
        categoriesRepository.save(cat23);
        Categorie cat24 = new Categorie("Depense", newUser.getLogin(), "Services public", 0, "Eau", "FFFFF");
        categoriesRepository.save(cat24);
        Categorie cat25 = new Categorie("Depense", newUser.getLogin(), "Services public", 0, "Téléphone", "FFFFF");
        categoriesRepository.save(cat25);
        Categorie cat26 = new Categorie("Depense", newUser.getLogin(), "Services public", 0, "Internet", "FFFFF");
        categoriesRepository.save(cat26);
        Categorie cat27 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Transports communs", "FFFFF");
        categoriesRepository.save(cat27);
        Categorie cat28 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Taxi", "FFFFF");
        categoriesRepository.save(cat28);
        Categorie cat29 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Voyages", "FFFFF");
        categoriesRepository.save(cat29);
        Categorie cat30 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Assurances", "FFFFF");
        categoriesRepository.save(cat30);
        Categorie cat31 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Entretiens des véhicules", "FFFFF");
        categoriesRepository.save(cat31);
        Categorie cat32 = new Categorie("Depense", newUser.getLogin(), "Transport", 0, "Carburant", "FFFFF");
        categoriesRepository.save(cat32);
        Categorie cat33 = new Categorie("Depense", newUser.getLogin(), "Education", 0, "Inscriptions", "FFFFF");
        categoriesRepository.save(cat33);
        Categorie cat34 = new Categorie("Depense", newUser.getLogin(), "Education", 0, "Cours de soutien", "FFFFF");
        categoriesRepository.save(cat34);
        Categorie cat35 = new Categorie("Depense", newUser.getLogin(), "Education", 0, "Fourniture", "FFFFF");
        categoriesRepository.save(cat35);
        Categorie cat36 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Culture et évènements sportifs", "FFFFF");
        categoriesRepository.save(cat36);
        Categorie cat37 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Bien-être beauté", "FFFFF");
        categoriesRepository.save(cat37);
        Categorie cat38 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Assurances des biens", "FFFFF");
        categoriesRepository.save(cat38);
        Categorie cat39 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Livre", "FFFFF");
        categoriesRepository.save(cat39);
        Categorie cat40 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Abonnements", "FFFFF");
        categoriesRepository.save(cat40);
        Categorie cat41 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Sport remise en forme", "FFFFF");
        categoriesRepository.save(cat41);
        Categorie cat42 = new Categorie("Depense", newUser.getLogin(), "Loisir", 0, "Hobbies et passion", "FFFFF");
        categoriesRepository.save(cat42);
        Categorie cat43 = new Categorie("Depense", newUser.getLogin(), "Divers", 0, "Cadeaux", "FFFFF");
        categoriesRepository.save(cat43);
        Categorie cat44 = new Categorie("Depense", newUser.getLogin(), "Divers", 0, "Animaux", "FFFFF");
        categoriesRepository.save(cat44);
        Categorie cat45 = new Categorie("Depense", newUser.getLogin(), "Divers", 0, "Jardin", "FFFFF");
        categoriesRepository.save(cat45);
        Categorie cat46 = new Categorie("Depense", newUser.getLogin(), "Imprévus", 0, "Amendes", "FFFFF");
        categoriesRepository.save(cat46);
        Categorie cat47 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Salaires", "FFFFF");
        categoriesRepository.save(cat47);
        Categorie cat48 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Vente et rentrée de loyer", "FFFFF");
        categoriesRepository.save(cat48);
        Categorie cat49 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Remboursements", "FFFFF");
        categoriesRepository.save(cat49);
        Categorie cat50 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Allocation familiale", "FFFFF");
        categoriesRepository.save(cat50);
        Categorie cat51 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Aide", "FFFFF");
        categoriesRepository.save(cat51);
        Categorie cat52 = new Categorie("Revenus", newUser.getLogin(), "Catego", 0, "Divers", "FFFFF");
        categoriesRepository.save(cat52);
        Categorie cat53 = new Categorie("Revenus", newUser.getLogin(), "Divers", 0, "Cadeaux", "FFFFF");
        categoriesRepository.save(cat53);

        userRepository.save(newUser);

        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        this.clearUserCaches(existingUser);
        return true;
    }

    public float soldeUser(String login) {
        Optional<User> constants = userRepository.findOneByLogin(login);
        User value = constants.orElseThrow(() -> new RuntimeException("No such data found"));
        return value.getUserSolde();
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
            .of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(
                user -> {
                    this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail().toLowerCase());
                    }
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO
                        .getAuthorities()
                        .stream()
                        .map(authorityRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(managedAuthorities::add);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }
            )
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(
                user -> {
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                    log.debug("Deleted User: {}", user);
                }
            );
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(
                user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    if (email != null) {
                        user.setEmail(email.toLowerCase());
                    }
                    user.setLangKey(langKey);
                    user.setImageUrl(imageUrl);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                }
            );
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(
                user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                }
            );
    }

    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(
                user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                }
            );
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
