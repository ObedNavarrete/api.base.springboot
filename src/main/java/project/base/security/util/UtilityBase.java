package project.base.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.base.security.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class UtilityBase {
    @Autowired HttpServletRequest request;
    @Autowired UsersRepository usersRepository;

    // creadoPor
    public Integer createdBy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer id = usersRepository.findIdLogued(authentication.getName());
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
        Integer id = usersRepository.findIdLogued(authentication.getName());
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
    
    // AddMonthsToDate
    public static Date addMonthsToDate(Date date, int months){
        if (months==0) return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    // Verificar si el usuario logueado tiene el rol
    public Map<String, Boolean> hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> response = new java.util.HashMap<>();
        response.put("hasRole", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role)));
        return response;
    }

    // Verificar si el usuario tiene el rol
    // Ejenplo de uso => Boolean result = this.idUserHasRole(1,"ROLE_CUSTOMER");
    public Boolean idUserHasRole(Integer id, String rol) {
        Boolean response = false;
        Users user = usersRepository.findByPasiveIsFalseAndId(id);
        if (user == null) {
            return false;
        }
        response = user.getRoles().stream().anyMatch(r -> r.getName().equals(rol));
        return response;
    }

    // Verificar si el usuario tiene el uno de los roles
    // Ejenplo de uso => Boolean result = this.idUserHasAnyRole(1, new String[]{"ROLE_CUSTOMER", "ROLE_MANAGER"});
    public Boolean idUserHasAnyRole(Integer id, String[] roles) {
        Boolean response = false;
        Users user = usersRepository.findByPasiveIsFalseAndId(id);
        if (user == null) {
            return false;
        }
        for (String rol : roles) {
            response = user.getRoles().stream().anyMatch(r -> r.getName().equals(rol));
            if (response) {
                break;
            }
        }
        return response;
    }

    // Verificar si el usuario sigue siendo v??lido
    public boolean isUserValid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> response = new java.util.HashMap<>();

        Integer id = usersRepository.findIdLogued(authentication.getName());
        if (id == null) {
            return false;
        } else {
            return true;
        }
    }
}
