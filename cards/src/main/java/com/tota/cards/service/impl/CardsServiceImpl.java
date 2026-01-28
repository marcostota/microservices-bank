package com.tota.cards.service.impl;

import com.tota.cards.dto.CardsDto;
import com.tota.cards.entity.Cards;
import com.tota.cards.exception.CardsAlreadyExistsException;
import com.tota.cards.exception.ResourceNotFoundException;
import com.tota.cards.mapper.CardsMapper;
import com.tota.cards.repository.CardsRepository;
import com.tota.cards.service.IcardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements IcardsService {

    private CardsRepository cardsRepository;


    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cardsOptional = cardsRepository.findByMobileNumber(mobileNumber);
        if(cardsOptional.isPresent()) {
            throw new CardsAlreadyExistsException("Card already exists for mobile number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }


    private Cards createNewCard(String mobileNumber) {
        Cards newCards = new Cards();
        long randomCardNumer = 100000000000L + new Random().nextInt(900000000);
        newCards.setCardNumber(Long.toString(randomCardNumer));
        newCards.setMobileNumber(mobileNumber);
        newCards.setCardType("CREDIT_CARD");
        newCards.setTotalLimit(1_00_000);
        newCards.setAmountUsed(0);
        newCards.setAvailableAmount(1_00_000);
        return newCards;
    }


    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","CardNumber" , mobileNumber));

        return CardsMapper.mapToCardsDto(cards, new CardsDto());

    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card","CardNUmer" , cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","mobileNumer" , mobileNumber)
        );
        cardsRepository.delete(cards);
        return true;
    }
}
