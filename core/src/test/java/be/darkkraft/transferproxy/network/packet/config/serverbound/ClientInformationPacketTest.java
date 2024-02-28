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

package be.darkkraft.transferproxy.network.packet.config.serverbound;

import be.darkkraft.transferproxy.api.profile.ChatVisibility;
import be.darkkraft.transferproxy.api.profile.ClientInformation;
import be.darkkraft.transferproxy.api.profile.MainHand;
import be.darkkraft.transferproxy.network.packet.PacketTestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class ClientInformationPacketTest extends PacketTestBase {

    @ParameterizedTest
    @MethodSource("generateInformation")
    void testWriteReadConsistency(final ClientInformation information) {
        this.test(new ClientInformationPacket(information), ClientInformationPacket::new);
    }

    public static Stream<ClientInformation> generateInformation() {
        return Stream.of(ChatVisibility.values())
                .flatMap(chatVisibility -> Stream.of(MainHand.values())
                        .flatMap(mainHand -> IntStream.range(0, 16)
                                .mapToObj(i -> ClientInformation.create("en_US",
                                        (byte) 1,
                                        chatVisibility,
                                        (i & 1) != 0,
                                        (byte) 0,
                                        mainHand,
                                        (i & 2) != 0,
                                        (i & 4) != 0))));
    }


}