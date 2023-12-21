package com.markovskisolutions.JJDT.web;

import com.markovskisolutions.JJDT.model.DTO.ModifyDTO;
import com.markovskisolutions.JJDT.model.DTO.UserDTO;
import com.markovskisolutions.JJDT.model.DTO.ViewDTO;
import com.markovskisolutions.JJDT.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Return a single user information.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we successfully converted request body to user entity.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when user do not exist in database.",
                    content = @Content
            )
    })
    @Parameter(name = "id", description = "Searched user ID.", required = true)
    @GetMapping("/{id}")
    public ViewDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create user base on request body and return it.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when user is successfully created.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return when some of the requested data fields is not fulfilled",
                    content = @Content
            )
    })
    @PostMapping("/create")
    public ViewDTO createUser(@RequestBody @Valid UserDTO userDTO)
    {
        Long userId = userService.createUser(userDTO);
        return userService.getUserById(userId);
    }

    @Operation(summary = "Get id of user that is need to be modified, together with request body and return updater user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When we update user with give id is successful.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ViewDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return when some of the requested data is not fulfilled",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when user do not exist in database.",
                    content = @Content()
            )
    })
    @Parameter(name = "id", description = "Id of the user that needs to be modify (updated)", required = true)
    @PostMapping("modify/{id}")
    public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody ModifyDTO modifyDTO) {
        boolean isModified = userService.modifyUser(id, modifyDTO);
        if (isModified) {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete user by given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When user with given ID is deleted successfully.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when user do not exist in database.",
                    content = @Content
            )
    })
    @Parameter(name = "id", description = "Id of the user that is need to be deleted", required = true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Return all users sorted.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's in database. Sorted first by last name ascending then by date of birth ascending",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when database is empty.",
                    content = @Content
            )
    })
    @GetMapping("/")
    public List<ViewDTO> getAllUsers() {
        return userService.getAllUsersOrdered();
    }

    @Operation(summary = "Return a user base on email input.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's with given email in database.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when there is no entity`s in data base with imputed email.",
                    content = @Content
            )
    })
    @Parameter(name = "email", description = "Email of the users that we search for", required = true)
    @GetMapping("/email={email}")
    public List<ViewDTO> getAllByEmail(@PathVariable String email) {
        return userService.getAllUsersByEmail(email);
    }

    @Operation(summary = "Return a user base on first name input.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's with given firs name in database.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when there is no entity`s in data base with imputed first name.",
                    content = @Content
            )
    })
    @Parameter(name = "firstName", description = "Firs name of the users that we search for", required = true)
    @GetMapping("/fn={firstName}")
    public List<ViewDTO> getAllByFirstName(@PathVariable String firstName) {
        return userService.getAllUsersByFirstName(firstName);
    }

    @Operation(summary = "Return a user base on last name input.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's with given last name in database.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when there is no entity`s in data base with imputed last name.",
                    content = @Content
            )
    })
    @Parameter(name = "lastName", description = "Last name of the users that we search for", required = true)
    @GetMapping("/ln={lastName}")
    public List<ViewDTO> getAllByLastName(@PathVariable String lastName) {
        return userService.getAllUsersByLastName(lastName);
    }

    @Operation(summary = "Return a user base on phone input.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's with given phone number in database.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when there is no entity`s in data base with imputed phone number.",
                    content = @Content
            )
    })
    @Parameter(name = "phone", description = "Phone number of users that we search for", required = true)
    @GetMapping("/p={phone}")
    public List<ViewDTO> getAllByPhoneNumber(@PathVariable String phone) {
        return userService.getAllUsersByPhoneNumber(phone);
    }

    @Operation(summary = "Return a user base on date of birth input. Date must be in format yyyy-mm-dd")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return when we have some entity's with given date of birth in database.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViewDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when there is no entity`s in data base with imputed date of birth.",
                    content = @Content
            )
    })
    @Parameter(name = "date", description = "Date of birth used to search for user with this date of birth", required = true)
    @GetMapping("d={date}")
    public List<ViewDTO> getAllByDate(@PathVariable String date) {
        return userService.getAllByDateOfBirth(date);
    }


    @Operation(summary = "Return a page of users.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return requested number of users starting from index X.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ViewDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when authorisation is failed..",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return when user do not exist in database.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return when parameters for offset or size are not correct.",
                    content = @Content
            )
    })
    @Parameter(name = "offset", description = "From which user we start our page.", required = true)
    @Parameter(name = "size", description = "How many elements is requested per page", required = true)
    @GetMapping("/pagination/{offset}/{size}")
    public List<ViewDTO> getUsersByPageSize(@PathVariable int offset, @PathVariable int size) {
        return userService.getUserWithPagination(offset, size);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String objectNotFound(ObjectNotFoundException exception) {
        return "Oops error 404 -Object not found";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String nullPointerException(NullPointerException exception) {
        return "Something is wrong with user creation. Please contact our service on : f.and.g.2019@gmail.com";
    }

    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dateException(DateTimeException exception) {
        return "Birth date need to be in format yyyy-mm-dd in order to be valid.";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notFullInformation(MethodArgumentNotValidException exception) {
        return "Check field " + exception.getFieldError().getField().toString();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incorrectPathParameters(IllegalArgumentException exception) {
        return "Please fill correct parameters for offset and page size.";
    }
}
