package penyaka.petproject.spring_rest_web_mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.mappers.BeerMapper;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;
import penyaka.petproject.spring_rest_web_mvc.repositories.BeerRepository;
import org.springframework.data.domain.Pageable;
import penyaka.petproject.spring_rest_web_mvc.util.PagingUtil;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<BeerDTO> getAllBeers(String beerName, BeerStyle beerStyle, Boolean getAmount, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PagingUtil.buildPageRequest(pageNumber, pageSize, "name");

        Page<Beer> beerPage;

        if(StringUtils.hasText(beerName) && beerStyle == null)//query bu name
            beerPage = getBeersByName(beerName, pageRequest);
        else if (!StringUtils.hasText(beerName) && beerStyle != null)//query by style
            beerPage = getBeersByStyle(beerStyle, pageRequest);
        else if (StringUtils.hasText(beerName) && beerStyle != null)//query by style and name
            beerPage = getBearsByNameAndStyle(beerName, beerStyle, pageRequest);
        else//get all
            beerPage = beerRepository.findAll(pageRequest);

        if (getAmount != null && !getAmount) {//set null to amount of all beers
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    private Page<Beer> getBearsByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByNameLikeIgnoreCaseAndStyle("%" + beerName + "%", beerStyle, pageable);
    }

    public Page<Beer> getBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByStyle(beerStyle, pageable);
    }

    public Page<Beer> getBeersByName (String beerName, Pageable pageable) {
        return beerRepository.findAllByNameLikeIgnoreCase("%"+beerName+"%", pageable);
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
            foundBeer.setVersion(beer.getVersion());
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
