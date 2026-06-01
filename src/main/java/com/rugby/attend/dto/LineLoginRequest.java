package com.rugby.attend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineLoginRequest {

    private String lineUserId;

    private String displayName;

    private String pictureUrl;
}
