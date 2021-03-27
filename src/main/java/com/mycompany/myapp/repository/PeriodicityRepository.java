package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.periodicite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicityRepository extends MongoRepository<periodicite, String> {}
