import java.util.Date;

public class CoAP_mes {
    public long arrive_date;
    public int des_port;
    public int src_port;
    public int mid;
    public CoAP_mes(long arrive_date, int des_port, int src_port, int mid)
    {
        this.arrive_date = arrive_date;
        this.des_port = des_port;
        this.src_port = src_port;
        this.mid = mid;
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

    public void setSrc_port(int src_port) {
        this.src_port = src_port;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
