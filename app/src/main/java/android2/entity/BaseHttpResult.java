package android2.entity;

/**
 * Created by Sher on 2017/9/3.
 */

public class BaseHttpResult extends BaseEntity {
    private int resultCode;
    private String resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
