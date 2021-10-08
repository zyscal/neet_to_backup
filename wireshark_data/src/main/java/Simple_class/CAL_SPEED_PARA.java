package Simple_class;

import java.util.ArrayList;
import java.util.Date;

public class CAL_SPEED_PARA {
    /**
     * UL is 0
     * DL is 1
     */

    private int UL_DL;
    private long begin_time;
    private long end_time;
    private long all_time;
    private double intercal_second;
    ArrayList<Integer> packet_num;
    private Integer history_packet_num;
    ArrayList<Integer> Byte_num;
    private Integer history_Byte_num;
    private Integer last_serial_of_array;

    ArrayList<Long> current_time;
    ArrayList<Integer> current_MID;
    Integer current_pos;
    ArrayList<Double> Packet_persecond;
    ArrayList<Double> Byte_persecond;

    public Integer getCurrent_pos() {
        return current_pos;
    }

    public void setCurrent_pos(Integer current_pos) {
        this.current_pos = current_pos;
    }

    public CAL_SPEED_PARA(int UL_DL, double intercal_second)
    {
        this.UL_DL = UL_DL;
        this.begin_time = 0;
        this.end_time = 0;
        this.all_time = 0;
        this.intercal_second = intercal_second;
        this.packet_num = new ArrayList<>();
        this.packet_num.add(0);

        this.Byte_num = new ArrayList<>();
        this.Byte_num.add(0);

        this.history_Byte_num = 0;
        this.history_packet_num = 0;
        this.last_serial_of_array = 0;

        this.Packet_persecond = new ArrayList<>();
        this.Packet_persecond.add(0.0);
        this.Byte_persecond = new ArrayList<>();
        this.Byte_persecond.add(0.0);

        this.current_MID = new ArrayList<>();
        this.current_time = new ArrayList<>();
        this.setCurrent_pos(0);
    }

    public long get_serial(long this_time)
    {
        return ((this_time - this.begin_time) / (long)(this.intercal_second * 1000000)) + 1;
    }

    public void add_arraylist(int sizeof_Byte, int Serial_num)
    {
        if(Serial_num == this.last_serial_of_array)
        {
            this.history_Byte_num += sizeof_Byte;
            this.history_packet_num += 1;
            this.getByte_num().set(this.last_serial_of_array, this.history_Byte_num);
            this.getPacket_num().set(this.last_serial_of_array, this.history_packet_num);
            return;
        }
        for(int i = this.last_serial_of_array + 1; i < Serial_num; i++)
        {
            this.getByte_num().add(this.history_Byte_num);
            this.getPacket_num().add(this.history_packet_num);
        }
        this.last_serial_of_array = Serial_num;
        this.history_Byte_num += sizeof_Byte;
        this.history_packet_num += 1;
        this.getByte_num().add(this.history_Byte_num);
        this.getPacket_num().add(this.history_packet_num);
        return;
    }

    public void calcul_speed()
    {
        for(int i = 1; i < this.getPacket_num().size(); i++)
        {
            Double value_persecond = (this.getPacket_num().get(i) - this.getPacket_num().get(i - 1)) / (double)this.intercal_second;
            this.Packet_persecond.add(value_persecond);
        }
        for(int i = 1; i < this.getByte_num().size(); i++)
        {
            Double value_persecond = (this.getByte_num().get(i) - this.getByte_num().get(i - 1)) / (double)this.intercal_second;
            this.Byte_persecond.add(value_persecond);
        }
    }


    public ArrayList<Double> getPacket_persecond() {
        return Packet_persecond;
    }

    public void setPacket_persecond(ArrayList<Double> packet_persecond) {
        Packet_persecond = packet_persecond;
    }

    public ArrayList<Double> getByte_persecond() {
        return Byte_persecond;
    }

    public void setByte_persecond(ArrayList<Double> byte_persecond) {
        Byte_persecond = byte_persecond;
    }

    public int getUL_DL() {
        return UL_DL;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public ArrayList<Integer> getPacket_num() {
        return packet_num;
    }

    public ArrayList<Integer> getByte_num() {
        return Byte_num;
    }

    public void setUL_DL(int UL_DL) {
        this.UL_DL = UL_DL;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
        this.all_time = this.end_time - this.begin_time;
    }

    public void setPacket_num(ArrayList<Integer> packet_num) {
        this.packet_num = packet_num;
    }

    public void setByte_num(ArrayList<Integer> byte_num) {
        Byte_num = byte_num;
    }

    public long getAll_time() {
        return all_time;
    }

    public void setAll_time(long all_time) {
        this.all_time = all_time;
    }


    public double getIntercal_second() {
        return intercal_second;
    }

    public void setIntercal_second(double intercal_second) {
        this.intercal_second = intercal_second;
    }

    public Integer getHistory_packet_num() {
        return history_packet_num;
    }

    public void setHistory_packet_num(Integer history_packet_num) {
        this.history_packet_num = history_packet_num;
    }

    public Integer getHistory_Byte_num() {
        return history_Byte_num;
    }

    public void setHistory_Byte_num(Integer history_Byte_num) {
        this.history_Byte_num = history_Byte_num;
    }

    public Integer getLast_serial_of_array() {
        return last_serial_of_array;
    }

    public void setLast_serial_of_array(Integer last_serial_of_array) {
        this.last_serial_of_array = last_serial_of_array;
    }

    public ArrayList<Long> getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(ArrayList<Long> current_time) {
        this.current_time = current_time;
    }

    public ArrayList<Integer> getCurrent_MID() {
        return current_MID;
    }

    public void setCurrent_MID(ArrayList<Integer> current_MID) {
        this.current_MID = current_MID;
    }
}
