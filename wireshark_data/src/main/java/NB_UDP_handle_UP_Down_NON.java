import Simple_class.CoAP_mes;
import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TransportPacket;
import io.pkts.protocol.Protocol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NB_UDP_handle_UP_Down_NON {
    /**
     * considered of the use of retransmission, we split the CON-ACK to CON and ACK, in this way ,we can develop a general method to handle all CON ,ACK or NON message.
     * for CON or ACK, there are three possible forms :
     * the first, without package loss, ONE MID and TOKEN include ONE CON or ACK message. we can simplely calculate the delay of NB-iot.
     * the second, with retransmission, the CON message lost once or more times, and the sender message is more than the receiver, we ignore this situation.
     * the last, with retransmission, without packge loss, we calculate each pair of CON or ACK or NON one-to-one
     */
    public String sender_file;
    public String reciver_file;
    public BufferedWriter delay_datetime;
    public BufferedWriter delay_value;
    public BufferedWriter packet_loss;
    public BufferedWriter IP_change_datetime;
    public Integer[] num_of_sender = new Integer[1];
    public Integer[] num_of_receiver = new Integer[1];
    ArrayList<CoAP_mes> Sender_list = new ArrayList<CoAP_mes>();
    ArrayList<CoAP_mes> Receiver_list = new ArrayList<CoAP_mes>();
    ArrayList<Integer> delay_list = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
    HashMap<String, Integer> wait_to_match = new HashMap<>();
    public NB_UDP_handle_UP_Down_NON(String root_name, String sender_file, String reciver_file){
        this.sender_file = sender_file;
        this.reciver_file = reciver_file;
        String delay_datetime = root_name + "_datetime.txt";
        String delay_value = root_name + "_delay.txt";
        String packet_loss = root_name + "_loss.txt";
        String IP_change_datetime = root_name + "_IPchange.txt";
        String UL_DL_NON_LOG = root_name + "_LOG.txt";
        try {
            this.delay_datetime = new BufferedWriter(new FileWriter(delay_datetime));
            this.delay_value = new BufferedWriter(new FileWriter(delay_value));
            this.packet_loss = new BufferedWriter(new FileWriter(packet_loss));
            this.IP_change_datetime = new BufferedWriter(new FileWriter(IP_change_datetime));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            System.setOut(new PrintStream(UL_DL_NON_LOG));
        }catch (Exception e)
        {}
        num_of_sender[0] = 0;
        num_of_receiver[0] = 0;
    }
    public void UP_DOWN_NON_delay() {

        handle_sender();
        handle_receiver();

        int sum_delay = 0;
        int SD_tem = 0;
        int big_delay = 0;
        for(int i : delay_list)
        {
            sum_delay += i;
            if(i > 10000)
            {
                System.out.println(i);
                big_delay++;
            }
        }
        float AVG_delay = (float)sum_delay / delay_list.size();
        for(int i : delay_list)
        {
            SD_tem += (i - AVG_delay) * (i - AVG_delay);
        }
        double SD = Math.sqrt(SD_tem / delay_list.size());
        System.out.println("AVG() delay : " + AVG_delay);
        System.out.println("SD : " + SD);
        System.out.println("num of big_delay : " + big_delay);
        System.out.println("big_delay : " + (float)big_delay / delay_list.size());
        System.out.println("loss : " + (float)(num_of_sender[0] - num_of_receiver[0]) / num_of_sender[0]);

        for(int i = 0; i < Sender_list.size(); i++)
        {
            Date newdata = new Date(Sender_list.get(i).getArrive_date() / 1000);
            String datatowrite = formatter.format(newdata);
            try
            {
                packet_loss.write(datatowrite + "\n");
            }catch (Exception e)
            {

            }
        }


        try {
            delay_datetime.close();
            delay_value.close();
            packet_loss.close();
            IP_change_datetime.close();
        }catch (Exception e)
        {

        }
    }
    public void handle_sender()
    {
        try {

            Pcap handle_sender = Pcap.openStream(this.sender_file);
            handle_sender.loop(new PacketHandler() {

                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket) packet.getPacket(Protocol.UDP);
                    if(packet.hasProtocol(Protocol.UDP))
                    {
                        num_of_sender[0]++;
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[12];
                        Coap.getBytes(Coapheader);
                        int mid = CoAP_mes.GetMID(Coapheader);
                        int type = CoAP_mes.GetType(Coapheader);
                        byte[] Token = CoAP_mes.GetToken(Coapheader);
                        String KEY = CoAP_mes.make_KEY(CoAP_mes.GetMIDbyByte(Coapheader), Token);
                        CoAP_mes sender_message = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid, Token, KEY);
                        if(wait_to_match.get(KEY) == null)
                        {
                            wait_to_match.put(KEY, 1);
                            Sender_list.add(sender_message);
                        }
                        else{
                            int value = wait_to_match.get(KEY);
                            wait_to_match.put(KEY, value + 1);
                        }
                    }

                    return true;
                }
            });
        }catch (Exception e)
        {

        }

    }
    public void handle_receiver()
    {
        System.out.println("size of sender : " + Sender_list.size());
        System.out.println("num_of_sender : " + num_of_sender[0]);
        try {
            Pcap handle_receiver = Pcap.openStream(this.reciver_file);
            handle_receiver.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket) packet.getPacket(Protocol.UDP);
                    if(packet.hasProtocol(Protocol.UDP))
                    {
                        num_of_receiver[0]++;
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[12];
                        Coap.getBytes(Coapheader);
                        int mid = CoAP_mes.GetMID(Coapheader);
                        int type = CoAP_mes.GetType(Coapheader);
                        byte[] Token = CoAP_mes.GetToken(Coapheader);
                        String KEY = CoAP_mes.make_KEY(CoAP_mes.GetMIDbyByte(Coapheader), Token);
                        CoAP_mes sender_message = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid, Token, KEY);
                        if(wait_to_match.get(KEY) != null)
                        {
                            Integer value = wait_to_match.get(KEY);
                            Receiver_list.add(sender_message);
                            if(value == 1)
                            {
                                match_Sender_Receiver(Sender_list, Receiver_list, KEY);
                                wait_to_match.remove(KEY);
                            }
                            else
                            {

                                wait_to_match.put(KEY, value - 1);
                            }
                        }else {
                            System.out.println("<<<<<<<<something wronge");
//                            return false;
                        }
                    }


                    return true;
                }
                void match_Sender_Receiver(ArrayList<CoAP_mes> Sender_list, ArrayList<CoAP_mes> Receiver_list, String KEY) throws IOException {

                    int j = 0;
                    int count = 0;
                    for(int i = 0; i < Sender_list.size(); i++)
                    {
                        if (Sender_list.get(i).getMid_string().equals(KEY))
                        {
                            count++;
                            for(;j < Receiver_list.size(); j++)
                            {
                                if(Receiver_list.get(j).getMid_string().equals(KEY))
                                {
//                                    System.out.println();
//                                    System.out.println(Sender_list.get(i).getMid_string());
//                                    System.out.println("CON : " + formatter.format(Sender_list.get(i).getArrive_date() / 1000));
//                                    System.out.println("ACK : " + formatter.format(Receiver_list.get(j).getArrive_date() / 1000));
                                    int dif = CoAP_mes.Get_diff_ms(Receiver_list.get(j).getArrive_date(), Sender_list.get(i).getArrive_date());
                                    String RTT =  dif + ",";
                                    delay_value.write(RTT);
                                    delay_list.add(dif);
                                    if(dif > 10000)
                                    {
                                        System.out.println("the abnormal delay : " + dif);
                                        System.out.println("the abnormal send_time : " + formatter.format(Sender_list.get(i).getArrive_date() / 1000));
                                        System.out.println("the abnormal recv_time : " + formatter.format(Receiver_list.get(j).getArrive_date() / 1000));
                                        System.out.println("----------------");
                                    }
                                    Date newdata = new Date(Sender_list.get(i).getArrive_date() / 1000);
                                    String datatowrite = formatter.format(newdata);
                                    delay_datetime.write(datatowrite + "\n");
                                    Sender_list.remove(i);
                                    i--;
                                    Receiver_list.remove(j);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }catch (Exception e)
        {

        }
    }
}
