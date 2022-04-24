package com.mitocode.service;

import com.mitocode.document.Dish;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DishService {
	public Flux<Dish> findAll();
	public Mono<Dish> findById(String id);
	public Mono<Dish> save(Dish dish);
	public Mono<Dish> update(Dish dish);
	public Mono<Void> delete(String id);
}
