package com.hmw.netty.junior.chapter4.node2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
    private int counter;
    private byte[] req;

    public TimeClientHandler(){
        // todo System.getProperty("line.separator") >>> 获取操作系统对应的换行符
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        // todo
    }

    //链路创建成功后
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexcepted exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
