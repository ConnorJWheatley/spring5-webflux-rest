package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 17:18
 */
@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> listOfVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("api/v1/vendors/{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("api/v1/vendors/{id}")
    Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor){

        // block not good to use, but do not know how to get reactive version working
        Vendor foundVendor = vendorRepository.findById(id).block();

        if(!foundVendor.getFirstName().equals(vendor.getFirstName())){
            foundVendor.setFirstName(vendor.getFirstName());

            return vendorRepository.save(foundVendor);
        }
        return Mono.just(foundVendor);

//        Mono<Vendor> foundVendor = vendorRepository.findById(id);
//
//        return foundVendor
//                .filter(foundFirst -> !Objects.equals(foundFirst.getFirstName(), vendor.getFirstName()))
//                .filter(foundLast -> !Objects.equals(foundLast.getLastName(), vendor.getLastName()))
//                .flatMap(f -> {
//                    f.setFirstName(vendor.getFirstName());
//                    f.setLastName(vendor.getLastName());
//                    return vendorRepository.save(f);
//                }).switchIfEmpty(foundVendor);

    }
}
