package com.xiaoace.minecraftPingLib;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class McPing {

    static int pingVersion;

    public static String getPing(final String address) throws IOException {
        return getPing(MCPingOptions.builder().hostname(address).build());
    }

    public static String getPing(final MCPingOptions options) throws IOException {

        if (options.getHostname() == null) {
            throw new NullPointerException("Hostname cannot be null");
        }

        try (Socket socket = new Socket()) {
            socket.setSoTimeout(options.getTimeout());
            socket.connect(new InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout());
            try (
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    //> Handshake
                    ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
                    DataOutputStream handshake = new DataOutputStream(handshake_bytes);
            ) {
                handshake.writeByte(0x00);
                writeVarInt(handshake, options.getProtocolVersion()); // 协议号
                writeVarInt(handshake, options.getHostname().length());
                handshake.writeBytes(options.getHostname());
                handshake.writeShort(options.getPort());
                writeVarInt(handshake, 1); // 1为获取状态 2为登录服务器

                writeVarInt(out, handshake_bytes.size()); // Size of packet
                out.write(handshake_bytes.toByteArray());

                //< Status Request
                out.writeByte(0x01); // Size of packet
                out.writeByte(0x00);

                //< Status Response
                // https://wiki.vg/Protocol#Response
                readVarInt(in); // Size
                pingVersion = readVarInt(in);
                int length = readVarInt(in);
                byte[] data = new byte[length];
                in.readFully(data);
                return new String(data, options.getCharset());
            }
        }

    }

    /**
     * varInt 读取函数
     */
    protected static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << (j++ * 7);
            if (j > 5)
                throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 0x80)
                break;
        }
        return i;
    }

    /**
     * varInt 写入函数
     */
    protected static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & ~0x7F) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

}
