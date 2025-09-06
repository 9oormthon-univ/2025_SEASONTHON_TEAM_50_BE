package com.ready.weddingplanner.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, nullable = false)
	private String category; // STUDIO/DRESS/MAKEUP/HALL/DINNER

	@Column(length = 120, nullable = false)
	private String name;

	private Integer priceMin;
	private Integer priceMax;

	@Column(length = 200)
	private String address;

	@Column(length = 200)
	private String snsUrl;

	@Column(columnDefinition = "TEXT")
	private String summary;

	@ElementCollection
	@CollectionTable(name = "vendor_tags", joinColumns = @JoinColumn(name = "vendor_id"))
	@Column(name = "tag")
	private List<String> tags = new ArrayList<>();

	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VendorImage> images = new ArrayList<>();

	 //연관 편의 메서드
	public void addImage(String url) {
		VendorImage img = new VendorImage(url, this);
		images.add(img);
	}

	// 기본 생성자 & 게터/세터 (롬복 없이 최소)
	protected Vendor() {
	}

	public Vendor(String category, String name, Integer priceMin, Integer priceMax, String address,
		String snsUrl, String summary) {
		this.category = category;
		this.name = name;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.address = address;
		this.snsUrl = snsUrl;
		this.summary = summary;
	}

	public Long getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public Integer getPriceMin() {
		return priceMin;
	}

	public Integer getPriceMax() {
		return priceMax;
	}

	public String getAddress() {
		return address;
	}

	public String getSnsUrl() {
		return snsUrl;
	}

	public String getSummary() {
		return summary;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<VendorImage> getImages() {
		return images;
	}
}