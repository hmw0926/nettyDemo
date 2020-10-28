package com.hmw.netty.junior.chapter4.node1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("The time server receive order : " + body + "; the counter is :" + ++counter);
        //Client发送100个"QUERY TIME ORDER"到服务器，但是服务器接收到"QUERY TIME ORDER QUERY TIME ORDER..."，是粘连的结果
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date((System.currentTimeMillis())).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        System.out.println(currentTime);
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
