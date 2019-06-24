package io.nuls.contract.account.storage;

import io.nuls.base.data.Address;
import io.nuls.contract.account.model.po.AccountPo;

public interface AccountStorageService {
    /**
     * 创建账户
     * Create account
     * @param account
     * @return
     */
    boolean saveAccount(AccountPo account);

    /**
     * 根据账户获取账户信息
     * According to the account to obtain account information
     * @param address
     * @return the result of the opration
     */
    AccountPo getAccount(byte[] address);

    /**
     * 删除账户
     * Delete account
     * @param address Account address to be deleted
     * @return the result of the opration
     */
    boolean removeAccount(Address address);

}
