//########################


import gov.nist.javax.sip.header.Server;

public class data_analy {

    public static void main(String[] args) {
        /**
         * statistics RTT and lost
         * one device
         */
//        do {
//            String BC26_20210221_20210222_4app_client_5683_read1 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read1.pcap";
//            String read1_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_RTT.txt";
//            String read1_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_lost.txt";
//            String read1_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_datetime.txt";
//            String read1_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_rtt_log.txt";
//            NB_CoAP_RTT nb_udp_reg_rtt = new NB_CoAP_RTT(read1_out_rtt, read1_out_rtt_lost,
//                    read1_out_datetime, BC26_20210221_20210222_4app_client_5683_read1, read1_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5683_read5 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read5.pcap";
//            String read5_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_RTT.txt";
//            String read5_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_lost.txt";
//            String read5_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_datetime.txt";
//            String read5_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(read5_out_rtt, read5_out_rtt_lost,
//                    read5_out_datetime, BC26_20210221_20210222_4app_client_5683_read5, read5_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5683_read3333 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read3333.pcap";
//            String read3333_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_RTT.txt";
//            String read3333_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_lost.txt";
//            String read3333_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_datetime.txt";
//            String read3333_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(read3333_out_rtt, read3333_out_rtt_lost,
//                    read3333_out_datetime, BC26_20210221_20210222_4app_client_5683_read3333, read3333_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5683_read34828 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read34828.pcap";
//            String read34828_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_RTT.txt";
//            String read34828_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_lost.txt";
//            String read34828_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_datetime.txt";
//            String read34828_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(read34828_out_rtt, read34828_out_rtt_lost,
//                    read34828_out_datetime, BC26_20210221_20210222_4app_client_5683_read34828, read34828_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_server_5683_observe503 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe503.pcap";
//            String ob503_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_RTT.txt";
//            String ob503_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_lost.txt";
//            String ob503_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_datetime.txt";
//            String ob503_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(ob503_out_rtt, ob503_out_rtt_lost,
//                    ob503_out_datetime, BC26_20210221_20210222_4app_server_5683_observe503, ob503_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_server_5683_observe34828029272 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe34828029272.pcap";
//            String ob34828029272_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_RTT.txt";
//            String ob34828029272_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_lost.txt";
//            String ob34828029272_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_datetime.txt";
//            String ob34828029272_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(ob34828029272_out_rtt, ob34828029272_out_rtt_lost,
//                    ob34828029272_out_datetime, BC26_20210221_20210222_4app_server_5683_observe34828029272, ob34828029272_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_server_5683_URI = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_URI.pcap";
//            String URI_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_RTT.txt";
//            String URI_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_lost.txt";
//            String URI_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_datetime.txt";
//            String URI_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(URI_out_rtt, URI_out_rtt_lost,
//                    URI_out_datetime, BC26_20210221_20210222_4app_server_5683_URI, URI_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5683_REG = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_REG.pcap";
//            String REG_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_RTT.txt";
//            String REG_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_lost.txt";
//            String REG_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_datetime.txt";
//            String REG_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(REG_out_rtt, REG_out_rtt_lost,
//                    REG_out_datetime, BC26_20210221_20210222_4app_client_5683_REG, REG_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5683_UPD = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_UPD.pcap";
//            String UPD_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_RTT.txt";
//            String UPD_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_lost.txt";
//            String UPD_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_datetime.txt";
//            String UPD_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(UPD_out_rtt, UPD_out_rtt_lost,
//                    UPD_out_datetime, BC26_20210221_20210222_4app_client_5683_UPD, UPD_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//
//            String BC26_20210221_20210222_4app_client_5700 = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5700.pcap";
//            String firmware_out_rtt = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_RTT.txt";
//            String firmware_out_rtt_lost = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_lost.txt";
//            String firmware_out_datetime = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_datetime.txt";
//            String firmware_Pcap_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_rtt_log.txt";
//            nb_udp_reg_rtt = new NB_CoAP_RTT(firmware_out_rtt, firmware_out_rtt_lost,
//                    firmware_out_datetime, BC26_20210221_20210222_4app_client_5700, firmware_Pcap_log);
//            nb_udp_reg_rtt.RTT_NB_CoAP_RTT();
//        }while (false);

//        do{
//            String read1_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_UL";
//            String sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read1_ACKSENDER.pcap";
//            String revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read1_ACKRECV.pcap";
//            NB_UDP_handle_UP_Down_NON nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read1_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read1_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read1_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read1_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read1_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read5_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read5_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read5_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read5_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read5_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read5_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read5_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read5_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read3333_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read3333_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read3333_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read3333_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read3333_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read3333_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read3333_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read3333_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read34828_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read34828_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read34828_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read34828_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String read34828_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read34828_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read34828_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(read34828_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String observe503_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe503_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe503_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(observe503_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String observe503_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe503_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe503_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(observe503_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String observe34828029272_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe34828029272_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe34828029272_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(observe34828029272_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String observe34828029272_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe34828029272_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe34828029272_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(observe34828029272_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String URI_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_URI_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_URI_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(URI_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String URI_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_URI_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_URI_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(URI_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String UPD_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_UPD_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_UPD_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(UPD_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String UPD_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_UPD_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_UPD_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(UPD_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String REG_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_REG_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_REG_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(REG_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String REG_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_REG_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_REG_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(REG_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String firmware_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5700_CONSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5700_CONRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(firmware_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String firmware_DL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700_DL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5700_ACKSENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5700_ACKRECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(firmware_DL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String NON_503_1_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_NON/app_bc26_20210221-20210222_NON_503_1_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_503_1_SENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_NON_503_1_RECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(NON_503_1_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String NON_503_2_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_NON/app_bc26_20210221-20210222_NON_503_2_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_503_2_SENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_NON_503_2_RECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(NON_503_2_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//
//            String NON_34828029272_UL = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_NON/app_bc26_20210221-20210222_34828029272_UL";
//            sender_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_34828029272_SENDER.pcap";
//            revicer_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_NON_34828029272_RECV.pcap";
//            nb_udp_handle_up_down_non = new NB_UDP_handle_UP_Down_NON(NON_34828029272_UL, sender_file, revicer_file);
//            nb_udp_handle_up_down_non.UP_DOWN_NON_delay();
//        }while(false);




//        Float[] speed_list = new Float[]{0.1f, 1.0f ,10.0f};
//        String server_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5700-5683.pcap";
//        for(int i = 0; i < speed_list.length; i++)
//        {
//            String BC26_20210221_20210222_4app_server_5683_read1_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read1_CONSENDER.pcap";
//            String read1_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1.txt";
//            NB_UDP_calculate_speed nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_read1_CONSENDER
//            , read1_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_read5_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read5_CONSENDER.pcap";
//            String read5_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_read5_CONSENDER
//                    , read5_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_read3333_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read3333_CONSENDER.pcap";
//            String read3333_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_read3333_CONSENDER
//                    , read3333_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_read34828_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_read34828_CONSENDER.pcap";
//            String read34828_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_read34828_CONSENDER
//                    , read34828_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_observe503_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe503_CONSENDER.pcap";
//            String observe503_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_observe503_CONSENDER
//                    , observe503_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_observe34828029272_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_observe34828029272_CONSENDER.pcap";
//            String observe34828029272_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_observe34828029272_CONSENDER
//                    , observe34828029272_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_URI_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_URI_CONSENDER.pcap";
//            String URI_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_URI_CONSENDER
//                    , URI_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_REG_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_REG_ACKSENDER.pcap";
//            String REG_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_REG_ACKSENDER
//                    , REG_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5683_UPD_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5683_UPD_ACKSENDER.pcap";
//            String UPD_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5683_UPD_ACKSENDER
//                    , UPD_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_server_5700_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_server_5700_ACKSENDER.pcap";
//            String firmware_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(server_file, BC26_20210221_20210222_4app_server_5700_ACKSENDER
//                    , firmware_speed_log,1, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//        }
//
//        String client_file = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5700-5683_without_error.pcap";
//        for(int i = 0; i < speed_list.length; i++)
//        {
//            String BC26_20210221_20210222_4app_client_5683_URI_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_URI_ACKSENDER.pcap";
//            String URI_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_URI/app_bc26_20210221-20210222_URI.txt";
//            NB_UDP_calculate_speed nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_URI_ACKSENDER
//                    , URI_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_UPD_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_UPD_CONSENDER.pcap";
//            String UPD_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_UPD/app_bc26_20210221-20210222_UPD.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_UPD_CONSENDER
//                    , UPD_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_NON_503_1_SENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_503_1_SENDER.pcap";
//            String NON_503_1_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_NON/app_bc26_20210221-20210222_NON_503_1.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_NON_503_1_SENDER
//                    , NON_503_1_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_NON_503_2_SENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_503_2_SENDER.pcap";
//            String NON_503_2_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_NON/app_bc26_20210221-20210222_NON_503_2.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_NON_503_2_SENDER
//                    , NON_503_2_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_NON_34828029272_SENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_NON_34828029272_SENDER.pcap";
//            String NON_34818_29272_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_NON_34828029272_SENDER
//                    , NON_34818_29272_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_read1_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read1_ACKSENDER.pcap";
//            String read1_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read1/app_bc26_20210221-20210222_read1.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_read1_ACKSENDER
//                    , read1_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_read5_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read5_ACKSENDER.pcap";
//            String read5_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read5/app_bc26_20210221-20210222_read5.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_read5_ACKSENDER
//                    , read5_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_read34828_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read34828_ACKSENDER.pcap";
//            String read34828_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read34828/app_bc26_20210221-20210222_read34828.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_read34828_ACKSENDER
//                    , read34828_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_read3333_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_read3333_ACKSENDER.pcap";
//            String read3333_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_read3333/app_bc26_20210221-20210222_read3333.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_read3333_ACKSENDER
//                    , read3333_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_observe503_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe503_ACKSENDER.pcap";
//            String observe503_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe503/app_bc26_20210221-20210222_observe503.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_observe503_ACKSENDER
//                    , observe503_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_observe34828029272_ACKSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_observe34828029272_ACKSENDER.pcap";
//            String observe34828029272_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_observe34828029272/app_bc26_20210221-20210222_observe34828029272.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_observe34828029272_ACKSENDER
//                    , observe34828029272_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5683_REG_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5683_REG_CONSENDER.pcap";
//            String REG_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_REG/app_bc26_20210221-20210222_REG.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5683_REG_CONSENDER
//                    , REG_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//
//            String BC26_20210221_20210222_4app_client_5700_CONSENDER = "/home/zyscal/Documents/need_to_backup/wiresahrk_caught/BC26_20210221-20210222/BC26_20210221-20210222_4app_client_5700_CONSENDER.pcap";
//            String firmware_speed_log = "src/app_bc26_20210221-20210222/app_bc26_20210221-20210222_5700/app_bc26_20210221-20210222_5700.txt";
//            nb_udp_calculate_speed = new NB_UDP_calculate_speed(client_file, BC26_20210221_20210222_4app_client_5700_CONSENDER
//                    , firmware_speed_log,0, speed_list[i]);
//            nb_udp_calculate_speed.speed_calculate();
//        }

        /** 20211031
         * 基于NB-IoT TCP 注册事件RTT计算
         */
//        NB_IoT_TCP_UDP_REG_20211031();
        /**
         *  20211101 UDP
         */
//        NB_IoT_UDP_REG_20211101();
        /**
         * 20211105 tcp 注册rttNB_IoT_TCP_REG_20211107
         */
//        NB_IoT_TCP_REG_20211105();
        /**
         * 20211106 tcp 注册rtt
         */
        NB_IoT_TCP_REG_20211106();

        /**
         * 20211107 tcp 不同时间间隔注册
         */
//        NB_IoT_TCP_REG_20211107();

        /**
         * 20211108 tcp 多个时间频率注册
         */
//        NB_IoT_TCP_REG_20211108();
//        NB_IoT_TCP_REG_20211108_2();

        /**
         * 20211109 tcp 多个频率注册
         */
//        NB_IoT_TCP_REG_20211109();

        /**
         * 20211110 tcp 1/16 QPS 注册
         */
//        NB_IoT_TCP_REG_20211110();
    }

    public static void NB_IoT_TCP_REG_20211110() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211110TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211110RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211110RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211110RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211110RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211110TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211110RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211110RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211110RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211110TCP/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211110RegOverTCP/ClientRetransmissions.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);

    }


    public static void NB_IoT_TCP_REG_20211109() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211109TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211109RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211109RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211109RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211109RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211109TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211109RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211109RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211109RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211109TCP/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211109RegOverTCP/ClientRetransmissions.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);

        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211109TCP/Server_retransmission.pcap";
        String Server_Retransmission_file = "src/20211109RegOverTCP/ServerRetransmissions.txt";
        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);
    }


    public static void NB_IoT_TCP_REG_20211108_2() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108_2TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211108_2RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211108_2RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211108_2RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211108_2RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108_2TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211108_2RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211108_2RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211108_2RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108_2TCP/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211108_2RegOverTCP/ClientRetransmissions.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);

        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108_2TCP/Server_retransmission.pcap";
        String Server_Retransmission_file = "src/20211108_2RegOverTCP/ServerRetransmissions.txt";
        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);
    }

    public static void NB_IoT_TCP_REG_20211108() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108TCP/Server_reg_rtt_without_retransmission.pcap";
//        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211108RegOverTCP/ServerRegRTT_without_retransmission.txt";
        String Server_Datetime = "src/20211108RegOverTCP/ServerRegDateTime_without_retransmission.txt";
        String Server_log = "src/20211108RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211108RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

//        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108TCP/Client_reg_rtt.pcap";
//        String Client_RTT = "src/20211108RegOverTCP/ClientRegRTT.txt";
//        String Client_DateTime = "src/20211108RegOverTCP/ClientRegDateTime.txt";
//        String Client_log_file = "src/20211108RegOverTCP/Client_log.txt";
//        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);
//
//        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108TCP/Client_retransmission.pcap";
//        String RetransmissionDatetimeFileName = "src/20211108RegOverTCP/ClientRetransmissions.txt";
//        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);
//
//        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211108TCP/Server_retransmission.pcap";
//        String Server_Retransmission_file = "src/20211108RegOverTCP/ServerRetransmissions.txt";
//        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);
    }

    public static void NB_IoT_TCP_REG_20211107() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211107TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211107RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211107RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211107RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211107RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211107TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211107RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211107RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211107RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211107TCP/ServerRetransmission.pcap";
        String Server_Retransmission_file = "src/20211107RegOverTCP/ServerRetransmissions.txt";
        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);

    }



    public static void NB_IoT_TCP_REG_20211106() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211106TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211106RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211106RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211106RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211106RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211106TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211106RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211106RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211106RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211106TCP/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211106RegOverTCP/ClientRetransmissions.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);
    }

    public static void NB_IoT_TCP_REG_20211105() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String Server_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211105TCP/Server_reg_rtt.pcap";
        String Server_RTT = "src/20211105RegOverTCP/ServerRegRTT.txt";
        String Server_Datetime = "src/20211105RegOverTCP/ServerRegDateTime.txt";
        String Server_log = "src/20211105RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211105RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, Server_RTT_FileName, Server_RTT, Server_Datetime, Server_log, Server_packet_loss);

        String Client_RTT_FileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211105TCP/Client_reg_rtt.pcap";
        String Client_RTT = "src/20211105RegOverTCP/ClientRegRTT.txt";
        String Client_DateTime = "src/20211105RegOverTCP/ClientRegDateTime.txt";
        String Client_log_file = "src/20211105RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, Client_RTT_FileName, Client_RTT, Client_DateTime, Client_log_file);

        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211105TCP/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211105RegOverTCP/ClientRetransmissions.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_RTT_FileName, RetransmissionDatetimeFileName);

        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211105TCP/Server_retransmission.pcap";
        String Server_Retransmission_file = "src/20211105RegOverTCP/ServerRetransmissions.txt";
        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);
    }

    public static void NB_IoT_UDP_REG_20211101() {
        String out_RTT = "src/20211101RegOverUDP/ClinetRTT.txt";
        String out_RTT_lost = "src/20211101RegOverUDP/ClientRTT_lost.txt";
        String out_datetime = "src/20211101RegOverUDP/ClientRTT_datetime.txt";
        String Pcap_open_stream = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211101UDP/Client_REG_RTT.pcap";
        String out_log = "src/20211101RegOverUDP/out_log.txt";
        String out_retransmission_datetime_file = "src/20211101RegOverUDP/retransmission_file.txt";
        NB_CoAP_RTT nb_coAP_rtt = new NB_CoAP_RTT(out_RTT, out_RTT_lost, out_datetime, Pcap_open_stream, out_log, out_retransmission_datetime_file);
        nb_coAP_rtt.RTT_NB_CoAP_RTT();
    }

    public static void NB_IoT_TCP_UDP_REG_20211031() {
        NB_CoAP_REG_TCP nb_coAP_reg_tcp = new NB_CoAP_REG_TCP();
        String fileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211031TCP/20211031RegOverTcp/Client_REG.pcap";
        String RegRTTOutName = "src/20211031RegOverTCP/ClientRegRTT.txt";
        String RegRTTOutDateTime = "src/20211031RegOverTCP/ClientRegRTTDateTime.txt";
        String Client_log_file = "src/20211031RegOverTCP/Client_log.txt";
        nb_coAP_reg_tcp.RegRTTFromClient(5700, 5701, fileName, RegRTTOutName, RegRTTOutDateTime, Client_log_file);
//
        fileName = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211031TCP/20211031RegOverTcp/Server_NB_REG_Created_RTT.pcap";
        RegRTTOutName = "src/20211031RegOverTCP/ServerRegRTT.txt";
        RegRTTOutDateTime = "src/20211031RegOverTCP/ServerRegRTTDateTime.txt";
        String Server_log_file = "src/20211031RegOverTCP/Server_log.txt";
        String Server_packet_loss = "src/20211031RegOverTCP/Server_packet_loss.txt";
        nb_coAP_reg_tcp.RegRTTFromServer(0, 5800, fileName, RegRTTOutName, RegRTTOutDateTime, Server_log_file, Server_packet_loss);
//

        String Client_REG = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211031TCP/20211031RegOverTcp/Client_REG.pcap";
        String Client_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211031TCP/20211031RegOverTcp/Client_retransmission.pcap";
        String RetransmissionDatetimeFileName = "src/20211031RegOverTCP/ClientRetransmission.txt";
        nb_coAP_reg_tcp.FindClientRetransmission(Client_Retransmission, 5701, Client_REG, RetransmissionDatetimeFileName);

        String Server_Retransmission = "/home/zyscal/Documents/need_to_backup/need_to_backup/wiresahrk_caught/20211031TCP/20211031RegOverTcp/Server_retransmission.pcap";
        String Server_Retransmission_file = "src/20211031RegOverTCP/ServerRetransmission.txt";
        nb_coAP_reg_tcp.FindServerRetransmission(Server_Retransmission, Server_Retransmission_file);
    }

}
