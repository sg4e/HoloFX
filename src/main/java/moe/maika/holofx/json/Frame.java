/*
 * The MIT License (MIT)
 * Copyright (c) 2024 sg4e
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package moe.maika.holofx.json;

import java.time.Instant;

public record Frame(
    Instant availableAt,
    Channel channel,
    long duration,
    String id,
    int liveViewers,
    Instant publishedAt,
    Instant startActual,
    Instant startScheduled,
    Status status,
    String title,
    String topicId,
    Type type
    ) {
    /*
    {

    "available_at": "2021-05-13T19:59:59.000Z",
    "channel": 

    {
        "english_name": "Ninomae Ina’nis",
        "id": "UCMwGHR0BTZuLsmjY_NT5Pwg",
        "name": "Ninomae Ina'nis Ch. hololive-EN",
        "photo": "https://yt3.ggpht.com/ytc/AAUvwng37V0l-NwF3bu7QA4XmOP5EZFwk5zJE-78OHP9=s800-c-k-c0x00ffffff-no-rj",
        "type": "vtuber"
    },
    "duration": 0,
    "id": "89rZZO10m0k",
    "live_viewers": 12685,
    "published_at": "2021-05-13T13:47:09.000Z",
    "start_actual": "2021-05-13T19:59:59.000Z",
    "start_scheduled": "2021-05-13T20:00:00.000Z",
    "status": "live",
    "title": "【Minecraft】 waHHHH",
    "topic_id": "minecraft",
    "type": "stream"

    },
     */
    
}
