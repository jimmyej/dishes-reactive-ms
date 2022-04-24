package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Dish;
import com.mitocode.repository.DishRepository;
import com.mitocode.service.DishService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DishServiceImpl implements DishService {

	@Autowired
	private DishRepository dishRepository;

	public Flux<Dish> findAll() {
		return dishRepository.findAll();
	}

	@Override
	public Mono<Dish> findById(String id) {
		return dishRepository.findById(id);
	}

	@Override
	public Mono<Dish> save(Dish dish) {
		return dishRepository.save(dish);
	}

	@Override
	public Mono<Dish> update(Dish dish) {
		return dishRepository.save(dish);
	}

	@Override
	public Mono<Void> delete(String id) {
		return dishRepository.deleteById(id);
	}

}
