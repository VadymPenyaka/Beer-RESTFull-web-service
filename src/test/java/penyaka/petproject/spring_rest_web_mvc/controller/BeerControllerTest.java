package penyaka.petproject.spring_rest_web_mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.service.BeerService;
import penyaka.petproject.spring_rest_web_mvc.service.BeerServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BeerService beerService;
    @Autowired
    ObjectMapper objectMapper;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;
    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();


    @Test
    void createBeerWithNullNameTest () throws Exception {
        BeerDTO beer = BeerDTO.builder().build();

        given(beerService.save(any(BeerDTO.class))).willReturn(beerServiceImpl.getAllBeers().get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    void gerBeerByIdNotFoundTest () throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBeertest () throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);

        given(beerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
    }

//    @Test
//    void patchTest() throws Exception {
//        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);
//        System.out.println(beer);
//        Map<String, Object> beerMap = new HashMap<>();
////        BeerDTO updatedBeer = beer;
////        updatedBeer.setName("New name");
//        beerMap.put("name", "updatedBeer");
//
//        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(beerMap)))
//                .andExpect(status().isNoContent());
//
//        verify(beerService).patchById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
//
//        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
//        assertThat(beerArgumentCaptor.getValue().getName()).isEqualTo(beerMap.get("name"));
//    }

    @Test
    void testListBeers () throws Exception {
        given(beerService.getAllBeers()).willReturn(beerServiceImpl.getAllBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void gerBeerByIdTest() throws Exception {
        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testBeer.getName())));
    }

    @Test
    void createBeerTest () throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);
        given(beerService.save(any(BeerDTO.class))).willReturn(beerServiceImpl.getAllBeers().get(1));
        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateBeerTest () throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);

        given(beerService.updateById(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void updateBeerTestBlankName () throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);
        beer.setName("");
        given(beerService.updateById(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));
    }

}