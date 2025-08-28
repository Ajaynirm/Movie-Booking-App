package com.example.demo.service;

import com.example.demo.dto.SearchResult;
import com.example.demo.dto.ShowDTO;
import com.example.demo.model.Show;
import com.example.demo.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ShowRepository showRepo;

    public List<SearchResult> searchShow(String query){
       List<Show> shows = showRepo.searchShows(query);
       return shows.stream().map(s->
           new SearchResult(s.getId(),s.getMovie().getTitle(),s.getTheatre().getName(),s.getTheatre().getLocation(),s.getShowTime())
       ).toList();
    }

}



