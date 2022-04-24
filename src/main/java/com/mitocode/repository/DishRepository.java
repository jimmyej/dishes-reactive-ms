package com.mitocode.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.document.Dish;

@Repository
public interface DishRepository extends ReactiveMongoRepository<Dish, String>{

}
