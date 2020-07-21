package vn.edu.hnue.backend.service.account;

import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.dto.AccountDTO;

public interface AccountService {
    ResponseBean getAllAccountOther();
    ResponseBean updateAccount(AccountDTO accountDTO);
    ResponseBean enabled(Long id, boolean isEnable);
}
