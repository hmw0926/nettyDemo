package com.hmw.netty.junior.chapter5.node2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class FixServerHandler extends ChannelHandlerAdapter {

    static final String ECHO_RESP = "Hi, Lilinfeng. Welcome to Netty.";
    //有连接可以读取
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("Receive from client [" + msg + "]");
        ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_RESP.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        ctx.close();
    }
}
