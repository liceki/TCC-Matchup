package com.matchup.controller;

import com.matchup.dto.*;
import com.matchup.model.Interest;
import com.matchup.model.insterest.Company;
import com.matchup.model.insterest.Genre;
import com.matchup.model.insterest.Platform;
import com.matchup.model.insterest.SubGenre;
import com.matchup.service.FilterSpecificationService;
import com.matchup.service.InterestService;
import com.matchup.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/interests")
public class InterestController {

    private final InterestService interestService;
    private final UserService userService;

    private final FilterSpecificationService filterSpecificationService;

    @Autowired
    public InterestController(InterestService interestService, FilterSpecificationService filterSpecificationService, UserService userService) {
        this.interestService = interestService;
        this.filterSpecificationService = filterSpecificationService;
        this.userService = userService;
    }

    @PostMapping("/add/{interestId}")
    public ResponseEntity<Boolean> linkInterestToUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("interestId") Long interestId) {
        return new ResponseEntity<>(userService.linkInterestToUser(userDetails.getUsername(), interestId), HttpStatus.OK);
    }

    @PostMapping("/remove/{interestId}")
    public ResponseEntity<Boolean> unlinkInterestToUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("interestId") Long interestId) {
        return new ResponseEntity<>(userService.unlinkInterestToUser(userDetails.getUsername(), interestId), HttpStatus.OK);
    }

    /*@PostMapping("/specification")
    @PostAuthorize("true")
    public ResponseEntity<List<Interest>> searchInterest(@RequestBody RequestDto requestDto) {
        return new ResponseEntity<>(interestService.getInterestsBySpecification(requestDto), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("/get-all-dependencies")
    public ResponseEntity<InterestDependenciesDto> getInterestDependencies() {
        return new ResponseEntity<>(interestService.getInterestsDependencies(), HttpStatus.ACCEPTED);
    }

    /**/

    @PostMapping("/register/interest")
    @PostAuthorize("true")
    public ResponseEntity<Interest> registerInterest(@ModelAttribute InterestDto interestDto) {
        System.out.println(interestDto.getName());
        return new ResponseEntity<>(interestService.saveInterest(interestDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/company")
    @PostAuthorize("true")
    public ResponseEntity<Company> registerCompany(@RequestBody Company company) {
        return new ResponseEntity<>(interestService.saveCompany(company), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/genre")
    @PostAuthorize("true")
    public ResponseEntity<Genre> registerGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(interestService.saveGenre(genre), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/subgenre")
    @PostAuthorize("true")
    public ResponseEntity<SubGenre> registerSubGenre(@RequestBody SubGenre subGenre) {
        return new ResponseEntity<>(interestService.saveSubGenre(subGenre), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/platform")
    @PostAuthorize("true")
    public ResponseEntity<Platform> registerPlatform(@RequestBody Platform platform) {
        return new ResponseEntity<>(interestService.savePlatform(platform), HttpStatus.ACCEPTED);
    }

    /*@PostMapping("/get-all-filtered")
    @PostAuthorize("true")
    public ResponseEntity<Specification> getAllFiltered(@RequestBody List<SearchRequestDto> searchRequestDtos) {
        return new ResponseEntity<>(filterSpecificationService.getSearchSpecification(searchRequestDtos), HttpStatus.ACCEPTED);
    }*/

    @PostMapping("/get-by-specifications")
    public Page<Interest> getInterestsBySpecificationWithPagination(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestBody RequestDto requestsDto) {
        System.out.println(userDetails.getUsername());
        return interestService.getInterestsBySpecificationWithPagination(userDetails, requestsDto.getSearchRequestDtos(), page, size, orderBy, direction);
    }

    @PatchMapping("/update-images")
    public void updateInterestImagesById(@AuthenticationPrincipal UserDetails userDetails,
                                         @ModelAttribute InterestImagesToUpdate interestImagesToUpdate) {
        interestService.updateInterestImagesById(userDetails.getUsername(), interestImagesToUpdate.getInterestId(), interestImagesToUpdate.getImageList());
    }
}
