package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.PartnerLink;
import com.ready.weddingplanner.domain.User;
import com.ready.weddingplanner.dto.PartnerDto;
import com.ready.weddingplanner.repository.PartnerLinkRepository;
import com.ready.weddingplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerLinkRepository partnerLinkRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public PartnerLink createPartnerLink(PartnerDto.PartnerRequest request) {
        User currentUser = userService.getMe();
        User partner = userRepository.findByEmail(request.getPartnerEmail())
                .orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        PartnerLink partnerLink = new PartnerLink();
        partnerLink.setUser(currentUser);
        partnerLink.setPartner(partner);
        partnerLink.setWeddingDate(request.getWeddingDate());
        partnerLink.setBudgetAmount(request.getBudgetAmount());

        return partnerLinkRepository.save(partnerLink);
    }

    @Transactional
    public void acceptPartnerLink(Long linkId) {
        User currentUser = userService.getMe();
        PartnerLink partnerLink = partnerLinkRepository.findById(linkId)
                .orElseThrow(() -> new IllegalArgumentException("Link not found"));

        if (!partnerLink.getPartner().equals(currentUser)) {
            throw new IllegalStateException("You are not authorized to accept this link");
        }

        partnerLink.setAccepted(true);
        partnerLinkRepository.save(partnerLink);
    }

    @Transactional(readOnly = true)
    public PartnerLink getPartner() {
        User currentUser = userService.getMe();
        return partnerLinkRepository.findByUserOrPartner(currentUser, currentUser)
                .orElse(null);
    }
}
