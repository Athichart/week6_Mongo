package com.example.week6_mongo.repository;


import com.example.week6_mongo.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {
    @Query(value = "{name: '?0'}")
    public Wizard findByName(String name);
    @Query(value = "{_id: ?0}")
    public Wizard findId(String id);



}
