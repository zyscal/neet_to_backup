//########################
import io.pkts.*;
import io.pkts.buffer.Buffer;
import io.pkts.buffer.ByteBuffer;
import io.pkts.packet.Packet;
import io.pkts.protocol.Protocol;
import io.pkts.packet.TransportPacket;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class data_analy {

    public static void main(String[] args) {
//        NB_UDP_REG_RTT nb_udp_reg_rtt = new NB_UDP_REG_RTT();
//        nb_udp_reg_rtt.RTT_NB_1000_reg_UDP_CoAP();

//        NB_UDP_REG_UP_DOWN nb_udp_reg_up_down = new NB_UDP_REG_UP_DOWN();
//        nb_udp_reg_up_down.Up_Down_delay();

        NB_UDP_IPchange nb_udp_iPchange = new NB_UDP_IPchange();
        nb_udp_iPchange.find_IPchange();

    }

}
