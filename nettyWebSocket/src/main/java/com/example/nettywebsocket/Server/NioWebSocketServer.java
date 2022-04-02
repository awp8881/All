package com.example.nettywebsocket.Server;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NioWebSocketServer {

    private  final static Log logger= LogFactory.get(NioWebSocketServer.class);
    private void init(){
        logger.info("正在启动websocket服务器");
        NioEventLoopGroup boss=new NioEventLoopGroup();
        NioEventLoopGroup work=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new NioWebSocketChannelInitializer());
            Channel channel = bootstrap.bind(8081).sync().channel();
            logger.info("webSocket服务器启动成功："+channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("运行出错："+e);
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("websocket服务器已关闭");
        }
    }

}
