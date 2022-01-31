package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 16:49
 */
@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    public Flux<Category> listOfCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    public Mono<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    public Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/api/v1/categories/{id}")
    public Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    public Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {

//        Category foundCategory = categoryRepository.findById(id).block();
//
//        if(foundCategory.getDescription() != category.getDescription()){
//            foundCategory.setDescription(category.getDescription());
//            return categoryRepository.save(foundCategory);
//        }
//
//        return Mono.just(foundCategory);

        Mono<Category> foundCategory = categoryRepository.findById(id);

        return foundCategory
                .filter(found -> !Objects.equals(found.getDescription(), category.getDescription()))
                .flatMap(f -> {
                    f.setDescription(category.getDescription());
                    return categoryRepository.save(f);
                }).switchIfEmpty(foundCategory);
    }
}

