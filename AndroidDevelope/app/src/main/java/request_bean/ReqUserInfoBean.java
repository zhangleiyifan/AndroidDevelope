package request_bean;

/**
 * @author: guoyazhou
 * @date: 2016-02-26 16:28
 */
public class ReqUserInfoBean {


    public String access_token;

    public ReqUserInfoBean(String access_token) {

        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
