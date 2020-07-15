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

import static by.training.application.ApplicationConstants.SAVE_WALLET_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = SAVE_WALLET_CMD_NAME)
public class SaveWalletCommand implements ServletCommand {
    private WalletService walletService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            UserDto userDto = (UserDto) session.getAttribute("user");
            String walletName = req.getParameter("wallet.name");
            WalletDto walletDto = WalletDto.builder()
                    .name(walletName)
                    .userId(userDto.getUserId())
                    .build();
            walletService.saveWallet(walletDto);
            userDto.getWallets().add(walletDto);
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME);
        } catch (IOException | WalletServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
