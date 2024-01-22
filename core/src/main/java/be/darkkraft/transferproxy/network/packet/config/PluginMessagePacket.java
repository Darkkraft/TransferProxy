/*
 * MIT License
 *
 * Copyright (c) 2024 Darkkraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package be.darkkraft.transferproxy.network.packet.config;

import be.darkkraft.transferproxy.api.network.connection.PlayerConnection;
import be.darkkraft.transferproxy.api.network.packet.serverbound.ServerboundPacket;
import be.darkkraft.transferproxy.network.packet.config.payload.BrandPayload;
import be.darkkraft.transferproxy.network.packet.config.payload.PayloadData;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static be.darkkraft.transferproxy.util.BufUtil.readString;
import static be.darkkraft.transferproxy.util.BufUtil.writeString;

public record PluginMessagePacket(String channel, @Nullable PayloadData payload) implements ServerboundPacket {

    public static final Map<String, Function<ByteBuf, PayloadData>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("minecraft:brand", BrandPayload::new);
    }

    public static PluginMessagePacket from(final @NotNull ByteBuf buf) {
        final String channel = readString(buf);
        final Function<ByteBuf, PayloadData> function = REGISTRY.get(channel);
        return new PluginMessagePacket(channel, function != null ? function.apply(buf) : null);
    }

    @Override
    public void handle(final @NotNull PlayerConnection connection) {

    }

    @Override
    public void write(final @NotNull ByteBuf buf) {
        writeString(buf, this.channel);
        if (this.payload != null) {
            this.payload.write(buf);
        }
    }

    @Override
    public int getId() {
        return 0x03;
    }

}