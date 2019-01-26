netty集成messagePack序列化框架
https://blog.csdn.net/qq_34448345/article/details/78888632

[netty]--最通用TCP黏包解决方案：LengthFieldBasedFrameDecoder和LengthFieldPrepender
https://blog.csdn.net/u010853261/article/details/55803933

Netty in Action: P48.
Netty’s Channel implementations are thread-safe, so you can store a reference to a Channel
and use it whenever you need to write something to the remote peer, even when many threads are in use.
The following listing shows a simple example of writing with multiple threads. Note that the messages
are guaranteed to be sent in order.

