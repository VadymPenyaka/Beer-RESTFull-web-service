package penyaka.petproject.spring_rest_web_mvc.services;

import penyaka.petproject.spring_rest_web_mvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCSV(File csvFile);

}
