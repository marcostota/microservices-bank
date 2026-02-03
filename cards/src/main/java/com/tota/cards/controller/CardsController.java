package com.tota.cards.controller;

import com.tota.cards.dto.CardsDto;
import com.tota.cards.dto.ResponseDto;
import com.tota.cards.service.IcardsService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces =  {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {


    @Value("${build.version}")
    private String buildVersion;

    private final IcardsService cardsService;

    public CardsController(IcardsService cardsService) {
        this.cardsService = cardsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam String mobileNumber){
        cardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED.name(), "Card created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobileNumber){
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardsDto cardsDto){
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.name(), "Card updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.name(), "Failed to update card"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber){
        boolean isDeleted = cardsService.deleteCard(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.name(), "Card deleted successfully"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.name(),  "Failed to delete card"));
        }
    }

    @GetMapping("/buildinfo")
    public ResponseEntity<String> buildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

}
