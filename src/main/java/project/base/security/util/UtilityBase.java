package project.base.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.base.security.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class UtilityBase {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UsersRepository usersRepository;

    // creadoPor
    public Integer createdBy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usersRepository.findById(authentication.getName());
        return id;
    }

    // creadoEl
    public Date createdAt() {
        return new Date();
    }

    // creadoEnIp
    public String createdByIp() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // modificadoPor
    public Integer modifiedBy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usersRepository.findById(authentication.getName());
        return id;
    }

    // modificadoEl
    public Date modifiedAt() {
        return new Date();
    }

    // modificadoEnIp
    public String modifiedByIp() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // randomUUID
    public String randomUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    // AddDaysToDate
    public static Date addDaysToDate(Date date, int days){
        if (days==0) return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
