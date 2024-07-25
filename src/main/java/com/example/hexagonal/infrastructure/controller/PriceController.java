package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.service.PriceService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@OpenAPIDefinition(tags = { @Tag(name = "Prices") })
public class PriceController {

    private final PriceService priceService;

    private static final Logger LOG = LoggerFactory.getLogger(PriceController.class);

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(method = "GET", summary = "Get a price by date, productId and brandId", description = "Fetches the price details for a given date, productId and brandId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found and returned", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Price not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @Parameter(description = "Price date", example = "2024-07-20:08.00.00") @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd:HH.mm.ss")
            LocalDateTime date,
            @Parameter(description = "Product identifier associated to the price", example = "150") @RequestParam("productId")
            Long productId,
            @Parameter(description = "Brand identifier associated to the price", example = "1") @RequestParam("brandId")
            Long brandId) {

        LOG.info("Received request to get price for productId: {}, brandId: {}, date: {}", productId, brandId, date);

        PriceResponse priceResponse = priceService.findPrice(date, brandId, productId);

        LOG.info("Returning price response: {}", priceResponse);

        return ResponseEntity.ok(priceResponse);
    }
}
