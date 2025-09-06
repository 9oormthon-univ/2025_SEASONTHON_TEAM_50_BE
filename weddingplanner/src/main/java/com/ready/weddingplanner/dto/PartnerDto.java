package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.PartnerLink;
import com.ready.weddingplanner.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class PartnerDto {

    @Data
    @NoArgsConstructor
    public static class PartnerRequest {
        private String partnerEmail;
        private LocalDate weddingDate;
        private Long budgetAmount;
    }

    @Data
    @NoArgsConstructor
    public static class AcceptRequest {
        private Long linkId;
    }

    @Data
    @NoArgsConstructor
    public static class PartnerResponse {
        private Long linkId;
        private PartnerInfo partner;
        private boolean accepted;
        private LocalDate weddingDate;
        private Long budgetAmount;

        public PartnerResponse(PartnerLink link) {
            this.linkId = link.getId();
            this.partner = new PartnerInfo(link.getPartner());
            this.accepted = link.isAccepted();
            this.weddingDate = link.getWeddingDate();
            this.budgetAmount = link.getBudgetAmount();
        }
    }

    @Data
    @NoArgsConstructor
    public static class PartnerInfo {
        private Long id;
        private String email;
        private String name;

        public PartnerInfo(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
        }
    }
}
