package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.RoleMapper;
import cn.ean.emp.mapper.UserMapper;
import cn.ean.emp.mapper.UserRoleMapper;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.dto.RoleDTO;
import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.RolePO;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.model.po.UserRolePO;
import cn.ean.emp.service.IUserService;
import cn.ean.emp.util.JwtTokenUtils;
import cn.ean.emp.util.UserUtils;
import cn.ean.emp.util.charopn.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ean
 * @FileName UserServiceImpl
 * @Date 2022/5/23 23:04
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtils jwtTokenUtils;
    private RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           UserRoleMapper userRoleMapper,
                           UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder,
                           JwtTokenUtils jwtTokenUtils,
                           RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.roleMapper = roleMapper;
    }

    /**
     * ??????????????????token
     *
     * @param userName
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public ResponseBO login(String userName, String password, String code, HttpServletRequest request) {
        // ???????????????????????????????????????????????????????????????return?????????????????????
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
            return ResponseBO.error("???????????????");
        }

        // ????????????
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);


        if (null == userDetails || passwordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseBO.error("???????????????????????????");
        }
        if (!userDetails.isEnabled()) {
            return ResponseBO.error("?????????????????????????????????");
        }
        // ??????security??????????????????????????????????????????
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        // ??????token
        String token = jwtTokenUtils.generateToken(userDetails);

        Map<String, String> tokenMap = new HashMap<>(16);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return ResponseBO.success("????????????", tokenMap);
    }

    /**
     * ???????????????????????????
     *
     * @param userName
     * @return
     */
    @Override
    public UserPO getUserByUserName(String userName) {
        UserPO userPO = userMapper.selectOne(new QueryWrapper<UserPO>()
                .eq("workId", userName)
                .eq("enabled", true));

        return userPO;
    }

    /**
     * ????????????id??????????????????
     *
     * @param userId
     * @return
     */
    @Override
    public List<RolePO> getRoles(Integer userId) {
        return roleMapper.getRoles(userId);
    }

    /**
     * ??????????????????
     *
     * @param keyWords
     * @return
     */
    @Override
    public List<UserPO> getAllUsers(String keyWords) {
        System.out.println("UserServiceImpl getAllUsers: List<UserPO>:"
                + userMapper.getAllUsers(UserUtils.getCurrentUser().getId(), keyWords).get(0).getRoles().get(0).getAuthorityName());
        return userMapper.getAllUsers(UserUtils.getCurrentUser().getId(), keyWords);
    }

    /**
     * ??????????????????
     *
     * @param userId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public ResponseBO updateUserRole(Integer userId, Integer[] rids) {
        userRoleMapper.delete(new QueryWrapper<UserRolePO>().eq("userId", userId));
        Integer result = userRoleMapper.updateUserRole(userId, rids);
        if (rids.length == result) {
            return ResponseBO.success("???????????????");
        }
        return ResponseBO.error("???????????????????????????");
    }

    /**
     * ??????????????????
     *
     * @param oldPass
     * @param pass
     * @param userId
     * @return
     */
    @Override
    public ResponseBO updateUserPassword(String oldPass, String pass, Integer userId) {
        UserPO userPO = userMapper.selectById(userId);
       // userPO = UserUtils.getCurrentUser();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // ???????????????????????????
        if (passwordEncoder.matches(oldPass, userPO.getPassword())) {
            System.out.println("=======????????????");
            userPO.setPassword(encoder.encode(pass));
            if (1 == userMapper.updateById(userPO)) {
                return ResponseBO.success("????????????????????????");
            }
        } else {
            userPO.setPassword(encoder.encode(pass));

            if (1 == userMapper.updateById(userPO)) {
                return ResponseBO.success("???????????????????????????????????????????????????????????????????????????");
            }
        }

        return ResponseBO.error("????????????????????????");
    }
}
