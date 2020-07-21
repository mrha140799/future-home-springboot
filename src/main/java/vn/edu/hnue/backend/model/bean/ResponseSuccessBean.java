package vn.edu.hnue.backend.model.bean;

import vn.edu.hnue.backend.model.util.Constants;

public class ResponseSuccessBean extends ResponseBean {
    public ResponseSuccessBean( Object result) {
        super(Constants.STATUS_CODE_1, result);
    }
}
