package by.training.wallet;

import by.training.dao.CRUDDao;

import java.sql.SQLException;
import java.util.List;

public interface WalletDao extends CRUDDao<WalletDto, Long> {
    List<WalletDto> findUserWallets(Long userId) throws WalletDaoException;
}