package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

@Builder
public record AdmissionDto(
        Long id,
        String application,
        Boolean approval,
        Long teamId,
        UserAccountDto userAccountDto
) {

    public static AdmissionDto from(Admission entity) {
        return AdmissionDto.builder()
                .id(entity.getId())
                .application(entity.getApplication())
                .approval(entity.getApproval())
                .teamId(entity.getTeam().getId())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .build();
    }

    public Admission toEntity(UserAccount userAccount, Team team) {
        return Admission.builder()
                .application(application)
                .approval(approval)
                .userAccount(userAccount)
                .team(team)
                .build();
    }

    public record ApplyRequest(
            String application
    ) {
        public AdmissionDto toDto() {
            return AdmissionDto.builder()
                    .application(application)
                    .build();
        }
    }
}