package com.project.KoiOrderingSystem.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Role;
import com.project.KoiOrderingSystem.exception.DuplicateEntity;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;


    @Autowired
    EmailService emailService;

    public AccountResponse register (RegisterRequest registerRequest) {
        Account account = modelMapper.map(registerRequest, Account.class);
        try {
            String originalPass =account.getPassword();
            account.setPassword(passwordEncoder.encode(originalPass));
            Account newAccount = accountRepository.save(account);

            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setReceiver(account);
            emailDetail.setSubject("Welcome to Koi Ordering System");
            emailDetail.setLink("http://localhost:5173/");
            emailService.sendEmail(emailDetail);

            return modelMapper.map(newAccount, AccountResponse.class);
        } catch (Exception e) {
            if (e.getMessage().contains(account.getUsername())) {
                throw new DuplicateEntity("Uername is already exist");
            } else  {
                throw new DuplicateEntity("Email is already exist");
            }
        }
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            ));

            // tài khoản có tồn tại
            Account account = (Account) authentication.getPrincipal();
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            accountResponse.setToken(tokenService.generateToken(account));
            return accountResponse;
        } catch (Exception e) {
            throw new EntityNotFoundException("Username or password invalid");
        }
    }

    public AccountResponse loginGoogle (LoginGoogleRequest loginGoogleRequest) {
        try{
            FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = decodeToken.getEmail();
            Account user = accountRepository.findAccountByEmail(email);
            if(user == null) {
                user.setFirstName(decodeToken.getName());
                user.setEmail(email);
                user.setUsername(email);
                user.setRole(Role.CUSTOMER);
                user = accountRepository.save(user);
            }
            AccountResponse accountResponse = modelMapper.map(user, AccountResponse.class);
            accountResponse.setToken(tokenService.generateToken(user));
            return accountResponse;
        } catch (FirebaseAuthException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        Account account = accountRepository.findAccountByEmail(forgotPasswordRequest.getEmail());
        if(account == null) throw new EntityNotFoundException("Email not exist!");
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setReceiver(account);
        emailDetail.setSubject("Forgot password");
        emailDetail.setLink("http://localhost:5173/reset-password?token=" + tokenService.generateToken(account));
        emailService.sendEmailForgotPassword(emailDetail);
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest){
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        accountRepository.save(account);
    }

    public List<AccountResponse> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(entity -> modelMapper.map(entity, AccountResponse.class))
                .collect(Collectors.toList());
    }

    public AccountResponse update(long accountId, ProfileRequest profileRequest){
        Account oldAccount = getAccountById(accountId);

        oldAccount.setLastName(profileRequest.getLastName());
        oldAccount.setFirstName(profileRequest.getFirstName());
        oldAccount.setAddress(profileRequest.getAddress());
        oldAccount.setEmail(profileRequest.getEmail());
        oldAccount.setPhone(profileRequest.getPhone());
        oldAccount.setProfile(profileRequest.getProfile());

        accountRepository.save(oldAccount);
        return modelMapper.map(oldAccount, AccountResponse.class);
    }

    public Account getAccountById(long id){
        Account account = accountRepository.findAccountById(id);
        if(account == null) throw new EntityNotFoundException("Account not exist!");

        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findAccountByUsername(username);
    }

    public Account getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountById(account.getId());
    }
}
