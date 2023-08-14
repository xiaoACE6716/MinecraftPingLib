package com.xiaoace.minecraftPingLib;

import lombok.Builder;
import lombok.Getter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Builder
public class MCPingOptions {

    /**
     * Minecraft服务器地址
     */
    @Getter
    private String hostname;

    /**
     * Minecraft服务器端口
     */
    @Getter
    @Builder.Default
    private int port = 25565;

    /**
     * 超时时间
     */
    @Getter
    @Builder.Default
    private int timeout = 10000;

    /**
     * 协议版本
     */
    @Getter
    @Builder.Default
    private int protocolVersion = -1;

    /**
     * 编码
     */
    @Getter
    @Builder.Default
    private Charset charset = StandardCharsets.UTF_8;

}
