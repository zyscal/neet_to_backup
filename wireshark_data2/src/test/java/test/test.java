package test;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        regAnjay reg = new regAnjay();
        killanjay kil = new killanjay();

        for(int i = 0; i < 5100; i++)
        {
            reg.run();
            Thread.sleep(5000);
            kil.run();
            Thread.sleep(1000);
            System.out.println(i);
        }
        //!dhcp && !llmnr && !nbns && !mdns && !dns && !wsp && !ntp && ip.addr== 192.168.1.54 && !snmp && !manolito && ip.dst  != 255.255.255.255 && !ssdp&& !enip&&!pn_io && !hcrt && udp.port == 3323
    }
}
