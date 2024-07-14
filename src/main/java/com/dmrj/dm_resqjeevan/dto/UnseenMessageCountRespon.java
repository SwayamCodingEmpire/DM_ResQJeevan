package com.dmrj.dm_resqjeevan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UnseenMessageCountRespon {
    private UUID fromUser;
    private long count;
}
