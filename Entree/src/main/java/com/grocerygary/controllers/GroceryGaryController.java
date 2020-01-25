package com.grocerygary.controllers;

import com.grocerygary.models.Ingredient;
import com.grocerygary.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GroceryGaryController {

    @Autowired
    IngredientsService ingredientsService;

    @GetMapping("/buildList")
    public void buildList() {
    }

    @GetMapping("/scrapeRecipe")
    @ResponseBody
    public List<Ingredient> scrapeRecipe(@RequestParam Optional<String> url){
        List<Ingredient> list = new ArrayList<>();
        return list;
    }


}
