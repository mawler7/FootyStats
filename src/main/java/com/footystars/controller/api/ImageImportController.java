package com.footystars.controller.api;

import com.footystars.service.api.ImageFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ImageImportController {

    private final ImageFetcherService imageImportService;

    @GetMapping("/import-images")
    public String importImages() {
        // Wywołujemy metodę importImagesFromCsv, która odczyta lokalny plik CSV
        // oraz pobierze i zapisze obrazki do folderu "src/main/resources/static/images"
        imageImportService.importImagesFromCsv("src/main/resources/photos/photos.csv", "src/main/resources/static/images");
        return "Import started.";
    }
}