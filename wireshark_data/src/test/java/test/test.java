package test;

import java.io.IOException;

public class test {
    public static int times = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        regAnjay reg = new regAnjay();
        killanjay kil = new killanjay();

        for(int i = 0; i < 288; i++)
        {
            test.times += 1;
            reg.run();
            Thread.sleep(1000 * 60 * 5);
            kil.run();
            Thread.sleep(1000);
            System.out.println(i);
        }
//        !dhcp && !llmnr && !nbns && !mdns && !dns && !wsp && !ntp && ip.addr== 192.168.1.54 && !snmp && !manolito && ip.dst  != 255.255.255.255 && !ssdp


    }
}
