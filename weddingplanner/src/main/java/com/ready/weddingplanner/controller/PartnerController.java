package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.PartnerLink;
import com.ready.weddingplanner.dto.PartnerDto;
import com.ready.weddingplanner.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping
    public ResponseEntity<Void> createPartnerLink(@RequestBody PartnerDto.PartnerRequest request) {
        partnerService.createPartnerLink(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptPartnerLink(@RequestBody PartnerDto.AcceptRequest request) {
        partnerService.acceptPartnerLink(request.getLinkId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PartnerDto.PartnerResponse> getPartner() {
        PartnerLink partnerLink = partnerService.getPartner();
        if (partnerLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PartnerDto.PartnerResponse(partnerLink));
    }
}
