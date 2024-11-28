package com.cuong02n.aimsbackend.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.net.http.HttpClient;
import java.util.Map;


@Builder
@Setter
@Getter
public class Request {
    @Singular
    public Map<String,String> headers;
    @Singular
    public Map<String,String> params;
    public Object body;
    public String url;

}
