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

package be.darkkraft.transferproxy.network.packet.built;

import be.darkkraft.transferproxy.api.network.packet.Packet;
import be.darkkraft.transferproxy.api.network.packet.built.BuiltPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static be.darkkraft.transferproxy.util.BufUtil.writeVarInt;

public class BuiltPacketImpl implements BuiltPacket {

    private final byte[] data;

    public BuiltPacketImpl(final @NotNull Packet packet) {
        final ByteBuf buf = Unpooled.buffer();
        writeVarInt(buf, packet.getId());
        packet.write(buf);
        buf.capacity(buf.readableBytes());

        this.data = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), this.data);
    }

    public BuiltPacketImpl(final byte[] data) {
        this.data = Objects.requireNonNull(data, "data cannot be null");
    }

    @Override
    public ByteBuf get(final @NotNull ByteBufAllocator allocator) {
        final int length = this.data.length;
        final ByteBuf buf = allocator.buffer(length, length);
        buf.writeBytes(this.data);
        return buf;
    }

}