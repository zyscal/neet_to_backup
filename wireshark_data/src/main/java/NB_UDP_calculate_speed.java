import Simple_class.CAL_SPEED_PARA;
import Simple_class.CoAP_mes;
import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TransportPacket;
import io.pkts.protocol.Protocol;

import javax.swing.*;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * this method is used to calculate the UL/DL speed of the client, the value of interval described the granularity of the speed, we use two arraylist to restore the history PACKET number and Byte number
 * the unit time PACKET/Byte numbers can be described by the last one subtracting the previous one.
 *
 *
 * we also found that, the Packet/s can not describe the behavior of packet loss perfectly, so I want to know the sending rate before the specified packet.
 * we use a Time_list to restore EVERY SENDING packet
 */
public class NB_UDP_calculate_speed {

    private CAL_SPEED_PARA UL_para;
    private CAL_SPEED_PARA DL_para;
    private ArrayList<Double> UL_PPerSec_ans_list;
    private ArrayList<Double> UL_BPerSec_ans_list;
    private ArrayList<Double> DL_PPerSec_ans_list;
    private ArrayList<Double> DL_BPerSec_ans_list;

    private ArrayList<Double> UL_PPerSec_before;
    private ArrayList<Double> UL_BPerSec_before;
    private ArrayList<Double> DL_PPerSec_before;
    private ArrayList<Double> DL_BPerSec_before;


    private String pretreatment;
    private String formal_process;
    private String speed_log;
    /**
     * UL = 0;
     * DL = 1;
     */
    private Integer UL_DL;
    private Float intercal;

    public NB_UDP_calculate_speed(String pretreatment, String formal_process, String speed_log, Integer UL_DL, Float intercal)
    {
        this.UL_PPerSec_ans_list = new ArrayList<>();
        this.UL_BPerSec_ans_list = new ArrayList<>();
        this.DL_PPerSec_ans_list = new ArrayList<>();
        this.DL_BPerSec_ans_list = new ArrayList<>();
        UL_PPerSec_before = new ArrayList<>();
        UL_BPerSec_before = new ArrayList<>();
        DL_PPerSec_before = new ArrayList<>();
        DL_BPerSec_before = new ArrayList<>();
        this.pretreatment = pretreatment;
        this.formal_process = formal_process;
        this.UL_DL = UL_DL;
        this.intercal = intercal;
        this.UL_para = new CAL_SPEED_PARA(0, intercal);
        this.DL_para = new CAL_SPEED_PARA(1, intercal);
        String[] speed_UL_DL_log = speed_log.split("\\.");
        if(UL_DL == 0)
        {
            speed_UL_DL_log[0] += "_UL_speed_intercal" + this.intercal + ".";

        }
        else
        {
            speed_UL_DL_log[0] += "_DL_speed_intercal" + this.intercal + ".";
        }
        this.speed_log = speed_UL_DL_log[0] + speed_UL_DL_log[1];
        try
        {
            System.setOut(new PrintStream(this.speed_log));

        }catch (Exception e)
        {}
    }
    public void speed_calculate()
    {
        try
        {
            Pcap pretreatment = Pcap.openStream(this.pretreatment);
            pretreatment.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket) packet.getPacket(Protocol.UDP);
                    int dst_port = transportPacket.getDestinationPort();
                    int src_port = transportPacket.getSourcePort();
                    if(dst_port == 5683 || dst_port == 5700)
                    {
                        //UL
                        handle_UL_DL(UL_para, transportPacket);
                    }
                    else if(src_port == 5683 || src_port == 5700)
                    {
                        //DL
                        handle_UL_DL(DL_para, transportPacket);
                    }
                    return true;
                }
                public void handle_UL_DL(CAL_SPEED_PARA cal_speed_para, TransportPacket transportPacket)
                {
                    //handle_UL_DL Instantaneous_rate
                    if(cal_speed_para.getBegin_time() == 0)
                    {
                        cal_speed_para.setBegin_time(transportPacket.getArrivalTime());
                    }
                    cal_speed_para.setEnd_time(transportPacket.getArrivalTime());

                    long serial_num = cal_speed_para.get_serial(transportPacket.getArrivalTime());
                    int sizeof_Byte = 8;
                    try
                    {
                        Buffer CoAP = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] CoAP_array = CoAP.getArray();
                        sizeof_Byte += CoAP_array.length;
                        cal_speed_para.add_arraylist(sizeof_Byte, (int)serial_num);

                        //handle Instantaneous rate
                        int Mid = CoAP_mes.GetMID(CoAP_array);
                        long long_time = transportPacket.getArrivalTime();
                        cal_speed_para.getCurrent_time().add(long_time);
                        cal_speed_para.getCurrent_MID().add(Mid);
                    }catch (Exception e){}
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //the last one - the before one = the num of the current one
        UL_para.calcul_speed();
        DL_para.calcul_speed();
        try {
            Pcap formal_process = Pcap.openStream(this.formal_process);
            formal_process.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket) packet.getPacket(Protocol.UDP);
                    int dst_port = transportPacket.getDestinationPort();
                    int src_port = transportPacket.getSourcePort();
                    if(dst_port == 5683 || dst_port == 5700)
                    {
                        //UL
                        formal_handle(UL_para, transportPacket);
                    }
                    else if(src_port == 5683 || src_port == 5700)
                    {
                        //Dl
                        formal_handle(DL_para, transportPacket);
                    }
                    return true;
                }
                public void formal_handle(CAL_SPEED_PARA cal_speed_para, TransportPacket transportPacket)
                {
                    int serial_num = (int)cal_speed_para.get_serial(transportPacket.getArrivalTime());
                    if(cal_speed_para.getUL_DL() == 0){//UL
                        UL_PPerSec_ans_list.add(cal_speed_para.getPacket_persecond().get(serial_num));
                        UL_BPerSec_ans_list.add(cal_speed_para.getByte_persecond().get(serial_num));
                        handle_before_speed(cal_speed_para, transportPacket, UL_PPerSec_before);
                    }
                    else {//DL
                        DL_PPerSec_ans_list.add(cal_speed_para.getPacket_persecond().get(serial_num));
                        DL_BPerSec_ans_list.add(cal_speed_para.getByte_persecond().get(serial_num));
                        handle_before_speed(cal_speed_para, transportPacket, DL_PPerSec_before);
                    }
                }
                public void handle_before_speed(CAL_SPEED_PARA cal_speed_para, TransportPacket transportPacket, ArrayList<Double> ans_list)
                {
                    try
                    {
                        Buffer CoAP = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] CoAP_array = CoAP.getArray();
                        int Mid = CoAP_mes.GetMID(CoAP_array);
                        long long_time = transportPacket.getArrivalTime();
                        for(int i = cal_speed_para.getCurrent_pos(); i < cal_speed_para.getCurrent_MID().size(); i++)
                        {
                            if(Mid == cal_speed_para.getCurrent_MID().get(i) && long_time == cal_speed_para.getCurrent_time().get(i))
                            {
                                int before_count = 0;
                                for(int j = i; j >= 0; j--)
                                {
                                    if (long_time - cal_speed_para.getCurrent_time().get(j) <= cal_speed_para.getIntercal_second() * 1000000)
                                    {
                                        before_count++;
                                    }
                                    else{
                                        break;
                                    }
                                }
                                ans_list.add((double) before_count / cal_speed_para.getIntercal_second());
                            }
                        }


                    }catch (Exception e){}
                }

            });
        }catch (Exception e)
        {

        }
        double all_UL_PPerSecond = 0;
        double all_UL_BPerSecond = 0;
        double all_DL_PPerSecond = 0;
        double all_DL_BPerSecond = 0;
        for(int i = 0; i < UL_PPerSec_ans_list.size(); i++)
        {
            all_UL_PPerSecond += UL_PPerSec_ans_list.get(i);
        }
        for(int i = 0; i < UL_BPerSec_ans_list.size(); i++)
        {
            all_UL_BPerSecond += UL_BPerSec_ans_list.get(i);
        }
        for(int i = 0; i < DL_PPerSec_ans_list.size(); i++)
        {
            all_DL_PPerSecond += DL_PPerSec_ans_list.get(i);
        }
        for(int i = 0; i < DL_BPerSec_ans_list.size(); i++)
        {
            all_DL_BPerSecond += DL_BPerSec_ans_list.get(i);
        }

        Double UL_PPerSec_before_AVG = 0.0;
        Double DL_PPerSec_before_AVG = 0.0;
        for (Double i : UL_PPerSec_before)
        {
            UL_PPerSec_before_AVG += i;
        }
        for (Double i : DL_PPerSec_before)
        {
            DL_PPerSec_before_AVG += i;
        }
        UL_PPerSec_before_AVG /= UL_PPerSec_before.size();
        DL_PPerSec_before_AVG /= DL_PPerSec_before.size();



        if(this.UL_DL == 0)
        {
            System.out.println("UL Packet persecond is : " + all_UL_PPerSecond / UL_PPerSec_ans_list.size());
            System.out.println("UL Byte persecond is : " + all_UL_BPerSecond / UL_BPerSec_ans_list.size());
            System.out.println("UL packet persecond before is : " + UL_PPerSec_before_AVG);
        }
        else if(this.UL_DL == 1)
        {
            System.out.println("DL Packet persecond is : " + all_DL_PPerSecond / DL_PPerSec_ans_list.size());
            System.out.println("DL Byte persecond is : " + all_DL_BPerSecond / DL_BPerSec_ans_list.size());
            System.out.println("DL Packet persecond is : " + DL_PPerSec_before_AVG);
        }






//        ArrayList<Double> check_UL_Packet_speed = UL_para.getPacket_persecond();
//        ArrayList<Double> check_UL_Byte_speed = UL_para.getByte_persecond();
//        for(int i = 0; i < check_UL_Packet_speed.size(); i++)
//        {
//            System.out.println("i : " + i + "\t" + check_UL_Packet_speed.get(i));
//        }
//        ArrayList<Integer> check_UL_Byte_array = UL_para.getByte_num();
//        ArrayList<Integer> check_UL_Packet_array = UL_para.getPacket_num();
//        for(int i = 0; i < check_UL_Packet_array.size()/10; i++)
//        {
//            System.out.println("i : " + i + "\t" + check_UL_Packet_array.get(i));
//        }

    }

}
