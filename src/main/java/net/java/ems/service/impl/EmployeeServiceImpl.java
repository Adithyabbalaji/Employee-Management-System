package net.java.ems.service.impl;

import lombok.AllArgsConstructor;
import net.java.ems.dto.EmployeeDto;
import net.java.ems.entity.Employee;
import net.java.ems.exception.NotFoundException;
import net.java.ems.mapper.EmployeeMapper;
import net.java.ems.repository.EmployeeRepository;
import net.java.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee getEmployee= employeeRepository.findById(employeeId)
                .orElseThrow(()-> new NotFoundException("not found"));
        return EmployeeMapper.mapToEmployeeDto(getEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee =  employeeRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        Employee updateEmp = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updateEmp);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee getEmployee= employeeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("not found"));
        employeeRepository.deleteById(id);

    }
}
