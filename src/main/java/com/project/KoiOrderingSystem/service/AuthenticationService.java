package com.project.KoiOrderingSystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Role;
import com.project.KoiOrderingSystem.exception.AuthException;
import com.project.KoiOrderingSystem.exception.DuplicateEntity;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.repository.AccountRepository;
import io.github.cdimascio.dotenv.Dotenv;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.client.RestTemplate;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

//    public AccountResponse loginGoogle (LoginGoogleRequest loginGoogleRequest) {
//        try{
//            FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
//            String email = decodeToken.getEmail();
//            Account user = accountRepository.findAccountByEmail(email);
//            if(user == null) {
//                user.setFirstName(decodeToken.getName());
//                user.setEmail(email);
//                user.setUsername(email);
//                user.setRole(Role.CUSTOMER);
//                user = accountRepository.save(user);
//            }
//            AccountResponse accountResponse = modelMapper.map(user, AccountResponse.class);
//            accountResponse.setToken(tokenService.generateToken(user));
//            return accountResponse;
//        } catch (FirebaseAuthException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public AccountResponse loginGoogle(GoogleLoginRequest googleLoginRequest) {
        try {
            Dotenv dotenv = Dotenv.load();
            String token = googleLoginRequest.getToken();
//            String googleClientId = "384168050473-onfo1qknuvbpjjpnk6ldn0fjdt3mtjd5.apps.googleusercontent.com";
            String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
            URL url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + token);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();

            String clientId = jsonResponse.get("aud").getAsString();
            if (!googleClientId.equals(clientId)) {
                throw new AuthException("Invalid token: client ID mismatch");
            }

            String email = jsonResponse.get("email").getAsString();
            String firstName = jsonResponse.get("given_name").getAsString();
            String lastName = jsonResponse.get("family_name").getAsString();

            Account user = accountRepository.findAccountByEmail(email);
            if (user == null) {
                user = new Account();
                user.setEmail(email);
                user.setUsername(email);
            }

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole(Role.CUSTOMER);
            accountRepository.save(user);

            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setEmail(email);
            accountResponse.setFirstName(firstName);
            accountResponse.setLastName(lastName);
            accountResponse.setUsername(email);
            accountResponse.setRole(Role.CUSTOMER);
            accountResponse.setToken(tokenService.generateToken(user));

            return accountResponse;

        } catch (Exception e) {
            throw new AuthException("Invalid token" + e);
        }
    }

    public AccountResponse loginFacebook(FacebookLoginRequest facebookLoginRequest) {
        String accessToken = facebookLoginRequest.getToken();
        String fbGraphUrl = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;
        Account user = new Account();
        AccountResponse accountResponse = new AccountResponse();
        RestTemplate restTemplate = new RestTemplate();
        try {
            JsonNode fbUser = restTemplate.getForObject(fbGraphUrl, JsonNode.class);
            String facebookId = fbUser.get("id").asText();
            user.setFirstName(fbUser.get("name").asText());
            user.setEmail(fbUser.has("email") ? fbUser.get("email").asText() : null);
            user.setUsername(fbUser.has("email") ? fbUser.get("email").asText() : facebookId);
            Account existingUser = accountRepository.findAccountByUsername(user.getUsername());
            if (existingUser == null) {
                user.setRole(Role.CUSTOMER);
                accountRepository.save(user);
            }


            accountResponse.setUsername(user.getUsername());
            accountResponse.setRole(user.getRole());
            accountResponse.setEmail(user.getEmail());
            accountResponse.setFirstName(user.getFirstName());
            accountResponse.setToken(tokenService.generateToken(user));

            return accountResponse;

        } catch (Exception e) {
            throw new AuthException("Invalid token" + e);
        }
    }

    public AccountResponse loginGoogleFirebase(GoogleLoginRequest googleLoginRequest) {
        String idToken = googleLoginRequest.getToken();
        Account user = new Account();
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            user.setRole(Role.CUSTOMER);
            user.setEmail(email);
            user.setUsername(email);
            user.setFirstName(name);
            Account existingUser = accountRepository.findAccountByUsername(user.getUsername());
            if (existingUser == null) {
                user.setRole(Role.CUSTOMER);
                accountRepository.save(user);
            }

            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setUsername(user.getUsername());
            accountResponse.setRole(user.getRole());
            accountResponse.setEmail(user.getEmail());
            accountResponse.setFirstName(user.getFirstName());
            accountResponse.setLastName(user.getLastName());
            accountResponse.setToken(tokenService.generateToken(user));
            return accountResponse;
            // Tại đây bạn có thể tạo user nếu chưa có hoặc cập nhật thông tin

            // Tạo JWT cho ứng dụng backend

        } catch (FirebaseAuthException e) {
             throw new AuthException("Invalid token" + e);
        }
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
