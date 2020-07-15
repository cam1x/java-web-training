package by.training.wallet;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.training.application.ApplicationConstants.EDIT_WALLET_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = EDIT_WALLET_CMD_NAME)
public class EditWalletCommand implements ServletCommand {
    private WalletService walletService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String walletIdString = req.getParameter("walletId");
        HttpSession session = req.getSession(false);
        try {
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            UserDto userDto = (UserDto) session.getAttribute("user");
            List<WalletDto> userWallets = userDto.getWallets();
            long walletId = Long.parseLong(walletIdString);
            Optional<WalletDto> optionalWallet = userWallets.stream()
                    .filter(x -> x.getId() == walletId)
                    .findFirst();
            if (!optionalWallet.isPresent()) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            WalletDto updatingWallet = optionalWallet.get();
            double walletAmount = updatingWallet.getAmount();
            String walletName = req.getParameter("wallet.name");
            String depositParameter = req.getParameter("wallet.deposit");
            double depositAmount = (depositParameter == null || depositParameter.isEmpty()) ?
                    0 : Double.parseDouble(depositParameter);
            walletAmount += depositAmount;
            if (!updatingWallet.getName().equals(walletName) || updatingWallet.getAmount() != walletAmount) {
                updatingWallet.setName(walletName);
                updatingWallet.setAmount(walletAmount);
                walletService.updateWallet(updatingWallet);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME);
        } catch (WalletServiceException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
