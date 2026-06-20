package com.steph.search;

import com.steph.post.PostController;
import com.steph.search.DTOs.SearchResultsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public SearchResultsDTO search(@RequestParam String query) {
        return searchService.search(query);
    }

}
