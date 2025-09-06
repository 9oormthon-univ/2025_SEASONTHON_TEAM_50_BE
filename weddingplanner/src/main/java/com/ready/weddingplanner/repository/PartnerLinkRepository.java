package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.PartnerLink;
import com.ready.weddingplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerLinkRepository extends JpaRepository<PartnerLink, Long> {
    Optional<PartnerLink> findByUserOrPartner(User user, User partner);
}
