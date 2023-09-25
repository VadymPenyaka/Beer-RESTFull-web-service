package penyaka.petproject.spring_rest_web_mvc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDTO> beerMap;

    @Override
    public List<BeerDTO> getAllBeers () {
        return new ArrayList<>(beerMap.values());
    }

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder().
                id(UUID.randomUUID())
                .version(1)
                .beerName("1")
                .upc("1")
                .createdDate(LocalDateTime.now())
                .price(new BigDecimal(123))
                .beerStyle(BeerStyle.PALE_ALE)
                .quantityOnHand(1)
                .updateDate(LocalDateTime.now())
                .build() ;

        BeerDTO beer2 = BeerDTO.builder().
                id(UUID.randomUUID())
                .version(1)
                .beerName("2")
                .upc("2")
                .createdDate(LocalDateTime.now())
                .price(new BigDecimal(321))
                .beerStyle(BeerStyle.PALE_ALE)
                .quantityOnHand(1)
                .updateDate(LocalDateTime.now())
                .build() ;

        BeerDTO beer3 = BeerDTO.builder().
                id(UUID.randomUUID())
                .version(1)
                .beerName("3")
                .upc("4")
                .createdDate(LocalDateTime.now())
                .price(new BigDecimal(321))
                .beerStyle(BeerStyle.PALE_ALE)
                .quantityOnHand(1)
                .updateDate(LocalDateTime.now())
                .build() ;

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Message from BeerService");
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO save (BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(beer.getVersion())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public void updateById(UUID id, BeerDTO beer) {
        BeerDTO existing = beerMap.get(id);

        existing.setName(beer.getName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setCreatedDate(beer.getCreatedDate());
        existing.setUpdateDate(LocalDateTime.now());
        existing.setVersion(beer.getVersion());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID id) {
            beerMap.remove(id);
    }

    @Override
    public void patchById(UUID id, BeerDTO beer) {
        BeerDTO existing = beerMap.get(id);

        if (StringUtils.hasText(beer.getName())){
            existing.setName(beer.getName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null){
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
    }


}
