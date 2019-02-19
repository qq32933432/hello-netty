package com.bxoon.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;

//    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
//        firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf messgae = null;
        for (int i=0;i<100;i++) {
            messgae = Unpooled.buffer(req.length);
            messgae.writeBytes(req);
            ctx.writeAndFlush(messgae);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is:" + body+";the counter is :"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warning("Unexpected excetion fronm downstream:" + cause.getMessage());
        ctx.close();
    }
}
