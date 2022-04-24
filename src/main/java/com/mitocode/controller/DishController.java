package com.mitocode.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.document.Dish;
import com.mitocode.service.DishService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
	
	@Autowired
	private DishService dishService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Dish>>> getAllDishes(){
		return Mono.just(
					ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(dishService.findAll())
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Dish>> getDishById(@PathVariable String id){
		return dishService.findById(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				);
	}
	
	@PostMapping
	public Mono<ResponseEntity<Dish>> registerDish(@RequestBody Dish dish, final ServerHttpRequest req){
		return dishService.save(dish)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				);
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Dish>> editDish(@PathVariable("id") String id, @RequestBody Dish dish){
		return dishService.findById(id).zipWith(Mono.just(dish), (db, rq) -> {
			db.setId(id);
			db.setName(rq.getName());
			db.setPrice(rq.getPrice());
			db.setStatus(rq.isStatus());
			return db;
		})
				.flatMap(dishService::update)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		return dishService.findById(id)
				.flatMap(p -> dishService.delete(id)
						.thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
						)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
