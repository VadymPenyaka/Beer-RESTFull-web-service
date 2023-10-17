package penyaka.petproject.spring_rest_web_mvc.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import penyaka.petproject.spring_rest_web_mvc.entity.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T00:18:56+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Override
    public Beer beerDtoToBeer(BeerDTO beerDTO) {
        if ( beerDTO == null ) {
            return null;
        }

        Beer.BeerBuilder beer = Beer.builder();

        beer.id( beerDTO.getId() );
        beer.version( beerDTO.getVersion() );
        beer.name( beerDTO.getName() );
        beer.beerStyle( beerDTO.getBeerStyle() );
        beer.upc( beerDTO.getUpc() );
        beer.quantityOnHand( beerDTO.getQuantityOnHand() );
        beer.price( beerDTO.getPrice() );
        beer.createdDate( beerDTO.getCreatedDate() );
        beer.updateDate( beerDTO.getUpdateDate() );

        return beer.build();
    }

    @Override
    public BeerDTO beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDTO.BeerBuilder beerDTO = BeerDTO.builder();

        beerDTO.id( beer.getId() );
        beerDTO.version( beer.getVersion() );
        beerDTO.beerStyle( beer.getBeerStyle() );
        beerDTO.upc( beer.getUpc() );
        beerDTO.quantityOnHand( beer.getQuantityOnHand() );
        beerDTO.price( beer.getPrice() );
        beerDTO.createdDate( beer.getCreatedDate() );
        beerDTO.updateDate( beer.getUpdateDate() );

        return beerDTO.build();
    }
}
