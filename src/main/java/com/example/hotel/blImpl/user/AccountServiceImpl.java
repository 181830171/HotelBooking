package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String EMAIL_EXIST = "邮箱已存在";
    private final static String NAME_EXIST = "用户名已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String DELETEUSER_ERROR = "删除失败";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        // 除了注册时填写的信息外，还需要设置用户的信用值和用户类型
        user.setCredit(100);
        user.setUserType(UserType.Client);
        // 避免重复注册 邮箱重复或者用户名重复
        User isExistEmail = accountMapper.getAccountByEmail(user.getEmail());
        if (isExistEmail != null) {
            System.out.println("邮箱已存在: " + user.getEmail());
            return ResponseVO.buildFailure(EMAIL_EXIST);
        }
        User isExistName = accountMapper.getAccountByName(user.getUserName());
        if (isExistName != null) {
            System.out.println("用户名已存在 " + user.getUserName());
            return ResponseVO.buildFailure(NAME_EXIST);
        }
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByEmail(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        User isExistName = accountMapper.getAccountByName(username);
        // 用户名不能够重复
        // 但是同一个用户的用户名可以保持不变，所以还要判断id不同
        if (isExistName != null && isExistName.getId() != id) {
            System.out.println("用户名已存在 " + username);
            return ResponseVO.buildFailure(NAME_EXIST);
        }
        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    //删除用户
    @Override
    public ResponseVO deleteUser(int id) {
        try {
            accountMapper.deleteUser(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETEUSER_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public double getCurrentUserCredit(int id) {
        return accountMapper.getCurrentUserCredit(id);
    }

    // 更新当前信用值
    @Override
    public ResponseVO updateCredit(int id, double change) {
        try {
            accountMapper.updateCredit(id, change);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
