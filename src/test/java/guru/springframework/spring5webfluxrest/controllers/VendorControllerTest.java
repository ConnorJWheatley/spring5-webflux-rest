package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 17:21
 */
public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void listOfVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Connor").lastName("Wheatley").build(),
                        Vendor.builder().firstName("Chris").lastName("Hammacott").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() {
        BDDMockito.given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().firstName("Connor").lastName("Wheatley").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);
    }
}