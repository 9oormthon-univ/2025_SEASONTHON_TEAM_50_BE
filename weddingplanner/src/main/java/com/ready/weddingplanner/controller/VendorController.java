package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.dto.VendorDto;
import com.ready.weddingplanner.service.VendorService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	private final VendorService service;
	public VendorController(VendorService service) { this.service = service; }

	@GetMapping
	public Page<VendorDto> list(
		@RequestParam(required = false) String category,
		@RequestParam(required = false) String q,
		@PageableDefault(size=20, sort="priceMin", direction=Sort.Direction.ASC) Pageable pageable
	) {
		return service.search(category, q, pageable);
	}

	@GetMapping("/{id}")
	public VendorDto detail(@PathVariable Long id) { return service.get(id); }
}