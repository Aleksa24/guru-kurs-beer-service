package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.common.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static guru.springframework.msscbeerservice.config.JmsConfig.BREWING_REQUEST_QUEUE;
import static guru.springframework.msscbeerservice.config.JmsConfig.NEW_INVENTORY_QUEUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent){
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewing beer: " + beer.getBeerName() + ": QOH: " +beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(NEW_INVENTORY_QUEUE,newInventoryEvent);
    }

}
