package com.example.startlight.starList.controller;

import com.example.startlight.starList.dto.StarListCreateRequest;
import com.example.startlight.starList.dto.StarListCreateResponse;
import com.example.startlight.starList.dto.StarListRepDto;
import com.example.startlight.starList.service.StarListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/star")
public class StarListController {
    private final StarListService starListService;

    @PostMapping("/create")
    public ResponseEntity<StarListCreateResponse> createStarList(@RequestBody StarListCreateRequest request) {
        List<StarListRepDto> list = starListService.createList(request.getPetId(), request.getStarList());
        StarListCreateResponse response = StarListCreateResponse.builder()
                .petId(request.getPetId())
                .starList(list).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getList")
    public ResponseEntity<StarListCreateResponse> getList(@RequestParam Long petId) {
        List<StarListRepDto> list = starListService.getList(petId);
        StarListCreateResponse response = StarListCreateResponse.builder()
                .petId(petId)
                .starList(list).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
