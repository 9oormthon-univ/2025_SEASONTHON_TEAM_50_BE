package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.Vendor;
import java.util.List;

public class VendorDto {
	public Long id;
	public String category;
	public String name;
	public Integer priceMin;
	public Integer priceMax;
	public String address;
	public String snsUrl;
	public String summary;
	public List<String> tags;
	public List<String> images;

	public static VendorDto from(Vendor v) {
		VendorDto d = new VendorDto();
		d.id = v.getId();
		d.category = v.getCategory();
		d.name = v.getName();
		d.priceMin = v.getPriceMin();
		d.priceMax = v.getPriceMax();
		d.address = v.getAddress();
		d.snsUrl = v.getSnsUrl();
		d.summary = v.getSummary();
		d.tags = v.getTags();
		d.images = v.getImages().stream().map(img -> img.getImageUrl()).toList();
		return d;
	}
}