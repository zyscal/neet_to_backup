
import Simple_class.CAL_RTT_PARA;
import Simple_class.CoAP_mes;
import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TransportPacket;
import io.pkts.protocol.Protocol;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class NB_CoAP_RTT {
    private BufferedWriter out_RTT_lost;
    private BufferedWriter out_datetime;
    private BufferedWriter out_RTT;
    private BufferedWriter out_retransmission_datetime;
    private CAL_RTT_PARA cal_rtt_para;
    private String Pcap_open_stream;
    private String Pcap_log;
    private PrintStream printStream;
    /**
     * this method is used to handled one NB-iot device, and faced to "CON-ACK" type datas, the out put of the method is CON-ACK's RTT and the RTT's datetimes.
     * beyond the RTTs and its datetime, we can also get the datetime when the CON is lost.
     * considered the retransmission, we may meet THREE kinds type of data:
     * the first, the normal kind, there is a CON message, and its ACK message arrived behind.
     * the second, the CON message lost ONCE or more times, the num of ACKs is less than CON's, we don't know whick CON message is exactly lost, so we ignore this transmission.
     * the last, the CON message overs time ONCE or more times, ACKs do not arrived immediately, but burst. the CONs and its ACKs is one-to-one. we calculate the RTTs in order.
     */

    /**
     * there are two ARRAYLIST: CON_list and ACK_list;
     * one HASHMAP :  wait_to_match
     * we look through all messages, put CONs or ACKs into their own list.
     * when put a CON message,wait_to_match's corresponding KEY ++,all_retransmission++(if the MAP does not contain the key, create a new node and make the value 1);
     * when put a ACK message,find the corresponding KEY, and get its value, if the current value == 1,match the CON-ACK and remove the key;else make its value--.
     */
    /**
     *
     */



    private NB_CoAP_RTT(String out_RTT, String out_RTT_lost, String out_datetime, String Pcap_open_stream, String Pcap_log)
    {
        try
        {
            this.out_RTT = new BufferedWriter(new FileWriter(out_RTT));
            this.out_RTT_lost = new BufferedWriter(new FileWriter(out_RTT_lost));
            this.out_datetime = new BufferedWriter(new FileWriter(out_datetime));
            this.Pcap_open_stream = Pcap_open_stream;
            this.Pcap_log = Pcap_log;
            this.printStream = new PrintStream(Pcap_log);
            System.setOut(printStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cal_rtt_para = new CAL_RTT_PARA();
    }

    public NB_CoAP_RTT(String out_RTT, String out_RTT_lost, String out_datetime, String Pcap_open_stream, String Pcap_log, String Retransmission_datetime) {
        this(out_RTT, out_RTT_lost, out_datetime, Pcap_open_stream, Pcap_log);
        try {
            this.out_retransmission_datetime = new BufferedWriter(new FileWriter(Retransmission_datetime));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void RTT_NB_CoAP_RTT() {
        try
        {
            Pcap pcap = Pcap.openStream(this.Pcap_open_stream);
            ArrayList<CoAP_mes> CON_list = new ArrayList<CoAP_mes>();
            ArrayList<CoAP_mes> ACK_list = new ArrayList<CoAP_mes>();
            ArrayList<Integer> RTT_value = new ArrayList<Integer>();
            HashMap<String, Integer> wait_to_match = new HashMap<>();
            HashMap<String, Integer> history_max = new HashMap<>();

            pcap.loop(new PacketHandler() {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {

                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();

                        byte[] Coapheader = new byte[12];
                        Coap.getBytes(Coapheader);
                        int mid = CoAP_mes.GetMID(Coapheader);
                        int type = CoAP_mes.GetType(Coapheader);
                        byte[] Token = CoAP_mes.GetToken(Coapheader);
                        String KEY = CoAP_mes.make_KEY(CoAP_mes.GetMIDbyByte(Coapheader), Token);
                        if(type == 0)//this is a CON
                        {
                            cal_rtt_para.setAll_CON(cal_rtt_para.getAll_CON() + 1);
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid, Token, KEY);
                            CON_list.add(con_mes);

                            if(wait_to_match.containsKey(KEY))
                            {
                                cal_rtt_para.setAll_retransmission(cal_rtt_para.getAll_retransmission() + 1);
                                Integer value = wait_to_match.get(KEY);
                                wait_to_match.put(KEY, value + 1);
                                history_max.put(KEY, value + 1);
                                out_retransmission_datetime.write(formatter.format(new Date(con_mes.getArrive_date() / 1000)) + "\n");
                            }
                            else {
                                wait_to_match.put(KEY, 1);
                                history_max.put(KEY, 1);
                            }

                        }
                        else if(type == 2)//this is an ACK message
                        {
                            CoAP_mes ack_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(), transportPacket.getSourcePort(), mid, Token, KEY);
                            ACK_list.add(ack_mes);
                            if(wait_to_match.containsKey(KEY))
                            {
                                Integer value = wait_to_match.get(KEY);
                                if (value == 1)
                                {
                                    match_CON_ACK(CON_list, ACK_list, KEY);
                                    wait_to_match.remove(KEY);
                                    history_max.remove(KEY);
                                }
                                else {
                                    wait_to_match.put(KEY, value - 1);
                                }
                            }
                            else
                            {
                                System.out.println("error ACK can not find its CON message");
                                Date newdata = new Date(ack_mes.getArrive_date() / 1000);
                                String datatowrite = formatter.format(newdata);
                                System.out.println(datatowrite);
                                return false;
                            }
                        }
                    }
                    return true;
                }
                void match_CON_ACK(ArrayList<CoAP_mes> CON_list, ArrayList<CoAP_mes> ACK_list, String KEY) throws IOException {
                    int j = 0;
                    int count = 0;
                    for(int i = 0; i < CON_list.size(); i++)
                    {
                        if (CON_list.get(i).getMid_string().equals(KEY))
                        {
                            count++;
                            for(;j < ACK_list.size(); j++)
                            {
                                if(ACK_list.get(j).getMid_string().equals(KEY))
                                {
                                    int dif = CoAP_mes.Get_diff_ms(ACK_list.get(j).getArrive_date(), CON_list.get(i).getArrive_date());
                                    String RTT =  dif + ",";
                                    out_RTT.write(RTT);
                                    RTT_value.add(dif);
                                    Date newdata = new Date(CON_list.get(i).getArrive_date() / 1000);
                                    String datatowrite = formatter.format(newdata);
                                    out_datetime.write(datatowrite + "\n");

                                    CON_list.remove(i);
                                    history_max.remove(i);
                                    i--;
                                    ACK_list.remove(j);
                                    break;
                                }
                            }
                        }
                    }
                    cal_rtt_para.setRetransmission_cause_of_small_RTO(cal_rtt_para.getRetransmission_cause_of_small_RTO() + count - 1);
                }
            });
            System.out.println("CON_list length : " + CON_list.size());
            System.out.println("ACK_list length : " + ACK_list.size());
            System.out.println("all CONs : " + cal_rtt_para.getAll_CON());
            System.out.println("retransmission : " +  cal_rtt_para.getAll_retransmission());

            int all_RTT_value = 0;
            for(Integer i : RTT_value)
            {
                all_RTT_value += i;
            }
            System.out.println("RTT AVG() : " + all_RTT_value / RTT_value.size());
            System.out.println("rate of retransmission :" + (float) cal_rtt_para.getAll_retransmission() / cal_rtt_para.getAll_CON());
            System.out.println("rate of lost : " + (float)(CON_list.size() - ACK_list.size()) / cal_rtt_para.getAll_CON());
//            System.out.println("keyset.size() : " + wait_to_match.keySet().size());
//            System.out.println("history_max.size() : " + history_max.keySet().size());
            int packet_loss_num = 0;
            for(String i : wait_to_match.keySet())
            {
//                System.out.println(wait_to_match.get(i) + " : " + history_max.get(i));
                if(wait_to_match.get(i) == history_max.get(i))
                {
                    packet_loss_num++;
                }
                if(history_max.get(i) == 1)
                {
                    System.out.println("there is maybe a problem , the message id is : " + i);
                }
                cal_rtt_para.setRetransmission_cause_of_small_RTO(cal_rtt_para.getRetransmission_cause_of_small_RTO()+history_max.get(i) - wait_to_match.get(i));
            }

            System.out.println("retransmission_cause_of_small_RTO : " + cal_rtt_para.getRetransmission_cause_of_small_RTO());
            System.out.println("rate of retransmission_cause_of_small_RTO : " + (float) cal_rtt_para.getRetransmission_cause_of_small_RTO()/ cal_rtt_para.getAll_CON());

            System.out.println("withour retransmission the CONs : " + (cal_rtt_para.getAll_CON() - cal_rtt_para.getAll_retransmission()));
            System.out.println("the whole packet loss num : " + packet_loss_num);
            System.out.println("the whole packet loss rate : " + (float)packet_loss_num / (cal_rtt_para.getAll_CON() - cal_rtt_para.getAll_retransmission()));



            //handle loss,

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            out_datetime.close();
            out_RTT_lost.close();
            out_RTT.close();
            out_retransmission_datetime.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
