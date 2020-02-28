package com.grocerygary.app.controllers;

import com.grocerygary.app.services.IngredientsService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GroceryGaryController {

  @Autowired IngredientsService ingredientsService;

  // Todo: Build list of saved items
  @GetMapping("/buildList")
  public void buildList() {}

  @GetMapping("/scrapeRecipe")
  @ResponseBody
  public List<String> scrapeRecipe(@RequestParam Optional<String> url) {
    return Optional.ofNullable(ingredientsService.getIngredients(url.get()))
        .orElse(Collections.emptyList());
  }
}
