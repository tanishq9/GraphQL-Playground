package com.example.graphqlplayground.lec05.controller;

import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Controller method returns CompletableFuture, in implementation a delay of 5 seconds is added,
// the response was sent to user after 5 seconds only i.e. only when the CF is completed then only we return response to client,
// so no partial processing of CF.
@RestController
public class DummyRestController {

	@GetMapping("/{value1}/{value2}")
	public CompletableFuture<String> cfTest(@PathVariable String value1, @PathVariable String value2) {
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("CF1 thread: " + Thread.currentThread().getName());
			return value1;
		});
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("CF2 thread: " + Thread.currentThread().getName());
			return value2;
		});
		return cf1.thenCombine(cf2, (cf1Output, cf2Output) -> cf1Output + cf2Output);
	}
}
