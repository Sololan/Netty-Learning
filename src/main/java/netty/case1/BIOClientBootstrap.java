package netty.case1;

import netty.case1.client.BIOClient;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/25
 */

public class BIOClientBootstrap {
    public static void main(String[] args) throws Exception {
        new BIOClient().start("127.0.0.1", 10010);
    }
}
