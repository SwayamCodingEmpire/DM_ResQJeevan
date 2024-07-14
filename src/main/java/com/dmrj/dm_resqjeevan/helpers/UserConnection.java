package com.dmrj.dm_resqjeevan.helpers;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserConnection {
    private String connectionUsername;
    private String convId;
    private Integer unSeen;
    private Boolean isOnline;
}
