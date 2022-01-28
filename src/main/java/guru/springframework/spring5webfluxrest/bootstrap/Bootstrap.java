package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 16:03
 */
@Component public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }
    @Override
    public void run(String... args) {
        System.out.println("###### LOADING DATA ON BOOTSTRAP");
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        categoryRepository.deleteAll()
                .thenMany(Flux.just("Fruits", "Nuts", "Breads", "Meats", "Eggs")
                .map(name -> new Category(null, name))
                .flatMap(categoryRepository::save))
                .then(categoryRepository.count())
                .subscribe(categories -> System.out.println(categories + " categories saved"));

        System.out.println("Loaded categories: " + categoryRepository.count().subscribe());
    }

    private void loadVendors() {
        vendorRepository.deleteAll()
                .thenMany(Flux.just(
                        Vendor.builder().firstName("Joe").lastName("Buck").build(),
                        Vendor.builder().firstName("Michael").lastName("Weston").build(),
                        Vendor.builder().firstName("Jessie").lastName("Waters").build(),
                        Vendor.builder().firstName("Jimmy").lastName("Buffet").build())
                        .flatMap(vendorRepository::save))
                .then(vendorRepository.count()).subscribe(vendors -> System.out.println(vendors + " vendors saved"));

        System.out.println("Loaded vendors: " + vendorRepository.count().subscribe());
    }
}