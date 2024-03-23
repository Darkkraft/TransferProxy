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

package be.darkkraft.transferproxy.api.event;

import be.darkkraft.transferproxy.api.event.listener.EventListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EventManager {

    void call(final @NotNull EventType eventType, final @NotNull Object event);

    <T extends EventListener<?>> void addListener(final @NotNull EventType eventType, final @NotNull T eventListener);

    @Contract("null, _ -> false; _, null -> false; _, _ -> _")
    <T extends EventListener<?>> boolean removeListener(final EventType eventType, final T eventListener);

    @Contract("null -> null; !null -> _")
    <T extends EventListener<?>> T[] getListeners(final EventType eventType);

}