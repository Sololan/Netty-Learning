package netty.bio;

import netty.bio.server.BIOServer;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/25
 */

public class BIOServerBootstrap {
    public static void main(String[] args) throws Exception {
        new BIOServer().start(10010);
    }
}
