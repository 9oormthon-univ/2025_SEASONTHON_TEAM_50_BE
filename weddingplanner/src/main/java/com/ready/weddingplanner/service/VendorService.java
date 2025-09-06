package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.Vendor;
import com.ready.weddingplanner.dto.VendorDto;
import com.ready.weddingplanner.repository.VendorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VendorService {

	private final VendorRepository repo;

	public VendorService(VendorRepository repo) {
		this.repo = repo;
	}

	public Page<VendorDto> search(String category, String q, Pageable pageable) {
		return repo.search(category, q, pageable).map(VendorDto::from);
	}

	public VendorDto get(Long id) {
		Vendor v = repo.findById(id).orElseThrow();
		return VendorDto.from(v);
	}
}