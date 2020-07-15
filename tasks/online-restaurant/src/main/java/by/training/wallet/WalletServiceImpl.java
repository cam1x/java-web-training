package by.training.wallet;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private WalletDao walletDao;

    @Override
    @Transactional
    public List<WalletDto> findUserWallets(Long userId) throws WalletServiceException {
        try {
            return walletDao.findUserWallets(userId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotFoundException(e);
        }
    }

    @Override
    public boolean deleteWallet(Long walletId) throws WalletServiceException {
        try {
            return walletDao.delete(walletId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteUserWallets(Long userId) throws WalletServiceException {
        try {
            List<WalletDto> userWallets = walletDao.findUserWallets(userId);
            boolean isSuccess = true;
            for (WalletDto userWallet : userWallets) {
                isSuccess &= walletDao.delete(userWallet.getId());
            }
            return isSuccess;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public List<WalletDto> findAllWallets() throws WalletServiceException {
        try {
            return walletDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public boolean saveWallet(WalletDto walletDto) throws WalletServiceException {
        try {
            return walletDao.save(walletDto) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotSavedException(e);
        }
    }

    @Override
    public boolean assignDefaultWallet(Long userId) throws WalletServiceException {
        WalletDto defaultWallet = WalletDto.builder()
                .name("Main wallet")
                .userId(userId)
                .build();
        try {
            return walletDao.save(defaultWallet) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotSavedException(e);
        }
    }

    @Override
    public boolean updateWallet(WalletDto walletDto) throws WalletServiceException {
        try {
            return walletDao.update(walletDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotUpdated(e);
        }
    }

    @Override
    public WalletDto getById(Long id) throws WalletServiceException {
        try {
            return walletDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WalletNotFoundException(e);
        }
    }
}
