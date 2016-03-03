package request_bean;

/**
 * @author: guoyazhou
 * @date: 2016-03-03 16:09
 */
public class ReqHealthInfoList {

    public int page;
    public int rows;
    public int id;


    public ReqHealthInfoList(int page, int rows, int id) {
        this.page = page;
        this.rows = rows;
        this.id = id;
    }
}
