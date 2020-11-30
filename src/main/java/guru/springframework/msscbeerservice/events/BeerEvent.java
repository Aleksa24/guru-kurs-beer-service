package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 5925166867424343040L;

    private final BeerDto beerDto;



}
