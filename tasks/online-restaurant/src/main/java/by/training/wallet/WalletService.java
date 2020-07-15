package by.training.wallet;

import java.util.List;

public interface WalletService {
    List<WalletDto> findUserWallets(Long userId) throws WalletServiceException;

    boolean deleteWallet(Long walletId) throws WalletServiceException;

    boolean deleteUserWallets(Long userId) throws WalletServiceException;

    List<WalletDto> findAllWallets() throws WalletServiceException;

    boolean saveWallet(WalletDto walletDto) throws WalletServiceException;

    boolean assignDefaultWallet(Long userId) throws WalletServiceException;

    boolean updateWallet(WalletDto walletDto) throws WalletServiceException;

    WalletDto getById(Long id) throws WalletServiceException;
}
