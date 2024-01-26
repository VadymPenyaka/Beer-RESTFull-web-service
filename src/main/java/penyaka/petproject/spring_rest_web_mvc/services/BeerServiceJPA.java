package penyaka.petproject.spring_rest_web_mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.mappers.BeerMapper;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;
import penyaka.petproject.spring_rest_web_mvc.repositories.BeerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getAllBeers(String beerName, BeerStyle beerStyle, Boolean getAmount) {
        List<Beer> beerList;

        if(StringUtils.hasText(beerName) && beerStyle == null)//query bu name
            beerList = getBeersByName(beerName);
        else if (!StringUtils.hasText(beerName) && beerStyle != null)//query by style
            beerList = getBeersByStyle(beerStyle);
        else if (StringUtils.hasText(beerName) && beerStyle != null)//query by style and name
            beerList = getBearsByNameAndSyle(beerName, beerStyle);
        else//get all
            beerList = beerRepository.findAll();

        if (getAmount != null && !getAmount) {//set null to amount of all beers
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList.stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    private List<Beer> getBearsByNameAndSyle(String beerName, BeerStyle beerStyle) {
        return beerRepository.findAllByNameLikeIgnoreCaseAndStyle("%" + beerName + "%", beerStyle);
    }

    public List<Beer> getBeersByStyle(BeerStyle beerStyle) {
        return beerRepository.findAllByStyle(beerStyle);
    }

    public List<Beer> getBeersByName (String beerName) {
        return beerRepository.findAllByNameLikeIgnoreCase("%"+beerName+"%");
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setName(beer.getName());
            foundBeer.setStyle(beer.getStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getName())){
                foundBeer.setName(beer.getName());
            }
            if (beer.getStyle() != null){
                foundBeer.setStyle(beer.getStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
