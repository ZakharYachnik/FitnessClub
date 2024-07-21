package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.PersonalTrainingDto;
import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.PersonalTraining;
import by.zakharyachnik.fitnessclub.entity.TrainingProgram;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.PersonalTrainingMapper;
import by.zakharyachnik.fitnessclub.mapper.UserMapper;
import by.zakharyachnik.fitnessclub.repository.PersonalTrainingRepository;
import by.zakharyachnik.fitnessclub.repository.TrainingProgramRepository;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonalTrainingServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonalTrainingMapper personalTrainingMapper;

    @Mock
    private PersonalTrainingRepository personalTrainingRepository;

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private PersonalTrainingServiceImpl personalTrainingService;

    private User trainer;
    private User customer;
    private PersonalTraining personalTraining;
    private PersonalTrainingDto personalTrainingDto;
    private UserDto userDto;
    private TrainingProgram trainingProgram;

    @BeforeEach
    void setUp() {
        trainer = new User();
        trainer.setId(1L);
        trainer.setUsername("trainer");

        customer = new User();
        customer.setId(2L);
        customer.setUsername("customer");

        personalTraining = new PersonalTraining();
        personalTraining.setId(1L);
        personalTraining.setTrainer(trainer);
        personalTraining.setCustomer(customer);
        personalTraining.setActive(true);

        userDto = new UserDto(2L, "customer", "Customer Fullname", true, "123456789", List.of("ROLE_USER"));

        personalTrainingDto = new PersonalTrainingDto(1L, userDto, 1L, null);

        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
    }


    @Test
    public void testAddNewPersonalTraining() throws NotFoundException, AlreadyExistsException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(trainer));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(customer));
        when(personalTrainingRepository.findByTrainerIdAndCustomerIdAndActive(anyLong(), anyLong(), anyBoolean())).thenReturn(Optional.empty());
        when(personalTrainingRepository.save(any(PersonalTraining.class))).thenReturn(personalTraining);
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);

        PersonalTrainingDto result = personalTrainingService.addNewPersonalTraining(1L, "customer");

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(personalTrainingRepository, times(1)).findByTrainerIdAndCustomerIdAndActive(anyLong(), anyLong(), anyBoolean());
        verify(personalTrainingRepository, times(1)).save(any(PersonalTraining.class));
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(personalTrainingDto, result);
    }

    @Test
    public void testGetUserTrainers() throws NotFoundException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(personalTrainingRepository.findByCustomerIdAndActive(anyLong(), anyBoolean())).thenReturn(Collections.singletonList(personalTraining));
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);

        List<UserDto> result = personalTrainingService.getUserTrainers(2L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(personalTrainingRepository, times(1)).findByCustomerIdAndActive(anyLong(), anyBoolean());
        verify(userMapper, times(1)).toUserDto(any(User.class));
        assertEquals(1, ((List<?>) result).size());
        assertEquals(userDto, result.get(0));
    }

    @Test
    public void testCancelPersonalTrainingByTrainerAndCustomer() throws NotFoundException {
        when(personalTrainingRepository.findByTrainerIdAndCustomerIdAndActive(anyLong(), anyLong(), anyBoolean())).thenReturn(Optional.of(personalTraining));
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);
        when(personalTrainingRepository.save(any(PersonalTraining.class))).thenReturn(personalTraining);

        PersonalTrainingDto result = personalTrainingService.cancelPersonalTraining(1L, 2L);

        verify(personalTrainingRepository, times(1)).findByTrainerIdAndCustomerIdAndActive(anyLong(), anyLong(), anyBoolean());
        verify(personalTrainingRepository, times(1)).save(any(PersonalTraining.class));
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(personalTrainingDto, result);
    }

    @Test
    public void testCancelPersonalTrainingById() throws NotFoundException {
        when(personalTrainingRepository.findById(anyLong())).thenReturn(Optional.of(personalTraining));
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);
        when(personalTrainingRepository.save(any(PersonalTraining.class))).thenReturn(personalTraining);

        PersonalTrainingDto result = personalTrainingService.cancelPersonalTraining(1L);

        verify(personalTrainingRepository, times(1)).findById(anyLong());
        verify(personalTrainingRepository, times(1)).save(any(PersonalTraining.class));
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(personalTrainingDto, result);
    }

    @Test
    public void testGetTrainerPersonalTrainings() {
        when(personalTrainingRepository.findByTrainerIdAndActive(anyLong(), anyBoolean())).thenReturn(Collections.singletonList(personalTraining));
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);

        List<PersonalTrainingDto> result = personalTrainingService.getTrainerPersonalTrainings(1L);

        verify(personalTrainingRepository, times(1)).findByTrainerIdAndActive(anyLong(), anyBoolean());
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(1, result.size());
        assertEquals(personalTrainingDto, result.get(0));
    }

    @Test
    public void testGetPersonalTraining() {
        when(personalTrainingRepository.findById(anyLong())).thenReturn(Optional.of(personalTraining));
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);

        PersonalTrainingDto result = personalTrainingService.getPersonalTraining(1L);

        verify(personalTrainingRepository, times(1)).findById(anyLong());
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(personalTrainingDto, result);
    }

    @Test
    public void testAddTrainingProgramToPersonalTraining() throws NotFoundException {
        when(personalTrainingRepository.findById(anyLong())).thenReturn(Optional.of(personalTraining));
        when(trainingProgramRepository.findById(anyLong())).thenReturn(Optional.of(trainingProgram));
        when(personalTrainingMapper.toPersonalTrainingDto(any(PersonalTraining.class))).thenReturn(personalTrainingDto);
        when(personalTrainingRepository.save(any(PersonalTraining.class))).thenReturn(personalTraining);

        PersonalTrainingDto result = personalTrainingService.addTrainingProgramToPersonalTraining(1L, 1L);

        verify(personalTrainingRepository, times(1)).findById(anyLong());
        verify(trainingProgramRepository, times(1)).findById(anyLong());
        verify(personalTrainingRepository, times(1)).save(any(PersonalTraining.class));
        verify(personalTrainingMapper, times(1)).toPersonalTrainingDto(any(PersonalTraining.class));
        assertEquals(personalTrainingDto, result);
    }
}

