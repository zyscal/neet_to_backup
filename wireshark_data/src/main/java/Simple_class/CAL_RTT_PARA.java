package Simple_class;

public class CAL_RTT_PARA {
    private int all_retransmission;
    private int all_CON;
    private int retransmission_cause_of_small_RTO;

    public CAL_RTT_PARA()
    {
        this.all_retransmission = 0;
        this.all_CON = 0;
        this.retransmission_cause_of_small_RTO = 0;
    }

    public int getAll_retransmission() {
        return all_retransmission;
    }

    public void setAll_retransmission(int all_retransmission) {
        this.all_retransmission = all_retransmission;
    }

    public int getAll_CON() {
        return all_CON;
    }

    public void setAll_CON(int all_CON) {
        this.all_CON = all_CON;
    }

    public int getRetransmission_cause_of_small_RTO() {
        return retransmission_cause_of_small_RTO;
    }

    public void setRetransmission_cause_of_small_RTO(int retransmission_cause_of_small_RTO) {
        this.retransmission_cause_of_small_RTO = retransmission_cause_of_small_RTO;
    }
}
