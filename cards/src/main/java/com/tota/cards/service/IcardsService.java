package com.tota.cards.service;

import com.tota.cards.dto.CardsDto;
import jakarta.validation.Valid;

public interface IcardsService {

    void createCard(@Valid String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(@Valid CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
