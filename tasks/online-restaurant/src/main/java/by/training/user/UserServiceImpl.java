package by.training.user;

import by.training.contact.ContactDto;
import by.training.contact.ContactService;
import by.training.contact.ContactServiceException;
import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import by.training.discount.DiscountService;
import by.training.discount.DiscountServiceException;
import by.training.order.OrderDto;
import by.training.order.OrderService;
import by.training.order.OrderServiceException;
import by.training.role.RoleDto;
import by.training.role.RoleService;
import by.training.role.RoleServiceException;
import by.training.wallet.WalletDto;
import by.training.wallet.WalletService;
import by.training.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Log4j
@Bean
@AllArgsConstructor
@TransactionSupport
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleService roleService;
    private ContactService contactService;
    private WalletService walletService;
    private OrderService orderService;
    private DiscountService discountService;

    @Override
    @Transactional
    public boolean loginUser(UserDto userDto) throws UserServiceException {
        Optional<UserDto> byLogin;
        try {
            byLogin = userDao.findByLogin(userDto.getLogin());
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotLoggedException(e);
        }
        return byLogin.filter(dto -> dto.getPassword()
                .equals(userDto.getPassword()))
                .isPresent();
    }

    @Override
    public Optional<UserDto> findByLogin(String login) throws UserServiceException {
        try {
            Optional<UserDto> optional = userDao.findByLogin(login);
            if (!optional.isPresent()) {
                return Optional.empty();
            }
            UserDto dto = optional.get();
            initUserDto(dto);
            return Optional.of(dto);
        } catch (UserDaoException e) {
            log.error(e.getMessage(), e);
            throw new UserServiceException(e);
        }
    }

    @Override
    @Transactional
    public boolean registerUser(UserDto userDto) throws UserServiceException {
        try {
            long discountId = discountService.saveDefaultDiscount();
            userDto.setDiscountId(discountId);
            Long saved = userDao.save(userDto);
            if (saved > 0) {
                roleService.assignDefaultRoles(saved);
                userDto.setUserId(saved);
                for (ContactDto contact : userDto.getContacts()) {
                    contact.setUserId(saved);
                    contactService.saveContact(contact);
                }
                walletService.assignDefaultWallet(saved);
                return true;
            }
            return false;
        } catch (DaoException | WalletServiceException | ContactServiceException | RoleServiceException | DiscountServiceException e) {
            log.error(e.getMessage(), e);
            throw new UserNotRegisteredException(e);
        }
    }

    @Override
    @Transactional
    public boolean updateUser(UserDto userDto) throws UserServiceException {
        try {
            return userDao.update(userDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotUpdatedException(e);
        }
    }

    @Override
    public boolean delete(Long userId) throws UserServiceException {
        try {
            return userDao.delete(userId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public boolean isLoginTaken(String login) throws UserServiceException {
        try {
            return userDao.isLoginTaken(login);
        } catch (UserDaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotFoundException(e);
        }
    }

    @Override
    public UserDto getById(Long id) throws UserServiceException {
        try {
            UserDto userDto = userDao.getById(id);
            initUserDto(userDto);
            return userDto;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() throws UserServiceException {
        try {
            List<UserDto> users = userDao.findAll();
            users.forEach(this::initUserDto);
            return users;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new UserNotFoundException(e);
        }
    }

    private void initUserDto(UserDto userDto) {
        try {
            long userId = userDto.getUserId();
            List<ContactDto> userContacts = contactService.findUserContacts(userId);
            List<WalletDto> userWallets = walletService.findUserWallets(userId);
            List<OrderDto> userOrders = orderService.findUserOrders(userId);
            List<RoleDto> userRoles = roleService.findUserRoles(userId);
            userDto.setContacts(userContacts);
            userDto.setWallets(userWallets);
            userDto.setRoles(userRoles);
            userDto.setOrders(userOrders);
        } catch (ContactServiceException | WalletServiceException | OrderServiceException | RoleServiceException e) {
            e.printStackTrace();
        }
    }
}