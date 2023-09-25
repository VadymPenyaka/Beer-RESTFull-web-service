package penyaka.petproject.spring_rest_web_mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.service.BeerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;
    public final static String BEER_PATH = "/api/v1/beer";
    public final static String BEER_PATH_ID = BEER_PATH + "/{id}";

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteByID (@PathVariable("id") UUID id) {
        beerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById (@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        beerService.updateById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchById (@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        beerService.patchById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException () {
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost (@RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.save(beer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("UUID", savedBeer.getId().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
    @RequestMapping(BEER_PATH_ID)
    public BeerDTO gerBeerById (@PathVariable("id") UUID id) {
        log.debug("BeerContoller123 y98");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers () {
        return beerService.getAllBeers();
    }
}
