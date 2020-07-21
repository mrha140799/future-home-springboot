package vn.edu.hnue.backend.model.bean;

import lombok.NoArgsConstructor;
import vn.edu.hnue.backend.model.util.Constants;

@NoArgsConstructor
public class ResponseErrorBean extends ResponseBean {

    public ResponseErrorBean(int code, Object result) {
        super(code, result);
    }

    public ResponseErrorBean(Object result) {
        super(Constants.STATUS_CODE_0,result);
    }
}
