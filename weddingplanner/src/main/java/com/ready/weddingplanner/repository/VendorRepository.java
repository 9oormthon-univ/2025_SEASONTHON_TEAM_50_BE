package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.Vendor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	@Query("""
        select v from Vendor v
        where (:category is null or v.category = :category)
          and (
            :q is null
            or lower(v.name) like lower(concat('%', :q, '%'))
            or lower(v.summary) like lower(concat('%', :q, '%'))
          )
    """)


	Page<Vendor> search(@Param("category") String category,
		@Param("q") String q,
		Pageable pageable);
}