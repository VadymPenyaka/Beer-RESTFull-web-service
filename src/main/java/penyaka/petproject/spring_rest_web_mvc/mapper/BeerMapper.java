package penyaka.petproject.spring_rest_web_mvc.mapper;

import org.mapstruct.Mapper;
import penyaka.petproject.spring_rest_web_mvc.entity.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer (BeerDTO beerDTO);

    BeerDTO beerToBeerDto (Beer beer);
}
