import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.packet.IPPacket;
import io.pkts.packet.Packet;
import io.pkts.packet.TransportPacket;
import io.pkts.protocol.Protocol;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.SimpleTimeZone;

public class NB_UDP_IPchange {
    public BufferedWriter out_NB_IPchange_time;
//    public BufferedWriter out_NB_IPchange_IP;
    public String Server_IP = "172.17.86.131";
    public String Client_IP = "";
    public NB_UDP_IPchange()
    {
        try
        {
            out_NB_IPchange_time = new BufferedWriter(new FileWriter("IPchange_time.txt"));
//            out_NB_IPchange_IP = new BufferedWriter(new FileWriter("IPchange_IP.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void find_IPchange()
    {
        Pcap pcap = null;
        try {
            pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/1000nb/20201211regudp.pcap");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            pcap.loop(new PacketHandler() {
                public String lastIP = null;
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
//                    System.out.println("---------------begin-----------------");
                    IPPacket ipPacket = (IPPacket)packet.getPacket(Protocol.IPv4);
                    String this_pac_IP = ipPacket.getSourceIP();
                    if(!this_pac_IP.equals(Server_IP))
                    {
                        if(!this_pac_IP.equals(Client_IP))
                        {
                            System.out.println("newIP : " + this_pac_IP);
                            Client_IP = this_pac_IP;
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy, M, d, H, m, s, S");
                            long chang_time = ipPacket.getArrivalTime();
                            Date date = new Date(chang_time / 1000);
                            String format_time = "datetime(" + dateFormat.format(date) + ")";
                            System.out.println("format_time : " + format_time);
                            out_NB_IPchange_time.write(format_time + ", ");
                        }
                    }



                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out_NB_IPchange_time.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
