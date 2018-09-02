import org.it611.das.util.SocketClient;

import java.io.IOException;

public class SocketTest {

    public static void main(String[] args) throws IOException {
        String ret = SocketClient.send("fdsfdsf#/root/桌面/最终数据/身份证/戴旭320923199610101212.jpg#IDcard");
        System.out.println(ret);


    }
}
