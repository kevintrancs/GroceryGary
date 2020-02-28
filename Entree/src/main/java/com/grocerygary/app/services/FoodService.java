package com.grocerygary.app.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@Slf4j
@Getter
public class FoodService {

  // Note: Food data from corpora https://github.com/dariusk/corpora/tree/master/data/foods
  Set<String> foodSet;

  @Getter(AccessLevel.PRIVATE)
  ObjectMapper objectMapper;

  @Getter(AccessLevel.PRIVATE)
  File file;

  @Getter(AccessLevel.PRIVATE)
  InputStream inputStream;

  @Getter(AccessLevel.PRIVATE)
  JsonNode jsonNode;

  public FoodService() throws IOException {
    file = ResourceUtils.getFile("classpath:foods/foods.json");
    objectMapper = new ObjectMapper();
    inputStream = new FileInputStream(file);
    foodSet = getFoods(inputStream);
  }

  private Set<String> getFoods(InputStream inputStream) {
    try {
      jsonNode = objectMapper.readTree(inputStream);
      return objectMapper.convertValue(jsonNode.get("foods"), new TypeReference<Set<String>>() {})
          .stream()
          .map(s -> s.toLowerCase())
          .collect(Collectors.toSet());
    } catch (Exception e) {
      log.error("Failed to readTree: {}", e);
    }
    return null;
  }
}
