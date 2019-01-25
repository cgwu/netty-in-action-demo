#!/usr/bin/env python
#encoding: utf-8
import socket
sock = socket.socket()
sock.connect(("localhost",8888))

ret_bytes = sock.recv(1024)
ret_str = str(ret_bytes, encoding="utf-8")
print("python客户端接收到:",ret_str)

sock.close()
