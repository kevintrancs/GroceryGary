package com.grocerygary.app.services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientsService {

  @Autowired FoodService foodService;

  WebClient webClient;
  Set<String> foodSet;

  public IngredientsService() {
    webClient = new WebClient();
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);
  }

  public List<String> getIngredients(String url) {
    if (isValidUrl(url)) {
      return fetchIngredients(url);
    }
    return null;
  }

  private boolean isValidUrl(String url) {
    try {
      new URL(Optional.ofNullable(url).orElse("")).toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private List<String> fetchIngredients(String url) {
    HtmlPage htmlPage;
    try {
      htmlPage = webClient.getPage(url);
      webClient.waitForBackgroundJavaScriptStartingBefore(5000);
      return foundIngredientsToSet(parsePage(htmlPage)).stream().collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Set<String> parsePage(HtmlPage htmlPage) {
    // Recipes can either be found in <li> or <label> or <span> tags
    List<DomElement> domElementList =
        Stream.of(
                htmlPage.getElementsByTagName("li"),
                htmlPage.getElementsByTagName("label"),
                htmlPage.getElementsByTagName("span"),
                htmlPage.getElementsByTagName("div"))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    return domElementList.stream()
        .filter(node -> !node.asText().isEmpty())
        .map(line -> line.asText())
        .filter(text -> text.matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*") && text.length() > 3)
        .collect(Collectors.toSet());
  }

  private Set<String> foundIngredientsToSet(Set<String> stringOfIngredientsList) {

    foodSet = foodService.getFoodSet();

    return stringOfIngredientsList.stream()
        .map(String::toLowerCase)
        .map(
            line -> { // Todo: For sure better way to do this, some flatmap filter?
              String found = "";
              for (String s : line.split(" ")) {
                if (foodSet.contains(s)) {
                  found = s;
                }
              }
              return found;
            })
        .filter(item -> item != " ")
        .collect(Collectors.toSet());
  }
}
