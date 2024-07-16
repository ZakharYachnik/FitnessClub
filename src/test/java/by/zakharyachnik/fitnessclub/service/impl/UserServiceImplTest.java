package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserRole;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.UserMapper;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        // Настройка моков перед каждым тестом
    }

    private UserDto createUserDto() {
        return new UserDto(
                1L,
                "username",
                "fullName",
                true,
                "phoneNumber",
                List.of("ROLE_USER")
        );
    }

    @Test
    void testFindAll() {
        List<User> users = List.of(new User(), new User());
        List<UserDto> userDtos = List.of(createUserDto(), createUserDto());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserDto(any(User.class))).thenReturn(createUserDto());

        List<UserDto> result = userServiceImpl.findAll();

        assertEquals(userDtos.size(), result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(users.size())).toUserDto(any(User.class));
    }

    @Test
    void testSave() throws AlreadyExistsException {
        String username = "testUser";
        String fullName = "Test User";
        String password = "password";
        String phoneNumber = "1234567890";
        String role = "ROLE_USER";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRoleRepository.findByName(role)).thenReturn(new UserRole());
        when(userMapper.toUserDto(any(User.class))).thenReturn(createUserDto());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        UserDto result = userServiceImpl.save(username, fullName, password, phoneNumber, role);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRoleRepository, times(1)).findByName(role);
        verify(userMapper, times(1)).toUserDto(any(User.class));
    }

    @Test
    void testSaveUserAlreadyExists() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userServiceImpl.save(username, "Test User", "password", "1234567890", "ROLE_USER");
        });

        assertEquals("User with username " + username + " already exists", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testBlockUser() throws NotFoundException {
        String username = "testUser";
        User user = new User().setUsername(username).setActive(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(createUserDto());

        UserDto result = userServiceImpl.blockUser(username);

        assertFalse(user.isActive());
        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUserDto(user);
    }

    @Test
    void testBlockUserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userServiceImpl.blockUser(username);
        });

        assertEquals("User with username " + username + " not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testUnblockUser() throws NotFoundException {
        String username = "testUser";
        User user = new User().setUsername(username).setActive(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(createUserDto());

        UserDto result = userServiceImpl.unblockUser(username);

        assertTrue(user.isActive());
        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUserDto(user);
    }

    @Test
    void testUnblockUserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userServiceImpl.unblockUser(username);
        });

        assertEquals("User with username " + username + " not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testUpdate() throws AlreadyExistsException, NotFoundException {
        String username = "updatedUser";
        String fullName = "Updated User";
        String password = "newPassword";
        String phoneNumber = "0987654321";
        Long userId = 1L;

        User existingUser = new User().setId(userId).setUsername("existingUser");
        User updatedUser = new User()
                .setId(userId)
                .setUsername(username)
                .setFullName(fullName)
                .setPassword("encodedPassword")
                .setPhoneNumber(phoneNumber);

        when(userRepository.findByUsernameAndIdNot(username, userId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userMapper.toUserDto(any(User.class))).thenReturn(createUserDto());

        UserDto result = userServiceImpl.update(username, fullName, password, phoneNumber, userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsernameAndIdNot(username, userId);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(argThat(user ->
                user.getId().equals(userId) &&
                user.getUsername().equals(username) &&
                user.getFullName().equals(fullName) &&
                user.getPhoneNumber().equals(phoneNumber)
        ));
        verify(passwordEncoder, times(1)).encode(password);
        verify(userMapper, times(1)).toUserDto(updatedUser);
    }

    @Test
    void testUpdateUserNotFound() {
        String username = "updatedUser";
        Long userId = 1L;

        when(userRepository.findByUsernameAndIdNot(username, userId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userServiceImpl.update(username, "Updated User", "newPassword", "0987654321", userId);
        });

        assertEquals("User with id " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateUsernameAlreadyExists() {
        String username = "updatedUser";
        Long userId = 1L;

        when(userRepository.findByUsernameAndIdNot(username, userId)).thenReturn(Optional.of(new User()));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userServiceImpl.update(username, "Updated User", "newPassword", "0987654321", userId);
        });

        assertEquals("User with username " + username + " and another id already exists", exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndIdNot(username, userId);
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User user = new User().setId(userId);
        UserDto userDto = createUserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto result = userServiceImpl.findById(userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toUserDto(user);
    }

    @Test
    void testFindByIdUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDto result = userServiceImpl.findById(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }
}
