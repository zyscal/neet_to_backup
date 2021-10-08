package Simple_class;

import gov.nist.javax.sip.header.To;

import java.io.IOException;
import java.util.Date;

public class CoAP_mes {
    public long arrive_date;
    public int des_port;
    public int src_port;
    public int mid;
    public byte[] Token;
    public String mid_string;



public CoAP_mes(long arrive_date, int des_port, int src_port, int mid, byte[] Token)
{
    this.arrive_date = arrive_date;
    this.des_port = des_port;
    this.src_port = src_port;
    this.mid = mid;
    this.Token = Token;
}
    public CoAP_mes(long arrive_date, int des_port, int src_port, int mid, byte[] Token, String mid_string)
    {
        this.arrive_date = arrive_date;
        this.des_port = des_port;
        this.src_port = src_port;
        this.mid = mid;
        this.Token = Token;
        this.mid_string = mid_string;
    }
    public long getArrive_date() {
        return arrive_date;
    }

    public void setArrive_date(long arrive_date) {
        this.arrive_date = arrive_date;
    }

    public int getDes_port() {
        return des_port;
    }

    public void setDes_port(int des_port) {
        this.des_port = des_port;
    }

    public int getSrc_port() {
        return src_port;
    }

    public String getMid_string() {
        return mid_string;
    }

    public void setMid_string(String mid_string) {
        this.mid_string = mid_string;
    }

    public void setSrc_port(int src_port) {
        this.src_port = src_port;
    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public byte[] getToken() {
        return Token;
    }

    public void setToken(byte[] token) {
        Token = token;
    }
    public static int Get_diff_ms(long ACK, long CON)
    {
        Date CON_Date = new Date(CON/1000 );
        Date ACK_Date = new Date(ACK/1000 );
        long cha = ACK_Date.getTime() - CON_Date.getTime();
        return (int)cha;
    }
    public static byte[] GetMIDbyByte(byte[] CoAPheader)
    {
        byte[] ans = new byte[]{CoAPheader[2], CoAPheader[3]};
        return  ans;
    }
    public static String make_KEY(byte[] MID, byte[] Token)
    {
        String KEY = "";
        for(int i = 0; i < MID.length; i++)
        {
            KEY = KEY + (MID[i] & 0xff) + " ";
        }
        for(int k = 0; k < Token.length; k++)
        {
            KEY = KEY + (Token[k] & 0xff) + " ";
        }
        return KEY;
    }
    public static int GetMID(byte[] CoAPheader) throws IOException {
        int high = (int)((CoAPheader[2] & 0xff) << 8) & 0xffff;
        int low = (int)(CoAPheader[3] & 0xff) & 0xffff;
        int MID = (high + low) & 0xffff;
        //system.out.println("MID : " + MID);
        return MID;
    }
    /**
     *
     * @return 0 means:CON
     * @return 1 means:NON (I am not sure right now!)
     * @return 2 means:ACK
     */
    public static int GetType(byte[] CoAPheader)
    {
        byte Type = (byte) ((CoAPheader[0] & 0x30) >> 4);
        return (int)Type;
    }

    public static byte[] GetToken(byte[] CoAPheader)
    {
        byte[] Token = new byte[8];
        for(int i = 4,j = 0; i <= 11; i++,j++)
        {
            Token[j] = CoAPheader[i];
        }
        return Token;
    }
}
