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
import by.zakharyachnik.fitnessclub.service.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalTrainingServiceImpl implements PersonalTrainingService {

    private final UserRepository userRepository;

    private final PersonalTrainingMapper personalTrainingMapper;

    private final PersonalTrainingRepository personalTrainingRepository;

    private final TrainingProgramRepository trainingProgramRepository;

    private final UserMapper userMapper;

    @Override
    public PersonalTrainingDto addNewPersonalTraining(Long trainerId, String customerUsername) throws NotFoundException, AlreadyExistsException {

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer with id " + trainerId + " not found"));

        User customer = userRepository.findByUsername(customerUsername)
                .orElseThrow(() -> new NotFoundException("User with username " + customerUsername + " not found"));

        if(personalTrainingRepository.findByTrainerIdAndCustomerIdAndActive(trainerId, customer.getId(), true).isPresent()) {
            throw new AlreadyExistsException("Personal training already exists");
        }
        return personalTrainingMapper.toPersonalTrainingDto(personalTrainingRepository
                .save(new PersonalTraining()
                    .setActive(true)
                    .setCustomer(customer)
                    .setTrainer(trainer)));
    }

    @Override
    public List<UserDto> getUserTrainers(Long userId) throws NotFoundException {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with id " + userId + " not found");
        }

        return personalTrainingRepository.findByCustomerIdAndActive(userId, true)
                .stream()
                .map(PersonalTraining::getTrainer)
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public PersonalTrainingDto cancelPersonalTraining(Long trainerId, Long customerId) throws NotFoundException {
        Optional<PersonalTraining> personalTraining = personalTrainingRepository.findByTrainerIdAndCustomerIdAndActive(trainerId, customerId, true);

        if(personalTraining.isEmpty()) {
            throw new NotFoundException("Personal training with trainer id " + trainerId + " and customer id " + customerId + " not found");
        }

        personalTraining.get().setActive(false);
        return personalTrainingMapper.toPersonalTrainingDto(personalTrainingRepository.save(personalTraining.get()));
    }

    @Override
    public PersonalTrainingDto cancelPersonalTraining(Long personalTrainingId) throws NotFoundException {
        Optional<PersonalTraining> personalTraining = personalTrainingRepository.findById(personalTrainingId);

        if(personalTraining.isEmpty()) {
            throw new NotFoundException("Personal training with id " + personalTrainingId + " not found");
        }

        personalTraining.get().setActive(false);
        return personalTrainingMapper.toPersonalTrainingDto(personalTrainingRepository.save(personalTraining.get()));
    }

    @Override
    public List<PersonalTrainingDto> getTrainerPersonalTrainings(Long trainerId) {
        return personalTrainingRepository.findByTrainerIdAndActive(trainerId, true)
                .stream()
                .map(personalTrainingMapper::toPersonalTrainingDto)
                .toList();
    }

    @Override
    public PersonalTrainingDto getPersonalTraining(Long personalTrainingId) {
        return personalTrainingMapper.toPersonalTrainingDto(personalTrainingRepository.findById(personalTrainingId).orElse(null));
    }

    @Override
    public PersonalTrainingDto addTrainingProgramToPersonalTraining(Long personalTrainingId, Long trainingProgramId) throws NotFoundException {

        PersonalTraining personalTraining = personalTrainingRepository.findById(personalTrainingId)
                .orElseThrow(() -> new NotFoundException("Personal training with id " + personalTrainingId + " not found"));

        TrainingProgram trainingProgram = trainingProgramRepository.findById(trainingProgramId)
                .orElseThrow(() -> new NotFoundException("Training program with id " + trainingProgramId + " not found"));

        return personalTrainingMapper.toPersonalTrainingDto(personalTrainingRepository
                .save(personalTraining
                    .setTrainingProgram(trainingProgram)));
        }


}
