package penyaka.petproject.spring_rest_web_mvc.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-23T21:27:29+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Override
    public Beer beerDtoToBeer(BeerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Beer.BeerBuilder beer = Beer.builder();

        beer.id( dto.getId() );
        beer.version( dto.getVersion() );
        beer.name( dto.getName() );
        beer.style( dto.getStyle() );
        beer.upc( dto.getUpc() );
        beer.quantityOnHand( dto.getQuantityOnHand() );
        beer.price( dto.getPrice() );
        beer.createDate( dto.getCreateDate() );
        beer.updateDate( dto.getUpdateDate() );

        return beer.build();
    }

    @Override
    public BeerDTO beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDTO.BeerDTOBuilder beerDTO = BeerDTO.builder();

        beerDTO.id( beer.getId() );
        beerDTO.version( beer.getVersion() );
        beerDTO.name( beer.getName() );
        beerDTO.style( beer.getStyle() );
        beerDTO.upc( beer.getUpc() );
        beerDTO.quantityOnHand( beer.getQuantityOnHand() );
        beerDTO.price( beer.getPrice() );
        beerDTO.createDate( beer.getCreateDate() );
        beerDTO.updateDate( beer.getUpdateDate() );

        return beerDTO.build();
    }
}
