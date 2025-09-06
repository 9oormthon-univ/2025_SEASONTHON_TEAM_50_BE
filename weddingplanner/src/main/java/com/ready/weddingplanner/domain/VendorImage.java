package com.ready.weddingplanner.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor_images")
public class VendorImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 300, nullable = false)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

	protected VendorImage() {
	}

	public VendorImage(String imageUrl, Vendor vendor) {
		this.imageUrl = imageUrl;
		this.vendor = vendor;
	}

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}