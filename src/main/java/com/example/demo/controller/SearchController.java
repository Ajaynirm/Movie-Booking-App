package com.example.demo.controller;

import com.example.demo.dto.SearchResult;
import com.example.demo.dto.ShowDTO;
import com.example.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/get")
    public ResponseEntity<?> search(@RequestParam String query){
        if(query.trim().isEmpty()) return ResponseEntity.ok(new ArrayList<>());
        List<SearchResult> shows=searchService.searchShow(query);
        return ResponseEntity.ok(shows);
    }

}



