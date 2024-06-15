package com.example.recruitment.logging;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestResponseRepository extends MongoRepository<RequestResponse, String> {
}

